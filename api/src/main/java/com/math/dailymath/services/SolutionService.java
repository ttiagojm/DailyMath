package com.math.dailymath.services;

import com.math.dailymath.errors.APIException;
import com.math.dailymath.models.DailySolution;
import com.math.dailymath.models.Solution;
import com.math.dailymath.utils.Utils;

import java.sql.*;


public class SolutionService  {

    /**
     * Method which gets the Daily Solution or the Daily Solution from previous day
     * @param conn
     * @param idSolution
     * @return
     * @throws APIException
     */
    public Solution getDailySolution(Connection conn, long idSolution) throws APIException {

        try {
            // Get DailySolution singleton instance
            DailySolution solution = DailySolution.getDailySolution(conn);

            // Actually Daily Solution
            if(solution.getIdSolution() == idSolution)
                return solution;

        } catch (APIException e){
            // If for some reason  there are no new DailyExercise and 404 was returned
            // just ignore the APIException and get the solution by ID
            if(e.getStatusCode() != 404)
                throw e;
        }

        // For users with out-dated exercise, get the solution for them
        return getSolution(conn, idSolution);
    }

    /**
     * Method which gets a solution by Id_Solution
     * @param conn
     * @param idSolution
     * @return
     * @throws APIException
     */
    public Solution getSolution(Connection conn, long idSolution) throws APIException {
        System.out.println("Getting Solution!");
        String query = "SELECT Solution FROM SOLUTION WHERE Id_Solution=?";

        try(PreparedStatement pstmt = conn.prepareStatement(query)) {
            ResultSet res = Utils.executeQuery(pstmt, idSolution);

            // Get First row (only row)
            res.next();
            String solutionStr = res.getString("Solution");
            return new Solution(idSolution, solutionStr);

        } catch (SQLException e) {
            e.printStackTrace(System.err);
            throw new APIException(500, "Server Error!");
        }
    }
}
