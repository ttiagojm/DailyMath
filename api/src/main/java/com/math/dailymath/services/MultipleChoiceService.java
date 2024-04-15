package com.math.dailymath.services;

import com.math.dailymath.errors.APIException;
import com.math.dailymath.models.MultipleChoice;


import java.sql.*;

public class MultipleChoiceService {

    public MultipleChoice getMultipleChoice(Connection conn, long IdExercise) throws APIException {
        System.out.println("Getting Multiple Choice Options!");
        String query = "SELECT Options FROM MULTIPLE_CHOICE WHERE Id_Exercise=?";

        try{
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setLong(1, IdExercise);
            ResultSet res = pstmt.executeQuery();

            // Get First row (only row)
            res.next();
            String options = res.getString("Options");

            res.close();
            pstmt.close();

            return new MultipleChoice(IdExercise, IdExercise, options);

        } catch (SQLException e){
            System.err.println(e.getMessage());
            throw new APIException(500, "Server Error!");
        }
    }
}
