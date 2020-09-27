package com.example.bluetoothapp;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btn1, btn2, btn3, btn4;
    BluetoothAdapter bluetoothAdapter;
    TextView scantxt;
    Intent btEnableIntent;
    Intent btnScanIntent;
    int requestCodeForEnable;
    IntentFilter scanIntentFilter = new IntentFilter(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = findViewById(R.id.btn_Discover);
        btn2 = findViewById(R.id.btn_off);
        btn3 = findViewById(R.id.btn_listpaireddevices);
        btn4 = findViewById(R.id.btn_scan);
        scantxt = findViewById(R.id.scanTxt);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        btEnableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        btnScanIntent = new Intent(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);

        requestCodeForEnable=1;

        bluetoothONmethod();
        bluetoothOFmethod();

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent discoverable = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                discoverable.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 120);
                startActivity(discoverable);
            }
        });

        registerReceiver(scanReceiver, scanIntentFilter);
    }



    BroadcastReceiver scanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED)){
                int modevValue = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE, BluetoothAdapter.ERROR);
                if (modevValue==BluetoothAdapter.SCAN_MODE_CONNECTABLE)
                {
                    scantxt.setText("This device is not in discoverable");
                }else if(modevValue==BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE)
                {
                    scantxt.setText("This device is discoverable");
                }else if(modevValue==BluetoothAdapter.SCAN_MODE_NONE){
                    scantxt.setText("This device is not in discoverable mode");
                }else{
                    scantxt.setText("Error  ");
                }
            };
        }
    };


    protected void onActivityResult (int requestCode, int resultCode,  Intent data) {
        // super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == requestCodeForEnable) {
            if (requestCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(), "BLUETOOTH IS ENABLED", Toast.LENGTH_LONG).show();
            } else if (requestCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "BLUETOOTH ENABLING CANCELLED", Toast.LENGTH_LONG).show();
            }
        }

    }

    private void bluetoothOFmethod(){
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bluetoothAdapter.isEnabled())
                {
                    bluetoothAdapter.disable();
                    Toast.makeText(getApplicationContext(), "Bluetooth has been disabled", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Bluetooth was not enabled", Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    private void bluetoothONmethod() {
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bluetoothAdapter == null) {
                    Toast.makeText(getApplicationContext(), "Bluetooth does not support on this device", Toast.LENGTH_LONG).show();

                } else {
                    if (bluetoothAdapter != null) {
                        startActivityForResult(btEnableIntent, requestCodeForEnable);

                    }
                }
                Toast.makeText(getApplicationContext(), "Bluetooth has been enabled", Toast.LENGTH_LONG).show();

            }

        });

    }
}
