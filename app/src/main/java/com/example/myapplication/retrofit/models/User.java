package com.example.myapplication.retrofit.models;

public class User {
    public String id;
    public String password;
    public String userInfo;
    public String email;

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
