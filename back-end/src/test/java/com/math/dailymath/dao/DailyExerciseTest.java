package com.math.dailymath.dao;

import com.math.dailymath.errors.APIException;
import com.math.dailymath.utils.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

class DailyExerciseTest {

    DailyExercise testDaily() throws SQLException, APIException {
        Connection conn = Utils.getConnection();
        return DailyExercise.getDailyExercise(conn);
    }

    @Test
    void getDailyExercise() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(2);
        Future<DailyExercise> e1 = service.submit(this::testDaily);
        Future<DailyExercise> e2 = service.submit(this::testDaily);
        service.awaitTermination(2000, TimeUnit.MILLISECONDS);

        Assertions.assertEquals(e1, e2);
    }
}