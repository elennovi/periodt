package com.example.periodt;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        Intent intent = getIntent();
        Button button = (Button) findViewById(R.id.register_btn);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                submitData();
            }
        });
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

        // Check if available

        // Post the data and go
    }
}