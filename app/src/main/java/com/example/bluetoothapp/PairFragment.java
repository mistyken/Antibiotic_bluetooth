package com.example.bluetoothapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;


public class PairFragment extends Fragment {

    Button btnpair;
    BluetoothAdapter bluetoothAdapter;
    Intent discoverIntent;
    TextView txtview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pair, container, false);

        btnpair = view.findViewById(R.id.btnPairedDevices);
        txtview = view.findViewById(R.id.txPair);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        btnpair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bluetoothAdapter.isEnabled()) {
                    txtview.setText("Paired Devices: ");
                    Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();
                    for (BluetoothDevice device : devices) {
                        txtview.append("\nDevice: " + device.getName() + "," + device);
                    }
                } else {
                    showToast("Turn on bluetooth to get paired devices");
                }
            }
        });



        return view;
    }

    // function of toast message
    private void showToast(String msg) {
        Toast.makeText(PairFragment.super.getContext(), msg, Toast.LENGTH_LONG).show();
    }
}
