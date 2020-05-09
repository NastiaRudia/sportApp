package com.example.myapplication.state;

import com.example.myapplication.retrofit.models.UserInfo;

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

    public int nextState(UserInfo userInfo) {
        for (Map.Entry entry : connect.entrySet()) {
            if (userInfo.check(entry.getKey().toString()))
                return (int) entry.getValue();
        }
        return connect.get("next");
    }
}
