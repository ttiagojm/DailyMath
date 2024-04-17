package com.math.dailymath.services;

import com.google.gson.JsonObject;
import com.math.dailymath.dao.DailyExercise;
import com.math.dailymath.dao.DaoExercise;
import com.math.dailymath.errors.APIException;
import com.math.dailymath.models.Exercise;
import com.math.dailymath.models.MultipleChoice;
import com.math.dailymath.models.Solution;
import com.math.dailymath.utils.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class ExerciseService {

    /**
     * Method get daily exercise
     *
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
     * @param idExercise
     * @throws APIException
     */
    public void markExerciseDone(Connection conn, long idExercise) throws APIException {
        System.out.println("Marking Exercise as 'Done'!");
        String query = "UPDATE EXERCISE SET Done=true WHERE Id_Exercise=?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            int affectedRows = Utils.executeUpdate(pstmt, idExercise);

            // Should be updated since exercises exists
            if (affectedRows == 0)
                throw new SQLException("Affected 0 rows");

        } catch (SQLException e) {
            e.printStackTrace(System.err);
            throw new APIException(500, "Server error!");
        }
    }

    /**
     * Method to get ALL exercises
     * @param conn
     * @return
     * @throws APIException
     */
    public ArrayList<Exercise> getExercises(Connection conn) throws APIException {
        return getAllExercises(conn, "SELECT * FROM EXERCISE WHERE");
    }

    /**
     * Method to get exercise done XOR not done
     * @param conn
     * @param done
     * @return
     * @throws APIException
     */
    public ArrayList<Exercise> getExercises(Connection conn, boolean done) throws APIException {
        return getAllExercises(conn, "SELECT * FROM EXERCISE WHERE Done=?", done);
    }

    /**
     * Method to get exercises
     * @param conn
     * @param params
     * @return
     * @throws APIException
     */
    private ArrayList<Exercise> getAllExercises(Connection conn, String query, Object... params) throws APIException {
        System.out.println("Getting all exercises");
        ArrayList<Exercise> exercises = new ArrayList<>();

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            ResultSet res = Utils.executeQuery(pstmt, params);

            while (res.next()) {
                int idExercise = res.getInt("Id_Exercise");
                int idSolution = res.getInt("Id_Solution");
                String typeExercise = res.getString("Type_ex");
                String source = res.getString("Source");
                String exerciseStr = res.getString("Exercise");
                boolean isMultiple = res.getBoolean("Is_multiple");
                boolean isDone = res.getBoolean("Done");

                // Add exercise to list
                exercises.add(
                        new Exercise(idExercise, idSolution, typeExercise, source, exerciseStr, isMultiple, isDone)
                );
            }

        } catch (SQLException e) {
            e.printStackTrace(System.err);
            throw new APIException(500, "Server Error!");
        }
        return exercises;
    }

    /**
     * Method to create an Exercise based on a JSON
     * @param conn
     * @param json
     * @return
     * @throws APIException
     */
    public DaoExercise createExercise(Connection conn, JsonObject json) throws APIException {
        // Verify if all needed fields were passed
        ArrayList<String> keys = new ArrayList<>(
                Arrays.asList("exercise", "source", "typeExercise", "solution")
        );

        if(!Utils.hasAllKeys(json, keys)){
            throw new APIException(400, "Missing fields");
        }

        // Insert them in database
        SolutionService solutionService = new SolutionService();
        Solution solution = solutionService.insertSolution(conn, json.get("solution").getAsString());

        boolean isMultiple = json.has("options");
        String exerciseStr = json.get("exercise").getAsString();
        String source = json.get("source").getAsString();
        String typeExercise = json.get("typeExercise").getAsString();
        Exercise exercise = insertExercise(conn, exerciseStr, source, typeExercise, isMultiple);
        DaoExercise daoExercise = new DaoExercise(exercise, solution);

        if(isMultiple){
            MultipleChoiceService mChoiceService = new MultipleChoiceService();
            MultipleChoice mChoice = mChoiceService.insertMultipleChoice(conn, exercise.getIdExercise(), json.get("options").getAsString());
            daoExercise = new DaoExercise(exercise, solution, mChoice);
        }

        return daoExercise;
    }

    /**
     * Insert exercise on database
     * @param conn
     * @param exercise
     * @param source
     * @param typeExercise
     * @param isMultiple
     * @return
     * @throws APIException
     */
    private Exercise insertExercise(Connection conn, String exercise, String source, String typeExercise, boolean isMultiple) throws APIException {
        System.out.println("Inserting Exercise!");
        String query = "INSERT INTO EXERCISE (Exercise, Source, Type_ex) VALUES (?)";

        try(PreparedStatement pstmt = conn.prepareStatement(query)){
            ResultSet res = Utils.executeQuery(pstmt, exercise, source, typeExercise);
            res.next();
            long idExercise = res.getLong("Id_Exercise");
            long idSolution = res.getLong("Id_Solution");
            boolean isDone = res.getBoolean("Is_Done");
            return new Exercise(idExercise, idSolution, exercise, source, typeExercise, isMultiple, isDone);

        } catch (SQLException e) {
            e.printStackTrace(System.err);
            throw new APIException(500, "Server Error!");
        }
    }
}
