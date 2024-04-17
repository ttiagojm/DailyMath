package com.math.dailymath;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.math.dailymath.errors.APIException;
import com.math.dailymath.models.Exercise;
import com.math.dailymath.services.ExerciseService;
import com.math.dailymath.utils.Utils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.util.ArrayList;

@WebServlet(name="ExercisesDoneServlet", urlPatterns = "/exercises/done")
public class ExercisesDoneServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        ExerciseService exerciseService = new ExerciseService();
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();

        try (Connection conn = Utils.getConnection()) {
            // Parse body and create JsonObject
            String json = Utils.parseBody(req.getReader());
            //json = JsonParser.parseString(json).getAsString();
            Type listType = new TypeToken<ArrayList<Exercise>>() {}.getType();
            ArrayList<Exercise> exercises = gson.fromJson(json, listType);

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter printWriter = resp.getWriter();

            // Update
            exerciseService.markExercisesDone(conn, exercises);
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
