package com.example.periodt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class LastperiodActivity extends AppCompatActivity {

    private DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.last_period);

        // set spinners items
        Spinner spinner_duration = (Spinner) findViewById(R.id.period_duration);
        ArrayAdapter<CharSequence> adapter_duration = ArrayAdapter.createFromResource(this,
                R.array.duration_array, android.R.layout.simple_spinner_item);
        adapter_duration.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_duration.setAdapter(adapter_duration);

        Spinner spinner_regular = (Spinner) findViewById(R.id.regular_period);
        ArrayAdapter<CharSequence> adapter_regular = ArrayAdapter.createFromResource(this,
                R.array.regular_array, android.R.layout.simple_spinner_item);
        adapter_regular.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_regular.setAdapter(adapter_regular);

        // get continue button
        Button continue_btn = (Button) findViewById(R.id.continue_btn);
        continue_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                submitData();
                redirectCalendar();
            }
        });

        // get database
        db = new DBHandler(LastperiodActivity.this);
    }

    private void redirectCalendar() {
        Intent intent = new Intent(this, CalendarActivity.class);
        startActivity(intent);
    }
    private void submitData(){
        // Duration
        Spinner duration_sp = (Spinner) findViewById(R.id.period_duration);
        String duration = duration_sp.getSelectedItem().toString();

        // Last one
        EditText last_et = (EditText) findViewById(R.id.select_last_date);
        String last = last_et.getText().toString();

        // Regularity
        Spinner regularity_sp = (Spinner) findViewById(R.id.regular_period);
        String cycle = regularity_sp.getSelectedItem().toString();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String uid = prefs.getString("uid", "0");

        // Submit the data
        Log.i("DURATION", duration);
        Log.i("LAST_PERIOD", last);
        Log.i("CYCLE", cycle);
        Log.i("UID", uid);

        db.registerPeriod(last, duration, cycle, uid);
    }
}