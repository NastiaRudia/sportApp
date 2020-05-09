package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.myapplication.retrofit.models.UserInfo;

public class Registration2 extends AppCompatActivity {
    private EditText height;
    private EditText weight;
    private String goal;
    private String experiense;
    private EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration2);
        height=findViewById(R.id.et_height);
        weight=findViewById(R.id.et_weight);
        name=findViewById(R.id.name);

    }

    public void onClick(View view) {
        UserInfo userInfo =new UserInfo(name.getText().toString(),weight.getText().toString(),
                height.getText().toString(),goal, experiense,"first", "") ;
        System.out.print(userInfo.toString());
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("user", userInfo);
        startActivity(intent);

    }
    public void onRadioButtonClicked(View view) {
        // если переключатель отмечен
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.rb_loose:
                if (checked){
                    goal="loose";
                }
                break;
            case R.id.rb_add:
                if (checked){
                    goal="add";
                }
                break;

            case R.id.rb_new:
                if (checked){
                    experiense="new";
                }
                break;
            case R.id.rb_prof:
                if (checked){
                    experiense="profy";
                }
                break;
        }
    }
}
