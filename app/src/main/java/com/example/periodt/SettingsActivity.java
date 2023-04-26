package com.example.periodt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // preferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();

        // fertile dropdown
        Spinner spinner_fertile = (Spinner) findViewById(R.id.fertile_notif_spinner);
        ArrayAdapter<CharSequence> adapter_fertile = ArrayAdapter.createFromResource(this,
                R.array.notif_array, android.R.layout.simple_spinner_item);
        adapter_fertile.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_fertile.setAdapter(adapter_fertile);
        spinner_fertile.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // take the position and add it to preferences
                editor.putInt("days_fertile_notif", i + 1);
                editor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        // fertile toggle
        Switch switch_fertile = (Switch) findViewById(R.id.fertile_notif_switch);
        boolean fertile_notif = prefs.getBoolean("fertile_notif", true);
        if(fertile_notif){
            switch_fertile.setChecked(true);
        }
        else{
            switch_fertile.setChecked(false);
        }
        switch_fertile.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editor.putBoolean("fertile_notif", true);
                } else {
                    editor.putBoolean("fertile_notif", false);
                }
                editor.apply();
            }
        });

        // period dropdown
        Spinner spinner_period = (Spinner) findViewById(R.id.period_notif_spinner);
        ArrayAdapter<CharSequence> adapter_period = ArrayAdapter.createFromResource(this,
                R.array.notif_array, android.R.layout.simple_spinner_item);
        adapter_period.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_period.setAdapter(adapter_period);
        spinner_period.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // take the position and add it to preferences
                editor.putInt("days_period_notif", i + 1);
                editor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        // period toggle
        Switch switch_period = (Switch) findViewById(R.id.period_notif_switch);
        boolean period_notif = prefs.getBoolean("period_notif", true);
        if(period_notif){
            switch_period.setChecked(true);
        }
        else{
            switch_period.setChecked(false);
        }
        switch_period.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editor.putBoolean("period_notif", true);
                } else {
                    editor.putBoolean("period_notif", false);
                }
                editor.apply();
            }
        });
    }
}