package com.example.bluetoothapp;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class DiscoverFrag extends Fragment {

    Button btndiscov;
    BluetoothAdapter bluetoothAdapter;
    Intent discoverIntent;
    TextView txtview;
    private static final int REQUEST_DISCOVER_BT =1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_discover, container, false);

        btndiscov = view.findViewById(R.id.btnDiscover);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        btndiscov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!bluetoothAdapter.isDiscovering()){
                    showToash("Making your device discoverable");
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    startActivityForResult(intent, REQUEST_DISCOVER_BT);
                }
            }
        });

        return view;
    }

    // function of toast message
    private void showToash(String msg){
        Toast.makeText(DiscoverFrag.super.getContext(), msg, Toast.LENGTH_LONG).show();
    }
}
