package com.math.dailymath.utils;

import java.sql.*;

/** Class with helper functions */
public class Utils {
    /** Method which creates a Database Connection */
    public static Connection getConnection() throws SQLException {
        // Configure shared variable and Database connection
        String database = System.getenv("POSTGRES_DB");
        String user = System.getenv("POSTGRES_USER");
        String passwd = System.getenv("POSTGRES_PASSWORD");
        Connection conn;

        try {
            Class.forName("org.postgresql.Driver");

            conn = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/"+database,
                    user,
                    passwd
            );
            System.out.println("CREATED DATABASE CONNECTION");
            return conn;

        } catch (ClassNotFoundException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public static int executeUpdate(PreparedStatement pstmt, Object... params) throws SQLException {
        setParameters(pstmt, params);
        return pstmt.executeUpdate();
    }

    public static ResultSet executeQuery(PreparedStatement pstmt, Object... params) throws SQLException {
        setParameters(pstmt, params);
        return pstmt.executeQuery();
    }

    private static void setParameters(PreparedStatement pstmt, Object... params) throws SQLException {
        for(int i = 0; i < params.length; i++){
            pstmt.setObject(i+1, params[i]);
        }
    }
}
