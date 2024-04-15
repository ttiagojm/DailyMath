package com.math.dailymath.models;

public class MultipleChoice {
    private long idMultiple;
    private long idExercise;
    private String options;

    public MultipleChoice(long idMultiple, long idExercise, String options) {
        this.idMultiple = idMultiple;
        this.idExercise = idExercise;
        this.options = options;
    }

    /** Getters and Setters */
    public long getIdMultiple() {
        return idMultiple;
    }

    public void setIdMultiple(long idMultiple) {
        this.idMultiple = idMultiple;
    }

    public long getIdExercise() {
        return idExercise;
    }

    public void setIdExercise(long idExercise) {
        this.idExercise = idExercise;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }
}
