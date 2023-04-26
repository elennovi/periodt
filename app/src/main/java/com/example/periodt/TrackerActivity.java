package com.example.periodt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.HashSet;
import java.util.Set;

public class TrackerActivity extends AppCompatActivity {

    private BottomNavigationView navBar;
    private DBHandler db;
    private String uid;
    private Set<String> track;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tracker);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        navBar = findViewById(R.id.navigation_bar);
        navBar.setSelectedItemId(R.id.it_calendar);

        navBar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.it_calendar){
                    startActivity(new Intent(getApplicationContext(), CalendarActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                }
                else if (item.getItemId() == R.id.it_tracker){
                    return true;
                }
                else if (item.getItemId() == R.id.it_settings){
                    startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                }
                return false;
            }
        });

        db = new DBHandler(TrackerActivity.this);

        track = new HashSet<String>();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        uid = prefs.getString("uid", "0");

        // period yes no
        ImageButton period_yes_btn = (ImageButton) findViewById(R.id.period_yes_btn);
        period_yes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(track.contains("period_yes_btn")){
                    period_yes_btn.setColorFilter(Color.rgb(238,153,146));
                    track.remove("period_yes_btn");
                }
                else{
                    period_yes_btn.setColorFilter(Color.rgb(151, 191, 180));
                    track.add("period_yes_btn");
                }
            }
        });
        ImageButton period_no_btn = (ImageButton) findViewById(R.id.period_no_btn);
        period_no_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(track.contains("period_no_btn")){
                    period_no_btn.setColorFilter(Color.rgb(238,153,146));
                    track.remove("period_no_btn");
                }
                else{
                    period_no_btn.setColorFilter(Color.rgb(151, 191, 180));
                    track.add("period_no_btn");
                }
            }
        });

        // symptons
        ImageButton no_symptons_btn = (ImageButton) findViewById(R.id.no_symptons_btn);
        no_symptons_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(track.contains("no_symptons_btn")){
                    no_symptons_btn.setColorFilter(Color.rgb(238,153,146));
                    track.remove("no_symptons_btn");
                }
                else{
                    no_symptons_btn.setColorFilter(Color.rgb(151, 191, 180));
                    track.add("no_symptons_btn");
                }
            }
        });
        ImageButton colicos_btn = (ImageButton) findViewById(R.id.colicos_btn);
        colicos_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(track.contains("colicos_btn")){
                    colicos_btn.setColorFilter(Color.rgb(238,153,146));
                    track.remove("colicos_btn");
                }
                else{
                    colicos_btn.setColorFilter(Color.rgb(151, 191, 180));
                    track.add("colicos_btn");
                }
            }
        });
        ImageButton ovulacion_btn = (ImageButton) findViewById(R.id.ovulacion_btn);
        ovulacion_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(track.contains("ovulacion_btn")){
                    ovulacion_btn.setColorFilter(Color.rgb(238,153,146));
                    track.remove("ovulacion_btn");
                }
                else{
                    ovulacion_btn.setColorFilter(Color.rgb(151, 191, 180));
                    track.add("ovulacion_btn");
                }
            }
        });
        ImageButton sensibilidad_btn = (ImageButton) findViewById(R.id.sensibilidad_btn);
        sensibilidad_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(track.contains("sensibilidad_btn")){
                    sensibilidad_btn.setColorFilter(Color.rgb(238,153,146));
                    track.remove("sensibilidad_btn");
                }
                else{
                    sensibilidad_btn.setColorFilter(Color.rgb(151, 191, 180));
                    track.add("sensibilidad_btn");
                }
            }
        });
        ImageButton headache_btn = (ImageButton) findViewById(R.id.headache_btn);
        headache_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(track.contains("headache_btn")){
                    headache_btn.setColorFilter(Color.rgb(238,153,146));
                    track.remove("headache_btn");
                }
                else{
                    headache_btn.setColorFilter(Color.rgb(151, 191, 180));
                    track.add("headache_btn");
                }
            }
        });
        ImageButton nauseas_btn = (ImageButton) findViewById(R.id.nauseas_btn);
        nauseas_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(track.contains("nauseas_btn")){
                    nauseas_btn.setColorFilter(Color.rgb(238,153,146));
                    track.remove("nauseas_btn");
                }
                else{
                    nauseas_btn.setColorFilter(Color.rgb(151, 191, 180));
                    track.add("nauseas_btn");
                }
            }
        });
        ImageButton lumbares_btn = (ImageButton) findViewById(R.id.lumbares_btn);
        lumbares_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(track.contains("lumbares_btn")){
                    lumbares_btn.setColorFilter(Color.rgb(238,153,146));
                    track.remove("lumbares_btn");
                }
                else{
                    lumbares_btn.setColorFilter(Color.rgb(151, 191, 180));
                    track.add("lumbares_btn");
                }
            }
        });
        ImageButton piernas_btn = (ImageButton) findViewById(R.id.piernas_btn);
        piernas_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(track.contains("piernas_btn")){
                    piernas_btn.setColorFilter(Color.rgb(238,153,146));
                    track.remove("piernas_btn");
                }
                else{
                    piernas_btn.setColorFilter(Color.rgb(151, 191, 180));
                    track.add("piernas_btn");
                }
            }
        });
        ImageButton articulaciones_btn = (ImageButton) findViewById(R.id.articulaciones_btn);
        articulaciones_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(track.contains("articulaciones_btn")){
                    articulaciones_btn.setColorFilter(Color.rgb(238,153,146));
                    track.remove("articulaciones_btn");
                }
                else{
                    articulaciones_btn.setColorFilter(Color.rgb(151, 191, 180));
                    track.add("articulaciones_btn");
                }
            }
        });
        ImageButton vulvar_btn = (ImageButton) findViewById(R.id.vulvar_btn);
        vulvar_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(track.contains("vulvar_btn")){
                    vulvar_btn.setColorFilter(Color.rgb(238,153,146));
                    track.remove("vulvar_btn");
                }
                else{
                    vulvar_btn.setColorFilter(Color.rgb(151, 191, 180));
                    track.add("vulvar_btn");
                }
            }
        });

        // secreción
        ImageButton blanco_btn = (ImageButton) findViewById(R.id.blanco_btn);
        blanco_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(track.contains("blanco_btn")){
                    blanco_btn.setColorFilter(Color.rgb(238,153,146));
                    track.remove("blanco_btn");
                }
                else{
                    blanco_btn.setColorFilter(Color.rgb(151, 191, 180));
                    track.add("blanco_btn");
                }
            }
        });
        ImageButton amarillo_btn = (ImageButton) findViewById(R.id.amarillo_btn);
        amarillo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(track.contains("amarillo_btn")){
                    amarillo_btn.setColorFilter(Color.rgb(238,153,146));
                    track.remove("amarillo_btn");
                }
                else{
                    amarillo_btn.setColorFilter(Color.rgb(151, 191, 180));
                    track.add("amarillo_btn");
                }
            }
        });
        ImageButton transparente_btn = (ImageButton) findViewById(R.id.transparente_btn);
        transparente_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(track.contains("transparente_btn")){
                    transparente_btn.setColorFilter(Color.rgb(238,153,146));
                    track.remove("transparente_btn");
                }
                else{
                    transparente_btn.setColorFilter(Color.rgb(151, 191, 180));
                    track.add("transparente_btn");
                }
            }
        });
        ImageButton verde_btn = (ImageButton) findViewById(R.id.verde_btn);
        verde_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(track.contains("verde_btn")){
                    verde_btn.setColorFilter(Color.rgb(238,153,146));
                    track.remove("verde_btn");
                }
                else{
                    verde_btn.setColorFilter(Color.rgb(151, 191, 180));
                    track.add("verde_btn");
                }
            }
        });
        ImageButton marron_btn = (ImageButton) findViewById(R.id.marron_btn);
        marron_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(track.contains("marron_btn")){
                    marron_btn.setColorFilter(Color.rgb(238,153,146));
                    track.remove("marron_btn");
                }
                else{
                    marron_btn.setColorFilter(Color.rgb(151, 191, 180));
                    track.add("marron_btn");
                }
            }
        });
        ImageButton seco_btn = (ImageButton) findViewById(R.id.seco_btn);
        seco_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(track.contains("seco_btn")){
                    seco_btn.setColorFilter(Color.rgb(238,153,146));
                    track.remove("seco_btn");
                }
                else{
                    seco_btn.setColorFilter(Color.rgb(151, 191, 180));
                    track.add("seco_btn");
                }
            }
        });
        ImageButton humedo_btn = (ImageButton) findViewById(R.id.humedo_btn);
        humedo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(track.contains("humedo_btn")){
                    humedo_btn.setColorFilter(Color.rgb(238,153,146));
                    track.remove("humedo_btn");
                }
                else{
                    humedo_btn.setColorFilter(Color.rgb(151, 191, 180));
                    track.add("humedo_btn");
                }
            }
        });
        ImageButton pegajoso_btn = (ImageButton) findViewById(R.id.pegajoso_btn);
        pegajoso_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(track.contains("pegajoso_btn")){
                    pegajoso_btn.setColorFilter(Color.rgb(238,153,146));
                    track.remove("pegajoso_btn");
                }
                else{
                    pegajoso_btn.setColorFilter(Color.rgb(151, 191, 180));
                    track.add("pegajoso_btn");
                }
            }
        });
        ImageButton cremoso_btn = (ImageButton) findViewById(R.id.cremoso_btn);
        cremoso_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(track.contains("cremoso_btn")){
                    cremoso_btn.setColorFilter(Color.rgb(238,153,146));
                    track.remove("cremoso_btn");
                }
                else{
                    cremoso_btn.setColorFilter(Color.rgb(151, 191, 180));
                    track.add("cremoso_btn");
                }
            }
        });

        // sexual desire
        Button bajo_btn = (Button) findViewById(R.id.bajo_btn);
        bajo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(track.contains("bajo_btn")){
                    bajo_btn.setBackgroundColor(Color.rgb(238,153,146));
                    track.remove("bajo_btn");
                }
                else{
                    bajo_btn.setBackgroundColor(Color.rgb(151, 191, 180));
                    track.add("bajo_btn");
                }
            }
        });
        Button medio_btn = (Button) findViewById(R.id.medio_btn);
        medio_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(track.contains("medio_btn")){
                    medio_btn.setBackgroundColor(Color.rgb(238,153,146));
                    track.remove("medio_btn");
                }
                else{
                    medio_btn.setBackgroundColor(Color.rgb(151, 191, 180));
                    track.add("medio_btn");
                }
            }
        });
        Button alto_btn = (Button) findViewById(R.id.alto_btn);
        alto_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(track.contains("alto_btn")){
                    alto_btn.setBackgroundColor(Color.rgb(238,153,146));
                    track.remove("alto_btn");
                }
                else{
                    alto_btn.setBackgroundColor(Color.rgb(151, 191, 180));
                    track.add("alto_btn");
                }
            }
        });

        // period quantity
        Button ligero_btn = (Button) findViewById(R.id.ligero_btn);
        ligero_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(track.contains("ligero_btn")){
                    ligero_btn.setBackgroundColor(Color.rgb(238,153,146));
                    track.remove("ligero_btn");
                }
                else{
                    ligero_btn.setBackgroundColor(Color.rgb(151, 191, 180));
                    track.add("ligero_btn");
                }
            }
        });
        Button moderado_btn = (Button) findViewById(R.id.moderado_btn);
        moderado_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(track.contains("moderado_btn")){
                    moderado_btn.setBackgroundColor(Color.rgb(238,153,146));
                    track.remove("moderado_btn");
                }
                else{
                    moderado_btn.setBackgroundColor(Color.rgb(151, 191, 180));
                    track.add("moderado_btn");
                }
            }
        });
        Button fuerte_btn = (Button) findViewById(R.id.fuerte_btn);
        fuerte_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(track.contains("fuerte_btn")){
                    fuerte_btn.setBackgroundColor(Color.rgb(238,153,146));
                    track.remove("fuerte_btn");
                }
                else{
                    fuerte_btn.setBackgroundColor(Color.rgb(151, 191, 180));
                    track.add("fuerte_btn");
                }
            }
        });
        Button intenso_btn = (Button) findViewById(R.id.intenso_btn);
        intenso_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(track.contains("intenso_btn")){
                    intenso_btn.setBackgroundColor(Color.rgb(238,153,146));
                    track.remove("intenso_btn");
                }
                else{
                    intenso_btn.setBackgroundColor(Color.rgb(151, 191, 180));
                    track.add("intenso_btn");
                }
            }
        });

        // stain
        ImageButton rojo_manchado_btn = (ImageButton) findViewById(R.id.rojo_manchado_btn);
        rojo_manchado_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(track.contains("rojo_manchado_btn")){
                    rojo_manchado_btn.setColorFilter(Color.rgb(238,153,146));
                    track.remove("rojo_manchado_btn");
                }
                else{
                    rojo_manchado_btn.setColorFilter(Color.rgb(151, 191, 180));
                    track.add("rojo_manchado_btn");
                }
            }
        });
        ImageButton marron_manchado_btn = (ImageButton) findViewById(R.id.marron_manchado_btn);
        marron_manchado_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(track.contains("marron_manchado_btn")){
                    marron_manchado_btn.setColorFilter(Color.rgb(238,153,146));
                    track.remove("marron_manchado_btn");
                }
                else{
                    marron_manchado_btn.setColorFilter(Color.rgb(151, 191, 180));
                    track.add("marron_manchado_btn");
                }
            }
        });

        // well-being
        Button btn_1 = (Button) findViewById(R.id.btn_1);
        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(track.contains("btn_1")){
                    btn_1.setBackgroundColor(Color.rgb(238,153,146));
                    track.remove("btn_1");
                }
                else{
                    btn_1.setBackgroundColor(Color.rgb(151, 191, 180));
                    track.add("btn_1");
                }
            }
        });
        Button btn_2 = (Button) findViewById(R.id.btn_2);
        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(track.contains("btn_2")){
                    btn_2.setBackgroundColor(Color.rgb(238,153,146));
                    track.remove("btn_2");
                }
                else{
                    btn_2.setBackgroundColor(Color.rgb(151, 191, 180));
                    track.add("btn_2");
                }
            }
        });
        Button btn_3 = (Button) findViewById(R.id.btn_3);
        btn_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(track.contains("btn_3")){
                    btn_3.setBackgroundColor(Color.rgb(238,153,146));
                    track.remove("btn_3");
                }
                else{
                    btn_3.setBackgroundColor(Color.rgb(151, 191, 180));
                    track.add("btn_3");
                }
            }
        });
        Button btn_4 = (Button) findViewById(R.id.btn_4);
        btn_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(track.contains("btn_4")){
                    btn_4.setBackgroundColor(Color.rgb(238,153,146));
                    track.remove("btn_4");
                }
                else{
                    btn_4.setBackgroundColor(Color.rgb(151, 191, 180));
                    track.add("btn_4");
                }
            }
        });
        Button btn_5 = (Button) findViewById(R.id.btn_5);
        btn_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(track.contains("btn_5")){
                    btn_5.setBackgroundColor(Color.rgb(238,153,146));
                    track.remove("btn_5");
                }
                else{
                    btn_5.setBackgroundColor(Color.rgb(151, 191, 180));
                    track.add("btn_5");
                }
            }
        });

        // SPM
        ImageButton spm_btn = (ImageButton) findViewById(R.id.spm_btn);
        spm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(track.contains("spm_btn")){
                    spm_btn.setColorFilter(Color.rgb(238,153,146));
                    track.remove("spm_btn");
                }
                else{
                    spm_btn.setColorFilter(Color.rgb(151, 191, 180));
                    track.add("spm_btn");
                }
            }
        });

        // save btn
        Button save_btn = (Button) findViewById(R.id.save_btn);
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // all the options that are selected need to be saved in the database
                db.updateTracker(track, uid);
                // go back to calendar
                Intent intent = new Intent(getApplicationContext(), CalendarView.class);
                startActivity(intent);
            }
        });
    }
}