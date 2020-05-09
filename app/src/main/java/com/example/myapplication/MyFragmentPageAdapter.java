package com.example.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.myapplication.state.StateMachine;

public class MyFragmentPageAdapter extends FragmentStatePagerAdapter {

    private final int count;
    private StateMachine FMS;
    private long baseId = 4;

    public MyFragmentPageAdapter(FragmentManager fm, int count, StateMachine fms) {
        super(fm);
        this.FMS = fms;
        this.count = count;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = PageFragment.newInstance(position, FMS);
        return fragment;
    }


    @Override
    public int getCount() {
        return count;
    }


}





