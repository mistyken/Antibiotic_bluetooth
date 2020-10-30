package com.example.bluetoothapp.model;

import java.util.Date;

public class Metric {
    String email;
    String device_id;
    String datetime;
    String heart_rate;

    public String get_email() {
        return email;
    }

    public void set_email(String email) {
        this.email = email;
    }

    public String get_device_id() {
        return device_id;
    }

    public void set_device_id(String device_id) {
        this.device_id = device_id;
    }

    public String get_heart_rate() {
        return heart_rate;
    }

    public void set_heart_rate(String device_id) {
        this.heart_rate = heart_rate;
    }

    public String get_datetime() {
        return datetime;
    }

    public void set_datetime(String datetime) {
        this.datetime = datetime;
    }
}
