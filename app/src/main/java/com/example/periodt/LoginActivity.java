package com.example.periodt;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    private DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Intent intent = getIntent();

        Button register = (Button) findViewById(R.id.login_btn);
        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                submitData();
            }
        });

        Button login = (Button) findViewById(R.id.register_redirect);
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                redirectSignup();
            }
        });

        // get database
        db = new DBHandler(LoginActivity.this);
    }

    private void submitData() {
        EditText email_et = (EditText) findViewById(R.id.email_addr_login);
        EditText pwd_et = (EditText) findViewById(R.id.password_login);

        // Get the data
        String email = email_et.getText().toString();
        String pwd = pwd_et.getText().toString();

        if(db.findByEmail(email)){
            if(db.correctPwd(email, pwd)){
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("isLogged", true);
                editor.apply();

                Intent intent = new Intent(this, LastperiodActivity.class);
                startActivity(intent);
            }
            else{
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                builder.setMessage(R.string.incorrect_pwd)
                        .setTitle(R.string.error);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            builder.setMessage(R.string.email_not_found)
                    .setTitle(R.string.error);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    private void redirectSignup() {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }
}