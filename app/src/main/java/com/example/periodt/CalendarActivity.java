package com.example.periodt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

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

    private BottomNavigationView navBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);

        db = new DBHandler(CalendarActivity.this);

        // get user uid
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        uid = prefs.getString("uid", "0");

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        navBar = findViewById(R.id.navigation_bar);
        navBar.setSelectedItemId(R.id.it_calendar);

        navBar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.it_calendar){
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
                    startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                }
                return false;
            }
        });

        // update next period
        String lastPeriod = db.getLastPeriod(uid);
        String cycle = db.getCycle(uid);
        String duration = db.getDuration(uid);
        boolean updated = updateNextPeriod(lastPeriod, duration, cycle);
        if (updated) {
            editor.putBoolean("periodNotifSent", false);
            editor.putBoolean("fertileNotifSent", false);
            editor.apply();
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

        // get the settings for the notifications
        boolean period_notif = prefs.getBoolean("period_notif", true);
        int days_period_notif = prefs.getInt("days_period_notif", 5);
        Log.i("notif", String.valueOf(period_notif));
        Log.i("till", String.valueOf(daysTillNextPeriod()));
        Log.i("days_notif", String.valueOf(days_period_notif));
        Log.i("sent", String.valueOf(periodNotifSent));
        // check if the current day is near the period starting date
        if(period_notif && (Integer.parseInt(daysTillNextPeriod()) <= days_period_notif) && !periodNotifSent){
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
            editor.apply();
        }

        // get the settings for the notifications
        boolean fertile_notif = prefs.getBoolean("fertile_notif", true);
        int days_fertile_notif = prefs.getInt("days_fertile_notif", 5);
        Boolean fertileNotifSent = prefs.getBoolean("fertileNotifSent", false);
        Log.i("notif", String.valueOf(fertile_notif));
        Log.i("till", String.valueOf(daysTillNextFertile()));
        Log.i("days_notif", String.valueOf(days_fertile_notif));
        Log.i("sent", String.valueOf(fertileNotifSent));
        // check if the current day is near the fertile period starting date
        if(fertile_notif && (Integer.parseInt(daysTillNextFertile()) <= days_fertile_notif) && !fertileNotifSent){
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
            editor.apply();
        }
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