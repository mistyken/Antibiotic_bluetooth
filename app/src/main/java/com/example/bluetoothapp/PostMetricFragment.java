package com.example.bluetoothapp;

import androidx.lifecycle.ViewModelProviders;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class PostMetricFragment extends Fragment {

    private PostMetricViewModel mViewModel;

    public static PostMetricFragment newInstance() {
        return new PostMetricFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.post_metric_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PostMetricViewModel.class);

        Button getUser = view.findViewById(R.id.getUserButton);
        final Button postMetric = view.findViewById(R.id.postMetricButton);
        final EditText emailField2 = view.findViewById(R.id.emailField1);
        final EditText deviceId = view.findViewById(R.id.deviceIdField);
        final EditText heartRate = view.findViewById(R.id.heartRateField);

        postMetric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.postMetrics(emailField2.getText().toString(),
                        deviceId.getText().toString(),
                        heartRate.getText().toString(),
                        "12345678");
            }
        });
    }
}