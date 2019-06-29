package com.univers.sweepplay.Device;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.univers.sweepplay.R;

public class SelectPairingModeActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = SelectPairingModeActivity.this;

    private NfcAdapter myNfcAdapter;
    private AppCompatTextView myText;
    private AppCompatButton btn_nfc;
    private AppCompatButton btn_device_list;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_pairing_mode);
        getSupportActionBar().hide();
        initViews();
        initListeners();


        myNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (myNfcAdapter == null) {
            myText.setText("NFC is not available for the device!!!");
        }
        else {
            myText.setText("NFC is available for the device");
        }
    }

    private void initViews() {
        myText= findViewById(R.id.myText);
        btn_nfc = findViewById(R.id.btn_nfc);
        btn_device_list = findViewById(R.id.btn_device_list);
    }

    private void initListeners() {
        myText.setOnClickListener(this);
        btn_nfc.setOnClickListener(this);
        btn_device_list.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) { // 2 buttons for
        switch (v.getId()) {
            case R.id.btn_device_list:
                Intent intent_manual = new Intent(getApplicationContext(), DeviceListActivity.class);
                startActivity(intent_manual);
                break;
            case R.id.btn_nfc:
                // Navigate to RegisterActivity
                Intent intent_nfc = new Intent(getApplicationContext(), NfcPairingActivity.class);
                startActivity(intent_nfc);
                break;
        }
    }
}
