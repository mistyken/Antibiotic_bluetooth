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
    Fragment1 fragment1;
    Fragment2 fragment2;
    PairFrag fragment3;
    DiscoverFrag frag4;
    DisconnectFrag disconnectFrag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spinner1);
        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new PairFrag();
        frag4 = new DiscoverFrag();
        disconnectFrag = new DisconnectFrag();

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
                        setFragment(fragment1);
                        break;
                    case 1:
                        setFragment(fragment2);
                        break;
                    case 2:
                        setFragment(fragment3);
                        break;
                    case 3:
                        setFragment(disconnectFrag);
                        break;
                    case 4:
                        setFragment(frag4);
                        break;
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
