package com.example.bluetoothapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ServicesList extends AppCompatActivity {
    private ListView servicesList;
    private LinearLayout messageContainer;
    private BluetoothDevice device;
    private List<String> servicesListNames;
    private ArrayAdapter<String> servicesAdapter;
    private Handler handler;
    private List<BluetoothGattService> services;
    private BluetoothGatt currentGatt;
    private EditText message;
    private Button send;
    private BluetoothGattCharacteristic characteristic;
    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_list);

        handler = new Handler();
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("Advertisement data");
        device = getIntent().getExtras().getParcelable("device");
        servicesList = findViewById(R.id.services_list);

        messageContainer = findViewById(R.id.message_container);
        message = findViewById(R.id.message);
        send = findViewById(R.id.send);

        currentGatt =device.connectGatt(this, false, gattCallback);
        dialog.show();
        servicesListNames = new ArrayList<>();
        servicesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        servicesList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!message.getText().toString().trim().isEmpty()) {
                    characteristic.setValue(message.getText().toString().getBytes());
                    currentGatt.writeCharacteristic(characteristic);
                    message.setText("");
                }
            }
        });

    }

    public BluetoothGattCallback gattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            super.onConnectionStateChange(gatt, status, newState);
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                currentGatt.discoverServices();
            } else {
                if (dialog.isShowing()) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            dialog.hide();
                        }
                    });
                }
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicRead(gatt, characteristic, status);
        }


        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
            gatt.executeReliableWrite();
        }


        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicChanged(gatt, characteristic);
        }

        @Override
        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorRead(gatt, descriptor, status);
        }


        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorWrite(gatt, descriptor, status);
        }


        @Override
        public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
            super.onReliableWriteCompleted(gatt, status);
        }

        @Override
        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
            super.onReadRemoteRssi(gatt, rssi, status);
        }


        @Override
        public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
            super.onMtuChanged(gatt, mtu, status);
        }

    };

    public void onItemClick (AdapterView<?> parent, View view, int position, long id){
        if(services != null){
            BluetoothGattService notificationService = services.get(position);
            if(notificationService.getUuid().equals(GattService.SERVICE_UUID)){
                characteristic = notificationService.getCharacteristic(GattService.CHAR_UUID);
                if(characteristic != null) {
                    messageContainer.setVisibility(View.VISIBLE);
                }

            }else{
                Toast.makeText(this, "Can't connect to this service", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
