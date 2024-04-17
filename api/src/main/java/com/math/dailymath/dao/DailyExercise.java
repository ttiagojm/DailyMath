package com.math.dailymath.dao;

import com.math.dailymath.errors.APIException;
import com.math.dailymath.models.Exercise;
import com.math.dailymath.models.MultipleChoice;
import com.math.dailymath.models.Solution;
import com.math.dailymath.services.ExerciseService;
import com.math.dailymath.services.MultipleChoiceService;
import com.math.dailymath.services.SolutionService;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

public class DailyExercise extends DaoExercise {

    // Singleton since everyone should have the same DailyExercise
    private static DailyExercise daily;
    private static LocalDate today = LocalDate.now();

    private DailyExercise(Exercise exercise, Solution solution){
        super(exercise, solution);
    }
    private DailyExercise(Exercise exercise, Solution solution, MultipleChoice multipleChoice){
        super(exercise, solution, multipleChoice);
    }

    public static synchronized DailyExercise getDailyExercise(Connection conn) throws APIException {
        if(daily == null || today.isBefore(LocalDate.now())){

            // Services needed to build a daily exercise
            ExerciseService exerciseService = new ExerciseService();
            SolutionService solutionService = new SolutionService();
            MultipleChoiceService multipleChoiceService = new MultipleChoiceService();

            today = LocalDate.now();

            // Obtain all the not Done exercises
            ArrayList<Exercise> exercises = exerciseService.getExercises(conn, false);

            if(exercises.isEmpty()) {
                System.err.println("No rows selected");
                throw new APIException(404, "No daily exercise was found!");
            }

            // Select a random exercise from 0 to last index of exercises (nextInt upper bound is exclusive)
            Random rng = new Random();
            int index = rng.nextInt(exercises.size());
            Exercise ex = exercises.get(index);

            // Update Exercise as Done (selected)
            exerciseService.markExerciseDone(conn, ex.getIdExercise());

            // Get Solution
            Solution solution = solutionService.getSolution(conn, ex.getIdSolution());

            // Verify if it's multiple choice
            if(ex.isMultiple()){
                MultipleChoice mChoice = multipleChoiceService.getMultipleChoice(conn, ex.getIdExercise());
                daily = new DailyExercise(ex, solution, mChoice);
            } else{
                daily = new DailyExercise(ex, solution);
            }
        }
        return daily;
    }
}
