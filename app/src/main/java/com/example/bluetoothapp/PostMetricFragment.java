package com.example.bluetoothapp;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import java.util.Calendar;
import java.util.TimeZone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bluetoothapp.Network.AntibioticCallbacks;

public class PostMetricFragment extends Fragment {

    private PostMetricViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.post_metric_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PostMetricViewModel.class);
        final TimeZone timeZone = TimeZone.getTimeZone("UTC");

        final TextView userInfoView = view.findViewById(R.id.userInfoView);
        final EditText emailField1 = view.findViewById(R.id.emailField1);
        final Button getUser = view.findViewById(R.id.getUserButton);
        final EditText firstName = view.findViewById(R.id.firstName);
        final EditText lastName = view.findViewById(R.id.lastName);
        final Button createUser = view.findViewById(R.id.createUser);
        final Button postMetric = view.findViewById(R.id.postMetricButton);
        final EditText emailField2 = view.findViewById(R.id.emailField2);
        final EditText deviceId = view.findViewById(R.id.deviceIdField);
        final EditText heartRate = view.findViewById(R.id.heartRateField);

        postMetric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailField2.getText().toString();
                String deviceID = deviceId.getText().toString();
                String heart_rate = heartRate.getText().toString();
                long currentTime = Calendar.getInstance(timeZone).getTimeInMillis();

                mViewModel.postMetrics(email, deviceID, heart_rate, String.valueOf(currentTime));
            }
        });

        createUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailField1.getText().toString();
                String first_name = firstName.getText().toString();
                String last_name = lastName.getText().toString();

                mViewModel.createUser(email, first_name, last_name);
            }
        });

        getUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailField1.getText().toString();

                mViewModel.getUser(email, new AntibioticCallbacks() {
                    @Override
                    public void onSuccess(@NonNull String data) {
                        userInfoView.setText(data);
                    }

                    @Override
                    public void onError(@NonNull Throwable throwable) {

                    }
                });
            }
        });
    }
}