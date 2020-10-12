package com.example.bluetoothapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class DiscoverFragment extends Fragment {

    Button btndiscov;
    BluetoothAdapter bluetoothAdapter;
    private static final int REQUEST_DISCOVER_BT = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final BroadcastReceiver discoveryFinishReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();

                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                        showToast(device.getName() + " is paired. Address is " + device.getAddress());
                    }
                } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                        showToast("Discovery completed");
                }
            }
        };

        View view = inflater.inflate(R.layout.fragment_discover, container, false);

        btndiscov = view.findViewById(R.id.btnDiscover);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        btndiscov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bluetoothAdapter.isDiscovering()) {
                    showToast("Making your device discoverable");
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 120);
                    startActivityForResult(intent, REQUEST_DISCOVER_BT);
                }
            }
        });

        return view;
    }

    // function of toast message
    private void showToast(String msg) {
        Toast.makeText(DiscoverFragment.super.getContext(), msg, Toast.LENGTH_LONG).show();
    }
}
