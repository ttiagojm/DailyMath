package com.math.dailymath;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.math.dailymath.errors.APIException;
import com.math.dailymath.services.ExerciseService;
import com.math.dailymath.utils.Utils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name="AddExerciseServlet", urlPatterns = "/add")
public class AddExerciseServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext  ctx = getServletContext();
        ExerciseService exerciseService = (ExerciseService) ctx.getAttribute("exerciseService");
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();

        try (Connection conn = Utils.getConnection()) {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter printWriter = resp.getWriter();

            // Thread Safety guaranteed because getExercise is sync
            String exerciseString = gson.toJson(exerciseService.insertExercise(conn));
            printWriter.print(exerciseString);
            printWriter.close();
            resp.setStatus(200);

        } catch (APIException e) {
            resp.setStatus(e.getStatusCode());
        } catch (SQLException e){
            System.out.println("SQL error: " + e.getMessage());
            resp.setStatus(500);
        }
    }
}
