package com.example.periodt;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignupActivity extends AppCompatActivity {

    private DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        Intent intent = getIntent();

        Button register = (Button) findViewById(R.id.register_btn);
        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                submitData();
            }
        });

        Button login = (Button) findViewById(R.id.login_redirect);
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                redirectLogin();
            }
        });

        // get database
        db = new DBHandler(SignupActivity.this);
    }

    private void submitData() {
        EditText email_et = (EditText) findViewById(R.id.email_addr);
        EditText pwd_et = (EditText) findViewById(R.id.password);
        EditText repeat_pwd_et = (EditText) findViewById(R.id.repeat_password);

        // Get the data
        String email = email_et.getText().toString();
        String pwd = pwd_et.getText().toString();
        String repeat_pwd = repeat_pwd_et.getText().toString();

        if (!pwd.equals(repeat_pwd)){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            builder.setMessage(R.string.same_pwd_error)
                    .setTitle(R.string.error);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else {
            // Check if available
            if(!db.findByEmail(email)){ // email is not used
                db.addNewUser(email, pwd);
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("isLogged", true);

                String uid = db.getIdByEmail(email);
                editor.putString("uid", uid);
                editor.apply();

                Intent intent = new Intent(this, LastperiodActivity.class);
                startActivity(intent);
            }
            else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                builder.setMessage(R.string.taken_email)
                        .setTitle(R.string.error);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
    }

    private void redirectLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}