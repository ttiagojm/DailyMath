package com.math.dailymath.services;

import com.math.dailymath.models.Database;
import com.math.dailymath.models.Exercise;

import java.time.LocalDate;

public class ServiceExercise {
    private final Database db;

    // Variables to keep daily exercise up to date
    private static LocalDate today = LocalDate.now();
    private static Exercise exercise = null;

    public ServiceExercise(){
        this.db = Database.getDatabase();
    }

    public Exercise getExercise(){
        // Verify if the exercise needs to be changed
        if(exercise == null || today.isBefore(LocalDate.now())){
            // TODO Call Database instance to get all exercises not randomized
        }

        return exercise;
    }
}
