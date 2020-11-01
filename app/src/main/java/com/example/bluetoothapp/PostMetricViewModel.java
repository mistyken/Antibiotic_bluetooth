package com.example.bluetoothapp;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.example.bluetoothapp.model.User;
import com.example.bluetoothapp.model.Metric;
import com.example.bluetoothapp.adapter.AntibioticAPI;

import java.util.Date;

public class PostMetricViewModel extends ViewModel {
    static final String BASE_URL = "https://m6fea98ao1.execute-api.us-west-1.amazonaws.com/dev/";

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    AntibioticAPI service = retrofit.create(AntibioticAPI.class);

    public void createUser(String email, String first_name, String last_name) {
        User user = new User();
        user.set_email(email);
        user.set_first_name(first_name);
        user.set_last_name(last_name);

        Call<User> call = service.createUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    public void getUser(String email) {
        Call<User> call =service.loadUser(email);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    public void postMetrics(String email, String deviceId, String heartRate, String datetime) {
        Metric metric = new Metric();
        metric.set_email(email);
        metric.set_device_id(deviceId);
        metric.set_heart_rate(heartRate);
        metric.set_datetime(datetime);

        Call<Metric> call = service.postMetric(metric);
        call.enqueue(new Callback<Metric>() {
            @Override
            public void onResponse(Call<Metric> call, Response<Metric> response) {

                Log.i("INFO", response.toString());
            }

            @Override
            public void onFailure(Call<Metric> call, Throwable t) {
                Log.e("ERROR", t.toString());
            }
        });
    }
}