package com.example.myapplication;



import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private String weight;
    private String height;
    private String goal;
    private String name;
    private String experience;
    private String training;
    private ArrayList<String> parametrs = new ArrayList<>();

    public User(String name, String weight, String height, String goal, String experience,String training) {
        parametrs.add(name);
        this.training=training;
        parametrs.add(weight);
        parametrs.add(height);
        parametrs.add(goal);
        parametrs.add(experience);
        parametrs.add(training);
        this.experience=experience;
        this.weight = weight;
        this.height = height;
        this.goal = goal;
        this.name = name;
    }

    public boolean check(String parametr) {
         return parametrs.contains(parametr);
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


    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
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
}

