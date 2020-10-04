package com.example.bluetoothapp;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class DisconnectFragment extends Fragment {
    Button btndisonn;
    BluetoothAdapter myAdapter;
    Intent disconnIntent;
    ImageView imageView;
    TextView txtview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_disconnect, container, false);

        btndisonn = view.findViewById(R.id.btnOff);
        myAdapter = BluetoothAdapter.getDefaultAdapter();
        imageView = view.findViewById(R.id.bbimage);

        btndisonn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myAdapter.isEnabled()) {
                    myAdapter.disable();
                    showToast("Turning off Bluetooth");
                    imageView.setImageResource(R.drawable.ic_action_off);
                } else {
                    showToast("Bluetooth is already off ");
                }
            }
        });

        return view;
    }

    // function of toast message
    private void showToast(String msg) {
        Toast.makeText(DisconnectFragment.super.getContext(), msg, Toast.LENGTH_LONG).show();
    }
}
