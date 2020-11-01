package com.example.bluetoothapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    Spinner spinner;
    DisclaimerFragment disclaimerFragment;
    SettingFragment settingFragment;
    PairFragment pairFragment;
    DiscoverFragment discoverFragment;
    DisconnectFragment disconnectFrag;
    BleMainActivity bleMainActivity;
    PostMetricFragment postMetricFrag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spinner1);
        disclaimerFragment = new DisclaimerFragment();
        settingFragment = new SettingFragment();
        pairFragment = new PairFragment();
        discoverFragment = new DiscoverFragment();
        disconnectFrag = new DisconnectFragment();
        postMetricFrag = new PostMetricFragment();
        bleMainActivity = new BleMainActivity();


        ArrayAdapter<String> myadapter = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.frag_options));

        myadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myadapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        setFragment(disclaimerFragment);
                        break;
                    case 1:
                        setFragment(settingFragment);
                        break;
                    case 2:
                        setFragment(pairFragment);
                        break;
                    case 3:
                        setFragment(disconnectFrag);
                        break;
                    case 4:
                        setFragment(discoverFragment);
                        break;
                    case 5:
                        setFragment(bleMainActivity);
                        break;
                    case 6:
                        setFragment(postMetricFrag);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void setFragment(Fragment frag) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.mainFrag, frag);
        transaction.commit();
    }
}
