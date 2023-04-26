package com.example.periodt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {

    private DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBHandler(MainActivity.this);

        // Check if logged
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isLogged = prefs.getBoolean("isLogged", false);
        Intent intent;
        if(isLogged) {
            intent = new Intent(this, SignupActivity.class);
            //intent = new Intent(this, LastperiodActivity.class);
        }
        else{
            intent = new Intent(this, SignupActivity.class);
        }
        startActivity(intent);
    }
}