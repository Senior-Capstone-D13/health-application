package com.example.healthapplication;

import java.util.HashMap;
import java.util.Objects;

public class DefaultCalories {
    int minutes;
    HashMap<String, Integer> exercise_by_calorie = new HashMap<>();
    public DefaultCalories(){

        // Tennis, weights are per minute
        // Running is by one mile
        this.exercise_by_calorie.put("Tennis", 9);
        this.exercise_by_calorie.put("Weights", 3);
        this.exercise_by_calorie.put("Running", 100);
        this.exercise_by_calorie.put("Soccer", 8);
        this.exercise_by_calorie.put("Football", 9);
        this.exercise_by_calorie.put("Basketball", 10);
    }

    public int get_calories(String sport, int number) throws Exception {
        return exercise_by_calorie.get(sport) * number;
    }
}
