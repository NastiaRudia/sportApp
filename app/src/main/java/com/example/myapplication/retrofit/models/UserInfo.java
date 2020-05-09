package com.example.myapplication.retrofit.models;

import java.io.Serializable;
import java.util.ArrayList;

public class UserInfo implements Serializable {

    private String id;
    private String weight;
    private String height;
    private String goal;
    private String name;
    private String experience;
    private String training;
    private ArrayList<String> parameters = new ArrayList<>();

    public UserInfo() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public UserInfo(String name, String weight, String height, String goal, String experience, String training, String id) {
        this.id = id;
        parameters.add(name);
        this.training=training;
        parameters.add(weight);
        parameters.add(height);
        parameters.add(goal);
        parameters.add(experience);
        parameters.add(training);
        this.experience=experience;
        this.weight = weight;
        this.height = height;
        this.goal = goal;
        this.name = name;
    }

    public boolean check(String parametr) {
         return parameters.contains(parametr);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public ArrayList<String> getParameters() {
        return parameters;
    }

    public void setParameters(ArrayList<String> parameters) {
        this.parameters = parameters;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getTraining() {
        return training;
    }

    public void setTraining(String training) {
        this.training = training;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

