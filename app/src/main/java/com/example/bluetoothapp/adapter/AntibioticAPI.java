package com.example.bluetoothapp.adapter;

import com.example.bluetoothapp.model.Metric;
import com.example.bluetoothapp.model.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Body;

public interface AntibioticAPI {
    @GET("users/{email}")
    Call<User> loadUser(@Path("email") String email);

    @POST("users")
    Call<User> createUser(@Body User user);

    @POST("metrics")
    Call<Metric> postMetric(@Body Metric metric);
}
