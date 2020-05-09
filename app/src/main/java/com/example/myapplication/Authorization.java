package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;

public class Authorization extends AppCompatActivity {
    private EditText password;
    private EditText email;
    private boolean flAuth;
    private TextView h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        FirebaseApp.initializeApp(this);
        password = (EditText) findViewById(R.id.editTextPassword);
        h = findViewById(R.id.textintro);
        email = (EditText) findViewById(R.id.editTextemail);
    }


    public void onClick(View view) {
        if (view.getId() == R.id.buttonRegistration) {
            Intent intent = new Intent(this, Registration1.class);
            startActivity(intent);
        } else if (email.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
            {
                Toast.makeText(getApplicationContext(),
                        "Поля для ввода не могут быть пустыми", Toast.LENGTH_SHORT).show();
            }
        } else {
            if(singing(email.getText().toString(), password.getText().toString()))
            { Intent intent = new Intent(this, Information.class);
            startActivity(intent);}
        }
    }


    public boolean singing(final String email, String password) {
                if (true) {
                    Toast.makeText(getApplicationContext(),
                            "Авторизация успешна", Toast.LENGTH_SHORT).show();
                    flAuth = true;
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Авторизация провалена, неверный пароль или email", Toast.LENGTH_SHORT).show();
                    flAuth = false;
                }

    return flAuth;
    }

}
