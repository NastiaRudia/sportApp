package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Registration1 extends AppCompatActivity {
    private EditText password;
    private EditText email;
    private boolean flAuth = true;
    private EditText repitPassword;
    private boolean fl = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration1);
        password = (EditText) findViewById(R.id.editTextPassword);
        email = (EditText) findViewById(R.id.editTextemail);
        repitPassword = (EditText) findViewById(R.id.editTextPasswordRep);
    }

    public void onClick(View view) {
        if (email.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "Поля для ввода не могут быть пустыми", Toast.LENGTH_SHORT).show();
        } else if (password.getText().toString().equals(repitPassword.getText().toString())) {
            registration(email.getText().toString(), password.getText().toString());
            Intent intent = new Intent(this, Registration2.class);
            startActivity(intent);
        } else
            Toast.makeText(getApplicationContext(),
                    "Пароли не совпадают", Toast.LENGTH_SHORT).show();

    }

    public void registration(String email, String password) {
        if (true) {
            Toast.makeText(getApplicationContext(),
                    "Регистрация успешна", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(),
                    "Неправильный формат email ", Toast.LENGTH_SHORT).show();
            fl = false;
        }
    }

}
