package com.example.myapplication.state;

import android.app.Activity;

import com.example.myapplication.User;

import java.util.ArrayList;

public class StateMachine {

     private int currentState;
    Activity activity;
    public int stateCount;
    public User user;
    public int finalState;
    public ArrayList<State> states = new ArrayList<>();

    public StateMachine(User user) {
        this.user = user;
        this.stateCount = 22;
        finalState = 21;
        for (int i = 0; i < stateCount; i++) {
            states.add(new State());
        }
    }

    public State getCurrentState() {
        return states.get(currentState);
    }

    public boolean isOnFinalState() {
        return currentState == finalState;
    }

    public String getCurrentInformation() {
        return states.get(currentState).description;
    }

    public void nextStep() {
        if (!isOnFinalState())
            currentState = states.get(currentState).nextState(user);


    }

}
