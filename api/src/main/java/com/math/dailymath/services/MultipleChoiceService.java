package com.math.dailymath.services;

import com.math.dailymath.errors.APIException;
import com.math.dailymath.models.MultipleChoice;
import com.math.dailymath.utils.Utils;

import java.sql.*;

public class MultipleChoiceService {

    public MultipleChoice getMultipleChoice(Connection conn, long idExercise) throws APIException {
        System.out.println("Getting Multiple Choice Options!");
        String query = "SELECT Id_Multiple,Options FROM MULTIPLE_CHOICE WHERE Id_Exercise=?";

        try(PreparedStatement pstmt = conn.prepareStatement(query)){
            ResultSet res = Utils.executeQuery(pstmt, idExercise);

            // Get First row (only row)
            res.next();
            long idMultiple = res.getLong("Id_Multiple");
            String options = res.getString("Options");

            return new MultipleChoice(idMultiple, idExercise, options);

        } catch (SQLException e){
            e.printStackTrace(System.err);
            throw new APIException(500, "Server Error!");
        }
    }

    public MultipleChoice insertMultipleChoice(Connection conn, long idExercise, String options) throws APIException {
        System.out.println("Inserting MultipleChoice!");
        String query = "INSERT INTO MULTIPLE_CHOICE (id_Exercise, Options) VALUES (?, ?)";

        try(PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            Utils.executeUpdate(pstmt, idExercise, options);

            // Get primary key generated
            ResultSet genKeys = pstmt.getGeneratedKeys();
            genKeys.next();

            long idMultiple = genKeys.getLong(1);
            return new MultipleChoice(idMultiple, idExercise, options);

        } catch (SQLException e) {
            e.printStackTrace(System.err);
            throw new APIException(500, "Server Error!");
        }
    }
}
