package com.example.periodt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
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

    private static final String CHANNEL_ID = "PERIOD";
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
        SharedPreferences.Editor editor = prefs.edit();
        uid = prefs.getString("uid", "0");

        // update next period
        String lastPeriod = db.getLastPeriod(uid);
        String cycle = db.getCycle(uid);
        String duration = db.getDuration(uid);
        boolean updated = updateNextPeriod(lastPeriod, duration, cycle);
        if (updated) {
            editor.putBoolean("periodNotifSent", false);
            editor.putBoolean("periodNotifSent", false);
        }

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        Boolean periodNotifSent = prefs.getBoolean("periodNotifSent", false);

        // check if the current day is near the period starting date
        if((Integer.parseInt(daysTillNextPeriod()) <= 5) && !periodNotifSent){
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.blood)
                    .setContentTitle("MENSTRUACIÓN")
                    .setContentText("¡Quedan menos de 5 días para tu menstruación!")
                    .setPriority(NotificationCompat.PRIORITY_HIGH);

            NotificationManager mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            Notification myNotification = builder.build();
            mNotifyManager.notify(0, myNotification);

            // Update the sharedPreferences
            editor.putBoolean("periodNotifSent", true);
        }

        Boolean fertileNotifSent = prefs.getBoolean("fertileNotifSent", false);
        // check if the current day is near the fertile period starting date
        if((Integer.parseInt(daysTillNextFertile()) <= 5) && !fertileNotifSent){
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.blood)
                    .setContentTitle("FERTILIDAD")
                    .setContentText("¡Quedan menos de 5 días para tu periodo fértil!")
                    .setPriority(NotificationCompat.PRIORITY_HIGH);

            NotificationManager mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            Notification myNotification = builder.build();
            mNotifyManager.notify(1, myNotification);

            // Update the sharedPreferences
            editor.putBoolean("fertileNotifSent", true);
        }
        editor.apply();
    }

    private String daysTillNextFertile() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        try{
            Date d1 = new SimpleDateFormat("dd/MM/yyyy").parse(formatter.format(date));
            Date d2 = new SimpleDateFormat("dd/MM/yyyy").parse(nextFertile());

            long toNext = (d2.getTime() - d1.getTime()) / (1000 * 3600 * 24);
            int toLast = daysTillPastFertile();
            if (toLast <= 0)
                return String.valueOf(toNext);
            return String.valueOf(toLast);

        } catch(ParseException e){
            throw new RuntimeException(e);
        }
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
        Log.i("LAST_PERIOD", lastPeriod);
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

    private String nextFertile(){
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
        int nextFertile = (Integer.parseInt(cycle) < 28) ? (10 - (28 - Integer.parseInt(cycle))): (10 + (Integer.parseInt(cycle) - 28));

        c.add(Calendar.DATE, nextPeriod + nextFertile);
        return sdf.format(c.getTime());
    }

    private String pastFertile(){
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
        int nextFertile = (Integer.parseInt(cycle) < 28) ? (10 - (28 - Integer.parseInt(cycle))): (10 + (Integer.parseInt(cycle) - 28));

        c.add(Calendar.DATE, nextFertile);
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

    private int daysTillPastFertile(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        try{
            Date d1 = new SimpleDateFormat("dd/MM/yyyy").parse(formatter.format(date));
            Date d3 = new SimpleDateFormat("dd/MM/yyyy").parse(pastFertile());

            long toLast = (d3.getTime() - d1.getTime()) / (1000 * 3600 * 24);

            return (int)toLast;
        } catch(ParseException e){
            throw new RuntimeException(e);
        }
    }

    public void goToFertile() {
        String nextDate = pastFertile();
        if (daysTillPastFertile() <= 0)
            nextDate = nextFertile();
        try {
            calendar.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(nextDate).getTime(), true, true);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean updateNextPeriod(String last, String duration, String cycle){
        // check if saved date is already passed
        if(Integer.parseInt(daysTillNextPeriod()) + Integer.parseInt(duration)<= 0){
            String newLast = nextPeriod();
            db.registerPeriod(newLast, duration, cycle, uid);
            return true;
        }
        return false;
    }
}