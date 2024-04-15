package com.math.dailymath.errors;

public class APIException extends Exception{
    private final int statusCode;
    public APIException(int statusCode, String errorMessage){
        super(errorMessage);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
