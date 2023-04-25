package com.example.periodt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class CalendarActivity extends AppCompatActivity {

    private DBHandler db;
    private CalendarView calendar;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);

        db = new DBHandler(CalendarActivity.this);
        // get user uid
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        uid = prefs.getString("uid", "0");

        // update next period
        String lastPeriod = db.getLastPeriod(uid);
        String cycle = db.getCycle(uid);
        String duration = db.getDuration(uid);
        updateNextPeriod(lastPeriod, duration, cycle);

        // get calendar
        calendar = (CalendarView) findViewById(R.id.calendarView);

        Button nextPeriod = (Button) findViewById(R.id.next_period_btn);
        String daysTillNextPeriod = daysTillNextPeriod();
        nextPeriod.setText(nextPeriod.getText().toString() + " " + daysTillNextPeriod);
        nextPeriod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToNextPeriod();
            }
        });

        Button nextFertile = (Button) findViewById(R.id.next_fertile_btn);
        String daysTillNextFertile = daysTillNextFertile();
        nextFertile.setText(nextFertile.getText().toString() + " " + daysTillNextFertile);
        nextFertile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToFertile();
            }
        });
    }

    private String daysTillNextFertile() {
        String lastPeriod = db.getLastPeriod(uid);
        String cycle = db.getCycle(uid);

        // calculate next period based on user-data
        Log.i("ciclo", cycle);
        int nextFertile = (Integer.parseInt(cycle) < 28) ? (10 - (28 - Integer.parseInt(cycle))): (10 + (Integer.parseInt(cycle) - 28));
        return String.valueOf(nextFertile);
    }

    private String daysTillNextPeriod() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        try{
            Date d1 = new SimpleDateFormat("dd/MM/yyyy").parse(formatter.format(date));
            Date d2 = new SimpleDateFormat("dd/MM/yyyy").parse(nextPeriod());
            long ret = (d2.getTime() - d1.getTime()) / (1000 * 3600 * 24);
            return String.valueOf(ret);
        } catch(ParseException e){
            throw new RuntimeException(e);
        }
    }

    private String nextPeriod(){
        String lastPeriod = db.getLastPeriod(uid);
        String cycle = db.getCycle(uid);
        String duration = db.getDuration(uid);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(lastPeriod));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        // calculate next period based on user-data
        int nextPeriod = Integer.parseInt(cycle);

        c.add(Calendar.DATE, nextPeriod);
        return sdf.format(c.getTime());
    }

    public void goToNextPeriod(){
        String nextDate = nextPeriod();
        try {
            calendar.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(nextDate).getTime(), true, true);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void goToFertile() {
        String lastPeriod = db.getLastPeriod(uid);
        String cycle = db.getCycle(uid);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(lastPeriod));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        String nextFertile = daysTillNextFertile();
        c.add(Calendar.DATE, Integer.parseInt(nextFertile));
        String nextDate = sdf.format(c.getTime());

        try {
            calendar.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(nextDate).getTime(), true, true);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateNextPeriod(String last, String duration, String cycle){
        // check if saved date is already passed
        String newLast = last;
        if(Integer.parseInt(daysTillNextPeriod()) <= 0){
            newLast = nextPeriod();
        }
        db.registerPeriod(newLast, duration, cycle, uid);
    }
}