package com.example.bluetoothapp;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import static android.app.Activity.RESULT_OK;


public class BleMainActivity extends Fragment {

    private Button start, stop, find;
    private static final int ENABLE_BLUETOOTH_REQUEST = 17;
    Context context =getContext();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ble_main_activity, container, false);

        find = view.findViewById(R.id.find);
        start = view.findViewById(R.id.client);
        stop = view.findViewById(R.id.stop);
        context = this.getContext();

        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, DevicesListActivity.class));

            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.stopService(new Intent(context, GattService.class));
                updateUi();
            }
        });


        start.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
                BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
                if(bluetoothAdapter == null){
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Bluetooth not supported");
                    builder.setMessage("Bluetooth needs to be enabled").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                           dialog.cancel();
                        }
                    });
                    builder.show();
                } else if(!bluetoothAdapter.isEnabled()){
                    Intent enableBtlntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtlntent, ENABLE_BLUETOOTH_REQUEST);
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (!bluetoothAdapter.isMultipleAdvertisementSupported()){
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Error");
                        builder.setMessage("Use a supported Multi Advertisement chipset").setPositiveButton
                                ("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                builder.show();
                    }else {
                        start();
                    }
                }

            }
        });
        updateUi();

        return view;
    }

    private void start() {
        Intent intent = new Intent(context, GattService.class);
       context.startService(intent);
       getActivity().finish();
    }

    private void updateUi(){
        if(isServiceRunning(GattService.class)){
            start.setEnabled(false);
            stop.setEnabled(true);
        }else{
            start.setEnabled(true);
            stop.setEnabled(false);
        }
    }

    private boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ENABLE_BLUETOOTH_REQUEST) {
            if (requestCode == RESULT_OK) {
                start();
            } else {
                getActivity().finish();
            }
        }
    }



}
