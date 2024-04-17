package com.math.dailymath.dao;

import com.google.gson.annotations.Expose;
import com.math.dailymath.models.Exercise;
import com.math.dailymath.models.MultipleChoice;
import com.math.dailymath.models.Solution;

import java.util.ArrayList;
import java.util.Arrays;

public class DaoExercise {
    @Expose
    private long idExercise;
    @Expose
    private Solution solution;
    @Expose
    private String typeExercise;
    @Expose
    private String source;
    @Expose
    private String exercise;
    @Expose
    private ArrayList<String> options;


    public DaoExercise(Exercise exercise, Solution solution) {
        this.idExercise = exercise.getIdExercise();
        this.solution = solution;
        this.typeExercise = exercise.getTypeExercise();
        this.source = exercise.getSource();
        this.exercise = exercise.getExercise();
    }

    public DaoExercise(Exercise exercise, Solution solution, MultipleChoice multipleChoice) {
        this(exercise, solution);
        String options = multipleChoice.getOptions();
        this.options = new ArrayList<>(Arrays.asList(options.split(",")));
    }

    /** Getters and Setters */
    public long getIdExercise() {
        return idExercise;
    }

    public void setIdExercise(long idExercise) {
        this.idExercise = idExercise;
    }

    public Solution getSolution() {
        return solution;
    }

    public void setSolution(Solution solution) {
        this.solution = solution;
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

    public ArrayList<String> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }
}
