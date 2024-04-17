package com.math.dailymath.utils;

import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

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

    /**
     * Verify if a JSONObject has a list of keys
     * @param json
     * @param keys
     * @return
     */
    public static boolean hasAllKeys(JsonObject json, ArrayList<String> keys){
        for(String k : keys){
            if(!json.has(k)) return false;
        }
        return true;
    }

    public static String parseBody(BufferedReader reader) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;

        while((line = reader.readLine()) != null){
            sb.append(line);
        }

        return sb.toString();
    }

    /**
     * Execute an update with an arbitrary number of parameters
     * @param pstmt
     * @param params
     * @return
     * @throws SQLException
     */
    public static int executeUpdate(PreparedStatement pstmt, Object... params) throws SQLException {
        setParameters(pstmt, params);
        return pstmt.executeUpdate();
    }

    /**
     * Execute a query with an arbitrary number of parameters
     * @param pstmt
     * @param params
     * @return
     * @throws SQLException
     */
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
