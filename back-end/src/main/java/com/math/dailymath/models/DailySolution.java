package com.math.dailymath.models;

import com.math.dailymath.dao.DailyExercise;
import com.math.dailymath.errors.APIException;
import com.math.dailymath.services.SolutionService;

import java.sql.Connection;

public class DailySolution extends Solution {
    // Singleton since everyone should have the same DailySolution
    private static DailySolution solution;

    private DailySolution(Solution sol) {
        super(sol.getIdSolution(), sol.getSolution());
    }

    public static synchronized DailySolution getDailySolution(Connection conn) throws APIException {
        DailyExercise dailyExercise = DailyExercise.getDailyExercise(conn);
        // If doesn't exist solution or the solution changed, update it!
        if(solution == null || dailyExercise.getIdSolution() != solution.getIdSolution()){
            SolutionService solutionService = new SolutionService();
            Solution sol = solutionService.getSolution(conn, dailyExercise.getIdSolution());
            solution = new DailySolution(sol);
        }

        return solution;
    }
}
