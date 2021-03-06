package com.univers.sweepplay.Device;

import android.app.Activity;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;
import android.widget.Toast;

import com.univers.sweepplay.R;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class NfcPairingActivity extends Activity {

    public static final String ERROR_DETECTED = "No NFC tag detected!";
    public static final String WRITE_SUCCESS = "Text written to the NFC tag successfully!";
    public static final String WRITE_ERROR = "Error during writing, is the NFC tag close enough to your device?";
    public static final String SECURE_SETTINGS_BLUETOOTH_ADDRESS = "bluetooth_address";
    public static final String MAC_ADDRESS_NULL = "No Mac address written";
    private String macAddress = null;

    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;
    IntentFilter writeTagFilters[];
    boolean writeMode;
    Tag myTag;
    Context context;
    TextView tvNFCContent;

    /**
     * Create activity
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc_pairing);
        context = this;

        tvNFCContent = findViewById(R.id.nfc_contents);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            // Stop here, we definitely need NFC
            Toast.makeText(this, "This device doesn't support NFC.", Toast.LENGTH_LONG).show();
            finish();
        }
        bth_message();
        writeMacAddress(getIntent());

        pendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
        writeTagFilters = new IntentFilter[] { tagDetected }; // tag detected
    }

    /**
     * Write mac address into device
     * detect arror into nfc write process
     * @param intent
     */
    private void writeMacAddress(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                      || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {  // were to write address
            try {
                if (myTag == null) { // no Tag to write
                    Toast.makeText(context, ERROR_DETECTED, Toast.LENGTH_LONG).show();
                } else {
                    if (macAddress == null)
                    {
                        Toast.makeText(context, MAC_ADDRESS_NULL, Toast.LENGTH_LONG).show();
                    } else {
                        write(bth_message(), myTag);
                        Toast.makeText(context, WRITE_SUCCESS, Toast.LENGTH_LONG).show();
                    }
                }
            } catch (IOException e) { // error during writing process
                Toast.makeText(context, WRITE_ERROR, Toast.LENGTH_LONG ).show();
                e.printStackTrace();
            } catch (FormatException e) {
                Toast.makeText(context, WRITE_ERROR, Toast.LENGTH_LONG ).show();
                e.printStackTrace();
            }
        }
    }

    /**
     * Write @text into tag
     * @param text
     * @param tag
     */
    private void write(String text, Tag tag) throws IOException, FormatException {
        NdefRecord[] records = { createRecord(text) };
        NdefMessage message = new NdefMessage(records);
        Ndef ndef = Ndef.get(tag);         // Get an instance of Ndef for the tag.
        ndef.connect();        // Enable I/O
        ndef.writeNdefMessage(message);         // Write the message
        ndef.close();         // Close the connection
    }

    /**
     * Create mac address from text
     * @param text
     */
    private NdefRecord createRecord(String text) throws UnsupportedEncodingException {
        String lang       = "en";
        byte[] txtBytes  = text.getBytes();
        byte[] languageBytes  = lang.getBytes("US-ASCII");
        int    languageLength = languageBytes.length;
        int    textLength = txtBytes.length;
        byte[] payloadId    = new byte[1 + languageLength + textLength];         // set status byte (see NDEF spec for actual bits)
        payloadId[0] = (byte) languageLength;         // copy langbytes and textbytes into payload
        System.arraycopy(languageBytes, 0, payloadId, 1, languageLength);
        System.arraycopy(txtBytes, 0, payloadId, 1 + languageLength, textLength);

        NdefRecord recordNFC = new NdefRecord(NdefRecord.TNF_WELL_KNOWN,  NdefRecord.RTD_TEXT,  new byte[0], payloadId);
        return recordNFC;
    }

    /**
     * restart/start new activity from the intent when the tag is detected
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        writeMacAddress(intent);
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())){
            myTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        }
    }

    /**
     * in case activity gone in pause mode
     * disable write mode
     */
    @Override
    public void onPause(){
        super.onPause();
        WriteOff();
    }

    /**
     * enable write mode when activity restart
     */
    @Override
    public void onResume(){
        super.onResume();
        WriteOn();
    }

    /**
     * enable nfc write mode
     */
    private void WriteOn(){
        writeMode = true;
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, writeTagFilters, null);
    }

    /**
     * disable nfc write mode
     */
    private void WriteOff(){
        writeMode = false;
        nfcAdapter.disableForegroundDispatch(this);
    }

    /**
     * recovery of the mac address
     */
    private String bth_message() { // recovery mac address
        BluetoothAdapter Bth_Def_Adapter = BluetoothAdapter.getDefaultAdapter();
        if ((Bth_Def_Adapter != null) && (Bth_Def_Adapter.isEnabled())) {
            macAddress = Settings.Secure.getString(getContentResolver(), SECURE_SETTINGS_BLUETOOTH_ADDRESS);
        }
        return macAddress;
    }
}