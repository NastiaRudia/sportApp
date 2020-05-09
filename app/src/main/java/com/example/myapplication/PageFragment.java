package com.example.myapplication;
import java.util.Random;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.state.StateMachine;

public class PageFragment extends Fragment {

    static final String ARGUMENT_PAGE_INFORMATION = "arg_page_information";

    String pageInformation;
    int backColor;



    static PageFragment newInstance(int page, StateMachine FMS) {
        PageFragment pageFragment = new PageFragment();
        Bundle arguments = new Bundle();
        arguments.putString(ARGUMENT_PAGE_INFORMATION, FMS.getCurrentInformation());
        FMS.nextStep();
        pageFragment.setArguments(arguments);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageInformation = getArguments().getString(ARGUMENT_PAGE_INFORMATION);

        Random rnd = new Random();
        backColor = Color.argb(40, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_page_fragment, null);

        TextView tvPage = view.findViewById(R.id.tvPage);
        tvPage.setText(pageInformation);
        tvPage.setBackgroundColor(backColor);

        return view;
    }

}