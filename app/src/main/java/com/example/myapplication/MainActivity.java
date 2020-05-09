package com.example.myapplication;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import com.example.myapplication.retrofit.models.UserInfo;
import com.example.myapplication.state.StateMachine;

public class MainActivity extends FragmentActivity {

    static final String TAG = "myLogs";
    StateMachine FMS;
    ViewPager pager;
    PagerAdapter pagerAdapter;
    UserInfo userInfo;
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userInfo = (UserInfo) getIntent().getSerializableExtra("user");
        setContentView(R.layout.activity_main);
        FMS = new StateMachine(userInfo);
        pager = findViewById(R.id.pager);
        createStateMachine(MainActivity.this);
        pagerAdapter = new MyFragmentPageAdapter(getSupportFragmentManager(), 2, FMS);
        pager.setAdapter(pagerAdapter);
        next=findViewById(R.id.button_next_part);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!FMS.isOnFinalState()) {
                    if (userInfo.getExperience().equals("new")) {
                        pagerAdapter = new MyFragmentPageAdapter(getSupportFragmentManager(), 3, FMS);
                        pager.setAdapter(pagerAdapter);
                    } else {
                        pagerAdapter = new MyFragmentPageAdapter(getSupportFragmentManager(), 4, FMS);
                        pager.setAdapter(pagerAdapter);
                    }
                }
                else {
                    pagerAdapter = new MyFragmentPageAdapter(getSupportFragmentManager(), 1, FMS);
                    pager.setAdapter(pagerAdapter);
                }
            }
        });

        pager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected, position = " + position);
                pagerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }

    private String des(Activity activity, int state) {
        AssetManager am = activity.getAssets();
        InputStream is = null;
        String my= "";
        try {
            is = am.open(state+".txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scanner conin = new Scanner(is);
        while (conin.hasNextLine())
            my+=conin.nextLine();
      return my;


    }
    private void createStateMachine(Activity activity) {
        AssetManager am = activity.getAssets();
        InputStream is = null;
        try {
            is = am.open("test.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String myText = null;
        String temp, description;
        String transition;
        int index;
        Scanner conin = new Scanner(is);
        while (conin.hasNext()) {
            temp = conin.nextLine();
            index = Integer.parseInt(temp.substring(0, temp.indexOf('#')));
            transition = temp.substring(temp.indexOf('#') + 1, temp.lastIndexOf('#'));
            FMS.states.get(index).connect.put(transition, Integer.parseInt(temp.substring(temp.lastIndexOf('#') + 1)));
        }
        for(int i=0; i<22; i++){
        FMS.states.get(i).description=des(activity, i);
    }
}}