package com.example.bluetoothapp;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.widget.Toast;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import static android.app.Activity.RESULT_OK;


public class SettingFragment extends Fragment {
    Button btn1;
    BluetoothAdapter bluetoothAdapter;
    Intent btEnableIntent;
    TextView txtview;
    ImageView mImageV;
    private static final int REQUEST_ENABLE_BT = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        btn1 = view.findViewById(R.id.btn_Enable);
        txtview = view.findViewById(R.id.textView2);
        mImageV = view.findViewById(R.id.bluetoothimage);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // check if bluetooth is available or not
        if (bluetoothAdapter == null) {
            txtview.setText("Bluetooth is not available");
        } else {
            txtview.setText("Bluetooth is availbale");
        }

        // set image according to bluetooth status
        if (bluetoothAdapter.isEnabled()) {
            mImageV.setImageResource(R.drawable.ic_action_on);
        } else {
            mImageV.setImageResource(R.drawable.ic_action_off);
        }

        // enable bluetooth by clicking on the btn1
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bluetoothAdapter.isEnabled()) {
                    showToash("Tunning on Bluetooth");
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent, REQUEST_ENABLE_BT);
                } else {
                    showToash("Bluetooth is already enabled");
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                if (requestCode == RESULT_OK) {
                    mImageV.setImageResource(R.drawable.ic_action_on);
                    showToash("Bluetooth is on");
                } else {
                    showToash("Could't bluetooth");
                }
                break;
        }


        super.
                onActivityResult(requestCode, resultCode, data);
    }

    // function of toast message
    private void showToash(String msg) {
        Toast.makeText(SettingFragment.super.getContext(), msg, Toast.LENGTH_LONG).show();
    }
}
