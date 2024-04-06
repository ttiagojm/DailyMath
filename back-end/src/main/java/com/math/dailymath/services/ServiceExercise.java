package com.math.dailymath.services;

import com.math.dailymath.models.Exercise;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

public class ServiceExercise {

    // Variables to keep daily exercise up to date
    private static final LocalDate today = LocalDate.now();
    private static Exercise exercise = null;
    private final Random rng = new Random();

    /**
     * Method to get a new exercise from DB
     * @param conn - Database connection
     */
    private void generateExercise(Connection conn){
        System.out.println("Generating Exercise!");
        String query = "SELECT * FROM EXERCISE WHERE Done=false";
        ArrayList<Exercise> exercises = new ArrayList<>();

        try{
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);

            while(res.next()){
                int idExercise = res.getInt("Id_Exercise");
                int idSolution = res.getInt("Id_Solution");
                String typeExercise = res.getString("Type_ex");
                String source = res.getString("Source");
                String exerciseStr = res.getString("Exercise");
                boolean isMultiple = res.getBoolean("Is_multiple");

                // Add exercise to list
                exercises.add(
                        new Exercise(idExercise, idSolution, typeExercise, source, exerciseStr, isMultiple)
                );
            }

            res.close();
            stmt.close();

            // Select a random exercise from 0 to last index of exercises (nextInt upper bound is exclusive)
            int index = rng.nextInt(exercises.size());

            exercise = exercises.get(index);

        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    public Exercise getExercise(Connection conn){
        // Verify if the exercise needs to be changed
        if(exercise == null || today.isBefore(LocalDate.now()))
            generateExercise(conn);

        return exercise;
    }
}
