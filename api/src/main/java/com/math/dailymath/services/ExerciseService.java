package com.math.dailymath.services;

import com.math.dailymath.dao.DailyExercise;
import com.math.dailymath.dao.DaoExercise;
import com.math.dailymath.errors.APIException;
import com.math.dailymath.models.Exercise;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ExerciseService {

    /**
     * Method get daily exercise
     * @param conn
     * @return
     * @throws APIException
     */
    public DailyExercise getDailyExercise(Connection conn) throws APIException {
        // Get DailyExercise singleton instance
        return DailyExercise.getDailyExercise(conn);
    }

    /**
     * Method to update exercise to be done
     * @param conn
     * @param ex
     * @throws APIException
     */
    public void markExerciseDone(Connection conn, Exercise ex) throws APIException {
        System.out.println("Marking Exercise as 'Done'!");
        String query = "UPDATE EXERCISE SET Done=true WHERE Id_Exercise=?";
        int affectedRows;

        try{

            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setLong(1, ex.getIdExercise());
            affectedRows = pstmt.executeUpdate();

           // Should be updated since exercises exists
           if(affectedRows == 0)
               throw new SQLException("Affected 0 rows");

        } catch (SQLException e){
            System.err.println(e.getMessage());
            throw new APIException(500, "Server error!");
        }
    }

    /**
     * Method to get a new exercise from DB
     * @param conn - Database connection
     * @return
     */
    public ArrayList<Exercise> getExercisesDone(Connection conn, boolean done) throws APIException {
        System.out.println("Generating Exercise!");
        String query = "SELECT * FROM EXERCISE WHERE Done=?";
        ArrayList<Exercise> exercises = new ArrayList<>();

        try{
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setBoolean(1, done);
            ResultSet res = pstmt.executeQuery();

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
            pstmt.close();

        } catch (SQLException e){
            System.err.println(e.getMessage());
            throw new APIException(500, "Server Error!");
        }

        return exercises;
    }

    public DaoExercise insertExercise(Connection conn, DaoExercise daoExercise){
        System.out.println("Inserting Exercise!");
        String query = "INSERT INTO EXERCISE (Exercise,Type_ex,Source,is_Multiple, is_Done) " +
                "VALUES (?,?,?,?, false)";
    }
}
