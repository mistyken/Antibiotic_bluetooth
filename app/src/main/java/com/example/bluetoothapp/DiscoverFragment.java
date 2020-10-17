package com.example.bluetoothapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


public class DiscoverFragment extends Fragment {
    Button btndiscov;
    Button btnScan;
    BluetoothAdapter bluetoothAdapter;
    private static final int REQUEST_DISCOVER_BT = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_discover, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        btndiscov = view.findViewById(R.id.btnDiscover);
        btnScan = view.findViewById(R.id.btnScan);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        btndiscov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bluetoothAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
                    showToast("Making your device discoverable");
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 120);
                    startActivityForResult(intent, REQUEST_DISCOVER_BT);
                }
            }
        });

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bluetoothAdapter.isDiscovering()) {
                    bluetoothAdapter.cancelDiscovery();
                }

                showToast("Scanning nearby devices");
                bluetoothAdapter.startDiscovery();
            }
        });
    }

    // function of toast message
    private void showToast(String msg) {
        Toast.makeText(DiscoverFragment.super.getContext(), msg, Toast.LENGTH_LONG).show();
    }
}
