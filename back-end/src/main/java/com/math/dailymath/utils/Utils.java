package com.math.dailymath.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** Class with helper functions */
public class Utils {
    /** Method which creates a Database Connection */
    public static Connection getConnection() {
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
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
