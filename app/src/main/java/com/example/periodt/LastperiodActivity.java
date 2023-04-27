package com.example.periodt;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

import java.text.SimpleDateFormat;
import java.util.Date;

public class LastperiodActivity extends AppCompatActivity {

    private DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.last_period);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

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
        // Last one
        EditText last_et = (EditText) findViewById(R.id.select_last_date);
        String last = last_et.getText().toString();
        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String today_str = sdf.format(today);
        // if no date has been introduced or the date is yet to come
        if(last.equals("") || last == null || last.compareTo(today_str) > 0){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            builder.setMessage(R.string.invalid_date)
                    .setTitle(R.string.error);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else{
            // Duration
            Spinner duration_sp = (Spinner) findViewById(R.id.period_duration);
            String duration = duration_sp.getSelectedItem().toString();

            // Regularity
            Spinner regularity_sp = (Spinner) findViewById(R.id.regular_period);
            String cycle = regularity_sp.getSelectedItem().toString();

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            String uid = prefs.getString("uid", "0");

            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("periodNotifSent", false);
            editor.putBoolean("fertileNotifSent", false);
            editor.apply();

            db.registerPeriod(last, duration, cycle, uid);

            redirectCalendar();
        }
    }
}