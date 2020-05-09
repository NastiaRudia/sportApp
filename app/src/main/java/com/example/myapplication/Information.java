package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.myapplication.retrofit.models.UserInfo;

public class Information extends AppCompatActivity {
    private EditText height;
    private EditText weight;
    private String goal;
    private EditText name;
    private RadioButton loose;
    private RadioButton add;
    private RadioButton newW;
    private RadioButton profy;
    private String experience;
    private String training;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        height = findViewById(R.id.et_height);
        weight = findViewById(R.id.et_weight);
        name = findViewById(R.id.name);
        add = findViewById(R.id.rb_add);
        loose = findViewById(R.id.rb_loose);
        newW = findViewById(R.id.rb_new);
        profy = findViewById(R.id.rb_prof);

    }

    public void onClick(View view) {
        if (view.getId() == R.id.but_save) {
            UserInfo userInfo = new UserInfo(name.getText().toString(), weight.getText().toString(),
                    height.getText().toString(), goal, experience,training, "");
            System.out.print(userInfo.toString());
        } else {
            UserInfo userInfo = new UserInfo(name.getText().toString(), weight.getText().toString(),
                    height.getText().toString(), goal, experience,training, "");
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("user", userInfo);
            startActivity(intent);
        }

    }


    public void onRadioButtonClicked(View view) {
        // если переключатель отмечен
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.rb_loose:
                if (checked) {
                    goal = "loose";
                }
                break;
            case R.id.rb_add:
                if (checked) {
                    goal = "add";
                }
                break;

            case R.id.rb_new:
                if (checked) {
                    experience = "new";
                }
                break;
            case R.id.rb_prof:
                if (checked) {
                    experience = "profy";
                }
                break;

        }
    }
}