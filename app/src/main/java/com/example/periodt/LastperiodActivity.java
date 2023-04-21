package com.example.periodt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class LastperiodActivity extends AppCompatActivity {

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
                R.array.yesno_array, android.R.layout.simple_spinner_item);
        adapter_regular.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_regular.setAdapter(adapter_regular);

        // get continue button
        Button continue_btn = (Button) findViewById(R.id.continue_btn);
        continue_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                redirectCalendar();
            }
        });
    }

    private void redirectCalendar() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}