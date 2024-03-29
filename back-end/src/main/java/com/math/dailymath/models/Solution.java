package com.math.dailymath.models;

public class Solution {
    private long idSolution;
    private String solution;

    public Solution(long idSolution, String solution) {
        this.idSolution = idSolution;
        this.solution = solution;
    }

    /** Getters and Setters */
    public long getIdSolution() {
        return idSolution;
    }

    public void setIdSolution(long idSolution) {
        this.idSolution = idSolution;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }
}
