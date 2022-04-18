// User Class that will be a doc in the database for every user
// Will be constantly modified to fit needs of application

package com.example.healthapplication;

import java.util.Date;
import java.util.HashMap;

public class User {
    private int age;
    private int height;
    private double weight;
    private double score;
    private HashMap<Date, Double> daily_calories_by_date;

    User(int age, int height, double weight, double score){
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.score = score;
        this.daily_calories_by_date = new HashMap<Date, Double>();
    }

    // Getter methods
    int getAge(){
        return age;
    }
    int getHeight(){
        return height;
    }
    double getWeight(){
        return weight;
    }
    double getScore(){
        return score;
    }

    // Setter Methods
    void setAge(int age){
        this.age = age;
    }
    void setHeight(int height){
        this.height = height;
    }
    void setWeight(double weight){
        this.weight = weight;
    }

    void setScore(double score){
        this.score = score;
    }

    // Algorithm Methods
    void calculateScore(){
        // Some algorithm related to field variables that calculates the score
    }
}
