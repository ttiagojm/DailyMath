package com.math.dailymath.services;

import com.math.dailymath.errors.ExerciseException;
import com.math.dailymath.models.Solution;

import java.sql.*;


public class SolutionService  {
    private static Solution solution = null;

    public synchronized Solution getSolution(Connection conn, long idSolution) throws ExerciseException {
        if(solution != null && solution.getIdSolution() == idSolution)
            return solution;

        System.out.println("Getting Solution!");
        String query = "SELECT Solution FROM SOLUTION WHERE Id_Solution=?";

        try{
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setLong(1, idSolution);
            ResultSet res = pstmt.executeQuery();

            // Get First row (only row)
            res.next();
            String solutionStr = res.getString("Solution");

            res.close();
            pstmt.close();

            return new Solution(idSolution, solutionStr);

        } catch (SQLException e){
            System.err.println(e.getMessage());
            throw new ExerciseException(500, "Server Error!");
        }
    }

    public synchronized void updateSolution(Connection conn, long idSolution) throws ExerciseException {
        System.out.println("Updating Solution!");
        solution = getSolution(conn, idSolution);
    }
}
