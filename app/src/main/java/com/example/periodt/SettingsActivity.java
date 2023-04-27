package com.example.periodt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class SettingsActivity extends AppCompatActivity {

    private BottomNavigationView navBar;
    private DBHandler db;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        db = new DBHandler(SettingsActivity.this);

        // get user uid
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        uid = prefs.getString("uid", "0");

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // navbar
        navBar = findViewById(R.id.navigation_bar);
        navBar.setSelectedItemId(R.id.it_settings);

        navBar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.it_calendar){
                    startActivity(new Intent(getApplicationContext(), CalendarActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                }
                else if (item.getItemId() == R.id.it_tracker){
                    if (db.alreadyTracked(uid)){
                        showMessage();
                        return false;
                    }
                    else {
                        startActivity(new Intent(getApplicationContext(), TrackerActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    }
                }
                else if (item.getItemId() == R.id.it_settings){
                    return true;
                }
                return false;
            }
        });

        // fertile dropdown
        Spinner spinner_fertile = (Spinner) findViewById(R.id.fertile_notif_spinner);
        ArrayAdapter<CharSequence> adapter_fertile = ArrayAdapter.createFromResource(this,
                R.array.notif_array, android.R.layout.simple_spinner_item);
        adapter_fertile.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_fertile.setAdapter(adapter_fertile);
        spinner_fertile.setSelection(prefs.getInt("days_fertile_notif", 5) - 1);
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
        spinner_period.setSelection(prefs.getInt("days_period_notif", 5) - 1);
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

        // modify period btn
        Button modify_period_btn = (Button) findViewById(R.id.modify_period_btn);
        modify_period_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LastperiodActivity.class);
                startActivity(intent);
            }
        });

        // logout btn
        Button logout_btn = (Button) findViewById(R.id.logout_btn);
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putBoolean("isLogged", false);
                editor.apply();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.setMessage(R.string.already_tracked)
                .setTitle(R.string.warning);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}