package com.math.dailymath.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Database db = null;
    private Connection conn;

    private Database(){
        String database = System.getenv("POSTGRES_DB");
        String user = System.getenv("POSTGRES_USER");
        String passwd = System.getenv("POSTGRES_PASSWORD");

        try {
            Class.forName("org.postgresql.Driver");

            this.conn = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/"+database,
                    user,
                    passwd
            );
            System.out.print("CREATED DATABASE");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Database getDatabase(){
        if(Database.db == null)
            Database.db = new Database();

        return Database.db;
    }
}
