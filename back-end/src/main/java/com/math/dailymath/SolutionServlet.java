package com.math.dailymath;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.math.dailymath.errors.ExerciseException;
import com.math.dailymath.services.SolutionService;
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

@WebServlet(name="SolutionServlet", urlPatterns = "/solution")
public class SolutionServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        ServletContext ctx = getServletContext();
        ctx.setAttribute("solutionService", new SolutionService());

        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long idSolution = Long.parseLong(req.getParameter("idSolution"));
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        ServletContext ctx = getServletContext();
        SolutionService solutionService = (SolutionService) ctx.getAttribute("solutionService");

        try (Connection conn = Utils.getConnection()) {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter printWriter = resp.getWriter();

            // Thread Safety guaranteed because getSolution is sync
            String exerciseString = gson.toJson(solutionService.getSolution(conn, idSolution));
            printWriter.print(exerciseString);
            printWriter.close();
            resp.setStatus(200);

        } catch (ExerciseException e) {
            resp.setStatus(e.getStatusCode());
        } catch (SQLException e){
            System.out.println("SQL error: " + e.getMessage());
            resp.setStatus(500);
        }
    }
}
