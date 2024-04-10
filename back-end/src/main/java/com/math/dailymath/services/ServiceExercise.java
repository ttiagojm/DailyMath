package com.math.dailymath.services;

import com.math.dailymath.errors.ExerciseException;
import com.math.dailymath.models.Exercise;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

public class ServiceExercise {

    // Variables to keep daily exercise up to date
    private static final LocalDate today = LocalDate.now();
    private static Exercise exercise = null;
    private final Random rng = new Random();

    /**
     * Method get daily exercise
     * @param conn
     * @return
     * @throws ExerciseException
     */
    public Exercise getExercise(Connection conn) throws ExerciseException {
        // Verify if the exercise needs to be changed
        if(exercise == null || today.isBefore(LocalDate.now())) {
            ArrayList<Exercise> exercises = generateExercise(conn);

            if(exercises.isEmpty()) {
                System.err.println("No rows selected");
                throw new ExerciseException(500, "Server error!");
            }
            // Select a random exercise from 0 to last index of exercises (nextInt upper bound is exclusive)
            int index = rng.nextInt(exercises.size());
            exercise = exercises.get(index);

            // Update Exercise as Done (selected)
            markExerciseDone(conn, exercise);
        }
        return exercise;
    }

    /**
     * Method to update exercise to be done
     * @param conn
     * @param ex
     * @throws ExerciseException
     */
    private void markExerciseDone(Connection conn, Exercise ex) throws ExerciseException {
        System.out.println("Marking Exercise as 'Done'!");
        String query = "UPDATE EXERCISE SET Done=true WHERE Id_Exercise=?";
        int affectedRows = 0;

        try{

            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setLong(1, ex.getIdExercise());
            affectedRows = pstmt.executeUpdate();

           // Should be updated since exercises exists
           if(affectedRows == 0)
               throw new SQLException("Affected 0 rows");

        } catch (SQLException e){
            System.err.println(e.getMessage());
            throw new ExerciseException(500, "Server error!");
        }
    }

    /**
     * Method to get a new exercise from DB
     * @param conn - Database connection
     * @return
     */
    private ArrayList<Exercise> generateExercise(Connection conn) throws ExerciseException {
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

        } catch (SQLException e){
            System.err.println(e.getMessage());
            throw new ExerciseException(500, "Server Error!");
        }

        return exercises;
    }
}
