package com.math.dailymath;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.math.dailymath.services.ServiceExercise;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebServlet(name="DailyServlet", urlPatterns = "/daily")
public class DailyServlet extends HttpServlet {

    // This variables are shared with all requests (thread safety can be a problem)
    private final Gson gson = new GsonBuilder()
                                .excludeFieldsWithoutExposeAnnotation()
                                .create();
    private final ServiceExercise exercise = new ServiceExercise();
    Connection conn = null;

    /**
     * Initialization of Servlet where it's initialize the database
     * @throws ServletException
     */
    public void init() throws ServletException {
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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = resp.getWriter();

        // Thread Safety guaranteed
        synchronized (this) {
            String exerciseString = gson.toJson(exercise.getExercise(conn));
            printWriter.print(exerciseString);
            printWriter.close();
        }
    }
}