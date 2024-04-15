package com.math.dailymath;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.math.dailymath.errors.ExerciseException;
import com.math.dailymath.services.ExerciseService;

import javax.servlet.ServletContext;
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

    private final ExerciseService exerciseService = new ExerciseService();

    /**
     * Initialization of Servlet where it's initialize the database
     * @throws ServletException
     */
    public void init() throws ServletException {

        // Configure shared variable and Database connection
        String database = System.getenv("POSTGRES_DB");
        String user = System.getenv("POSTGRES_USER");
        String passwd = System.getenv("POSTGRES_PASSWORD");
        Connection conn;
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();

        try {
            Class.forName("org.postgresql.Driver");

            conn = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/"+database,
                    user,
                    passwd
            );
            System.out.print("CREATED DATABASE");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Set share variables between servlets
        ServletContext context = getServletContext();
        context.setAttribute("conn", conn);
        context.setAttribute("gson", gson);

        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        ServletContext context = getServletContext();
        Gson gson = (Gson) context.getAttribute("gson");
        Connection conn = (Connection) context.getAttribute("conn");

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = resp.getWriter();

        // Thread Safety guaranteed because getExercise is sync
        try {
            String exerciseString = gson.toJson(exerciseService.getExercise(conn));
            printWriter.print(exerciseString);
            printWriter.close();
            resp.setStatus(200);
        } catch (ExerciseException e){
            resp.setStatus(e.getStatusCode());
        }
    }

    @Override
    public void destroy() {
        ServletContext context = getServletContext();
        Connection conn = (Connection) context.getAttribute("conn");

        try {
            conn.commit();
            conn.close();

            context.removeAttribute("conn");
            context.removeAttribute("exerciseService");
            context.removeAttribute("gson");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        super.destroy();
    }
}