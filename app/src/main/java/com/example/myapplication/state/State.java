package com.example.myapplication.state;

import com.example.myapplication.User;

import java.util.HashMap;
import java.util.Map;


public class State {


    public Boolean getBodyPart() {
        return bodyPart;
    }

    public void setBodyPart(Boolean bodyPart) {
        this.bodyPart = bodyPart;
    }

    private Boolean bodyPart = true;
    public String description = "Описание Упражнения";

    public Map<String, Integer> connect = new HashMap<>();

    public int nextState(User user) {
        for (Map.Entry entry : connect.entrySet()) {
            if (user.check(entry.getKey().toString()))
                return (int) entry.getValue();
        }
        return connect.get("next");
    }
}
