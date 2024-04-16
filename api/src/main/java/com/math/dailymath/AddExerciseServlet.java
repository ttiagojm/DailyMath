package com.math.dailymath;

//@WebServlet(name="AddExerciseServlet", urlPatterns = "/add")
//public class AddExerciseServlet extends HttpServlet {
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        ServletContext  ctx = getServletContext();
//        ExerciseService exerciseService = (ExerciseService) ctx.getAttribute("exerciseService");
//        Gson gson = new GsonBuilder()
//                .excludeFieldsWithoutExposeAnnotation()
//                .create();
//
//        try (Connection conn = Utils.getConnection()) {
//            resp.setContentType("application/json");
//            resp.setCharacterEncoding("UTF-8");
//            PrintWriter printWriter = resp.getWriter();
//
//            // Thread Safety guaranteed because getExercise is sync
//            String exerciseString = gson.toJson(exerciseService.insertExercise(conn));
//            printWriter.print(exerciseString);
//            printWriter.close();
//            resp.setStatus(200);
//
//        } catch (APIException e) {
//            resp.setStatus(e.getStatusCode());
//        } catch (SQLException e){
//            System.out.println("SQL error: " + e.getMessage());
//            resp.setStatus(500);
//        }
//    }
//}
