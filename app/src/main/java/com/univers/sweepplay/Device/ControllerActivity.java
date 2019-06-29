package com.univers.sweepplay.Device;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.univers.sweepplay.R;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;


public class ControllerActivity extends Activity{

    ImageButton upBtn, downBtn;
    Button start;
    String command;

    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private OutputStream outStream = null;
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    public String newAddress = null;

    /**
     * Create activity
     * @param savedInstanceState
     * @return false in case of false commands
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);

        //Initialising buttons in the view
        upBtn = findViewById(R.id.upBtn);
        downBtn = findViewById(R.id.downBtn);
        start= findViewById(R.id.start);

        //getting the bluetooth adapter value and calling BluetoothState function
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothState();

        upBtn.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) //MotionEvent.ACTION_DOWN is when you hold a button down
                { command = "1";
                    try {
                        outStream.write(command.getBytes()); //transmits the value of command to the bluetooth module
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else
                if(event.getAction() == MotionEvent.ACTION_UP) //MotionEvent.ACTION_UP is when you release a button
                { command = "0";
                    try {
                        outStream.write(command.getBytes());
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });

        start.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) //MotionEvent.ACTION_DOWN is when you hold a button down
                { command = "3";
                    try {
                        outStream.write(command.getBytes()); //transmits the value of command to the bluetooth module
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else
                if(event.getAction() == MotionEvent.ACTION_UP) //MotionEvent.ACTION_UP is when you release a button
                { command = "0";
                    try {
                        outStream.write(command.getBytes());
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });



        downBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) //MotionEvent.ACTION_DOWN is when you hold a button down
                { command = "4";
                    try {
                        outStream.write(command.getBytes()); //transmits the value of command to the bluetooth module
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else
                if(event.getAction() == MotionEvent.ACTION_UP) //MotionEvent.ACTION_UP is when you release a button
                { command = "0";
                    try {
                        outStream.write(command.getBytes());
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });
    }

    /**
     * recreate activity to manage control
     * connection methods are here in case program goes into the background
     */
    @Override
    public void onResume(){
        super.onResume();
        //Get MAC address from DeviceListActivity
        Intent intent = getIntent();
        newAddress = intent.getStringExtra( DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        BluetoothDevice device = btAdapter.getRemoteDevice(newAddress);

        //try to create a bluetooth socket for communication
        try {
            btSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
        } catch (IOException e1) {
            Toast.makeText(getBaseContext(), "ERROR - Could not create Bluetooth socket", Toast.LENGTH_SHORT).show();
        }

        try { // Establish the connection.
            btSocket.connect();
        } catch (IOException e) {
            try {
                btSocket.close();        //If IO exception occurs attempt to close socket
            } catch (IOException e2) {
                Toast.makeText(getBaseContext(), "ERROR - Could not close Bluetooth socket", Toast.LENGTH_SHORT).show();
            }
        }

        try { // Create a data stream for talk to the device
            outStream = btSocket.getOutputStream();
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), "ERROR - Could not create bluetooth outstream", Toast.LENGTH_SHORT).show();
        }
        send("x"); //sending data x
    }

    /**
     * Close Bt socket to device
     */
    @Override
    public void onPause() {
        super.onPause();
        try     {
            btSocket.close();
        } catch (IOException e2) {
            Toast.makeText(getBaseContext(), "ERROR - Failed to close Bluetooth socket", Toast.LENGTH_SHORT).show();
        }
    }
    //takes the UUID and creates a communication socket
    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {

        return  device.createRfcommSocketToServiceRecord(MY_UUID);
    }

    /**
     * Ask user to turn bluetooth on
     */
    private void bluetoothState() {
        // Check device has Bluetooth and that it is turned on
        if(btAdapter==null) {
            Toast.makeText(getBaseContext(), "ERROR - Device does not support bluetooth", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            if (btAdapter.isEnabled()) {
            } else {
                //ask user to turn on Bluetooth
                Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(i, 1);
            }
        }
    }

    /**
     * Method to send data
     *
     * @param message
     */
    private void send(String message) {
        byte[] buffer = message.getBytes();
        try {
            //attempt to place data on the outstream to the BT device
            outStream.write(buffer);
        } catch (IOException e) {
            //if the sending fails this is most likely because device is no longer there
            Toast.makeText(getBaseContext(), "ERROR - Device not found", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
