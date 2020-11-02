package com.example.bluetoothapp;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.bluetoothapp.model.User;
import com.example.bluetoothapp.model.Metric;
import com.example.bluetoothapp.Network.AntibioticAPIUtils;
import com.example.bluetoothapp.Network.AntibioticCallbacks;

public class PostMetricViewModel extends ViewModel {

    public void createUser(String email, String first_name, String last_name) {
        User user = new User();
        user.set_email(email);
        user.set_first_name(first_name);
        user.set_last_name(last_name);

        AntibioticAPIUtils.getAPIService().createUser(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.i("[INFO] createUser", response.toString());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("[ERROR] CreateUser", t.toString());
            }
        });
    }

    public void getUser(String email, @Nullable final AntibioticCallbacks callbacks) {
        AntibioticAPIUtils.getAPIService().loadUser(email).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();

                    if (callbacks != null) {
                        callbacks.onSuccess(String.format("%s %s %s", user.get_email(), user.get_first_name(), user.get_last_name()));
                    }
                } else {
                    if (callbacks != null) {
                        callbacks.onSuccess("Unable to retrieve user");
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                if (callbacks != null)
                    callbacks.onError(t);
            }
        });
    }

    public void postMetrics(String email, String deviceId, String heartRate, String datetime) {
        Metric metric = new Metric();
        metric.set_email(email);
        metric.set_device_id(deviceId);
        metric.set_heart_rate(heartRate);
        metric.set_datetime(datetime);

        AntibioticAPIUtils.getAPIService().postMetric(metric).enqueue(new Callback<Metric>() {
            @Override
            public void onResponse(Call<Metric> call, Response<Metric> response) {
                Log.i("[INFO] postMetrics", response.toString());
            }

            @Override
            public void onFailure(Call<Metric> call, Throwable t) {
                Log.e("[ERROR] postMetrics", t.toString());
            }
        });
    }
}