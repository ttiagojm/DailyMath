package com.math.dailymath;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.math.dailymath.errors.APIException;
import com.math.dailymath.services.ExerciseService;
import com.math.dailymath.utils.Utils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.sql.Connection;

@WebServlet(name="AddExerciseServlet", urlPatterns = "/add")
public class AddExerciseServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        ExerciseService exerciseService = new ExerciseService();
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();

        try (Connection conn = Utils.getConnection()) {
            // Parse body and create JsonObject
            String json = Utils.parseBody(req.getReader());
            JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter printWriter = resp.getWriter();

            // Thread Safety guaranteed because getExercise is sync
            String exerciseString = gson.toJson(exerciseService.createExercise(conn, jsonObject));
            printWriter.print(exerciseString);
            printWriter.close();
            resp.setStatus(200);

        } catch (APIException e) {
            resp.setStatus(e.getStatusCode());
        } catch (Exception e){
            e.printStackTrace(System.err);
            resp.setStatus(500);
        }
    }
}

