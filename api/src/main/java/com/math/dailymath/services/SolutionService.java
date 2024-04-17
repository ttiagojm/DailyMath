package com.math.dailymath.services;

import com.math.dailymath.errors.APIException;
import com.math.dailymath.models.Solution;
import com.math.dailymath.utils.Utils;

import java.sql.*;


public class SolutionService  {

    /**
     * Method which gets a solution by Id_Solution
     * @param conn
     * @param idSolution
     * @return
     * @throws APIException
     */
    public Solution getSolution(Connection conn, long idSolution) throws APIException {
        System.out.println("Getting Solution!");
        String query = "SELECT SOLUTION FROM SOLUTION WHERE Id_Solution=?";

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

    public Solution insertSolution(Connection conn, String solution) throws APIException {
        System.out.println("Inserting Solution!");
        String query = "INSERT INTO SOLUTION (solution) VALUES (?)";

        try(PreparedStatement pstmt = conn.prepareStatement(query)){
            ResultSet res = Utils.executeQuery(pstmt, solution);
            res.next();
            long idSolution = res.getLong("Id_Solution");
            return new Solution(idSolution, solution);

        } catch (SQLException e) {
            e.printStackTrace(System.err);
            throw new APIException(500, "Server Error!");
        }
    }
}
