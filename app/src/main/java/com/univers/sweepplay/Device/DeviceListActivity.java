package com.univers.sweepplay.Device;

import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.univers.sweepplay.R;

public class DeviceListActivity extends Activity {
    TextView connectionStatus;    // textview for connection status
    ListView pairedListView;
    public static String EXTRA_DEVICE_ADDRESS; //An EXTRA to take the device MAC to the next activity
    private BluetoothAdapter adapter;
    private ArrayAdapter<String> pairedDevicesArrayAdapter;

    /**
     * Create activity
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_list);

        connectionStatus = findViewById(R.id.connecting);
        connectionStatus.setTextSize(40);

        // Initialize array adapter for paired devices
        pairedDevicesArrayAdapter = new ArrayAdapter<>(this, R.layout.device_name);

        pairedListView = findViewById(R.id.paired_devices);
        // Find and set up the ListView for paired devices
        pairedListView.setAdapter(pairedDevicesArrayAdapter);
        pairedListView.setOnItemClickListener(mDeviceClickListener);
    }

    /**
     * recreate activity
     *
     * connection methods are here in case program goes into the background
     */
    public void onResume() {
        super.onResume();
        bluetoothStatus();

        pairedDevicesArrayAdapter.clear();// clears the array to avoid duplication
        connectionStatus.setText(" "); //makes the textview blank


        // Get a set of currently paired devices and append to pairedDevices list
        Set<BluetoothDevice> pairedDevices = adapter.getBondedDevices();

        // Add previously paired devices to the array
        if (pairedDevices.size() > 0) {
            findViewById(R.id.title_paired_devices).setVisibility(View.VISIBLE);//make title viewable
            for (BluetoothDevice device : pairedDevices) {
                pairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        } else {
            pairedDevicesArrayAdapter.add("no devices paired");
        }
    }

    // Set up on-click listener for the listview
    private OnItemClickListener mDeviceClickListener = new OnItemClickListener() {
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
            connectionStatus.setText("Connecting...");
            // Get the device MAC address, which is the last 17 chars in the View
            String info = ((TextView) v).getText().toString();
            String address = info.substring(info.length() - 17);

            // Make an intent to start next activity while taking an extra which is the MAC address.
            Intent i = new Intent(DeviceListActivity.this, ControllerActivity.class);
            i.putExtra(EXTRA_DEVICE_ADDRESS, address);
            startActivity(i);
        }
    };

    /**
     * Ask user to turn bluetooth on
     */
    private void bluetoothStatus() {
        // Check that device has Bluetooth and that it is turned on
        adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter == null) {
            Toast.makeText(getBaseContext(), "Device does not support Bluetooth", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            if (!adapter.isEnabled()) {  //Prompt user to turn on Bluetooth
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }
}