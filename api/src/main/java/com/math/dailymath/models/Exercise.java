package com.math.dailymath.models;

import com.google.gson.annotations.Expose;

public class Exercise {
    private long idExercise;
    @Expose
    private long idSolution;
    @Expose
    private String typeExercise;
    @Expose
    private String source;
    @Expose
    private String exercise;
    @Expose
    private boolean isMultiple;
    private boolean isDone;

    public Exercise(long idExercise, long idSolution, String typeExercise,
                    String source, String exercise, boolean isMultiple, boolean isDone) {
        this.idExercise = idExercise;
        this.idSolution = idSolution;
        this.typeExercise = typeExercise;
        this.source = source;
        this.exercise = exercise;
        this.isMultiple = isMultiple;
        this.isDone = isDone;
    }

    /** Getters and Setters */
    public long getIdExercise() {
        return idExercise;
    }

    public void setIdExercise(long idExercise) {
        this.idExercise = idExercise;
    }

    public long getIdSolution() {
        return idSolution;
    }

    public void setIdSolution(long idSolution) {
        this.idSolution = idSolution;
    }

    public String getTypeExercise() {
        return typeExercise;
    }

    public void setTypeExercise(String typeExercise) {
        this.typeExercise = typeExercise;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public boolean isMultiple() {
        return isMultiple;
    }

    public void setMultiple(boolean multiple) {
        isMultiple = multiple;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
