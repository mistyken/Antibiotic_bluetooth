package com.example.bluetoothapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;


public class DiscoverFragment extends Fragment {
    Button btndiscov;
    Button btnScan;
    BluetoothAdapter bluetoothAdapter;
    private static final int REQUEST_DISCOVER_BT = 1;
    private ArrayAdapter<String> foundDevicesAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        requireActivity().registerReceiver(discoveryReceiver, filter);

        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        requireActivity().registerReceiver(discoveryReceiver, filter);

        return inflater.inflate(R.layout.fragment_discover, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        btndiscov = view.findViewById(R.id.btnDiscover);
        btnScan = view.findViewById(R.id.btnScan);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        foundDevicesAdapter = new ArrayAdapter<>(this.getContext(), R.layout.device_name);
        ArrayAdapter<String> pairedDevicesAdapter = new ArrayAdapter<>(this.getContext(), R.layout.device_name);

        ListView foundDevicesListView = view.findViewById(R.id.found_devices);
        foundDevicesListView.setAdapter(foundDevicesAdapter);
        foundDevicesListView.setOnItemClickListener(deviceClickListener);

        ListView pairedDevicesListView = view.findViewById(R.id.paired_devices);
        pairedDevicesListView.setAdapter(pairedDevicesAdapter);
        pairedDevicesListView.setOnItemClickListener(deviceClickListener);

        Set<BluetoothDevice> pairDevices = bluetoothAdapter.getBondedDevices();

        if (pairDevices.size() >0) {
            view.findViewById(R.id.paired_devices).setVisibility(View.VISIBLE);
            for (BluetoothDevice device : pairDevices) {
                pairedDevicesAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        } else {
                pairedDevicesAdapter.add("No Devices have been paired");
        }

        btndiscov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bluetoothAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
                    showToast("Making your device discoverable");
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 240);
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

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (bluetoothAdapter != null) {
            bluetoothAdapter.cancelDiscovery();
        }

        requireActivity().unregisterReceiver(discoveryReceiver);
    }

    private AdapterView.OnItemClickListener deviceClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            bluetoothAdapter.cancelDiscovery();

            String info = ((TextView) view).getText().toString();
            String address = info.substring(info.length() - 17);

            Intent intent = new Intent();
            intent.putExtra("device_address", address);

            showToast(address);
        }
    };

    private final BroadcastReceiver discoveryReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if(BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device != null && device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    foundDevicesAdapter.add(device.getName() + "\n" + device.getAddress());
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                if (foundDevicesAdapter.getCount() == 0) {
                    foundDevicesAdapter.add("No Devices found");
                }
            }
        }
    };

    // function of toast message
    private void showToast(String msg) {
        Toast.makeText(DiscoverFragment.super.getContext(), msg, Toast.LENGTH_LONG).show();
    }
}
