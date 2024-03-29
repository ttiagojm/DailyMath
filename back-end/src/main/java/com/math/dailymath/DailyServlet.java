package com.math.dailymath;

import com.google.gson.Gson;
import com.math.dailymath.services.ServiceExercise;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name="DailyServlet", urlPatterns = "/daily")
public class DailyServlet extends HttpServlet {

    // This variables are shared with all requests (thread safety can be a problem)
    private final Gson gson = new Gson();
    private final ServiceExercise exercise = new ServiceExercise();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = resp.getWriter();

        // Thread Safety guaranteed
        synchronized (this) {
            String exerciseString = gson.toJson(exercise.getExercise());
            printWriter.print(exerciseString);
            printWriter.close();
        }
    }
}