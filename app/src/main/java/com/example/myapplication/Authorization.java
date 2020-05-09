package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.retrofit.HttpClient;
import com.example.myapplication.retrofit.Response;
import com.example.myapplication.retrofit.models.User;
import com.example.myapplication.retrofit.models.UserInfo;
import com.google.firebase.FirebaseApp;

import java.io.IOException;

import okhttp3.ResponseBody;

public class Authorization extends AppCompatActivity {
    private EditText password;
    private EditText email;
    private boolean flAuth;
    private TextView h;
    private HttpClient httpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        FirebaseApp.initializeApp(this);
        password = findViewById(R.id.editTextPassword);
        h = findViewById(R.id.textintro);
        email = findViewById(R.id.editTextemail);
        httpClient = new HttpClient("http://localhost:8080/");
    }


    public void onClick(View view) throws IOException {
        if (view.getId() == R.id.buttonRegistration) {
            Intent intent = new Intent(this, Registration1.class);
            startActivity(intent);
        } else if (email.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Поля не можуть бути пусті", Toast.LENGTH_SHORT).show();
        } else {
            User user = new User(email.getText().toString(), password.getText().toString());
            ResponseBody response = httpClient.userService.getUsersList().execute().body();
            System.out.println(response.toString());
//            if (response.getResult()) {
//                Toast.makeText(getApplicationContext(),
//                        "Авторизація успішна", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(this, Information.class);
//                intent.putExtra("userId", response.getData().id);
//                startActivity(intent);
//            }
//            else {
//                Toast.makeText(getApplicationContext(),
//                        response.getMessage(), Toast.LENGTH_SHORT).show();
//            }
        }
    }

}
