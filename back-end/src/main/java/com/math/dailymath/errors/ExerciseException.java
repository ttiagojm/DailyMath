package com.math.dailymath.errors;

public class ExerciseException extends Exception{
    private final int statusCode;
    public ExerciseException(int statusCode, String errorMessage){
        super(errorMessage);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
