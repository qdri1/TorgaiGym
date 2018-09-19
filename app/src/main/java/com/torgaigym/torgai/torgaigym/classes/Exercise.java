package com.torgaigym.torgai.torgaigym.classes;

public class Exercise {

    private String exerciseId;
    private String name;
    private String description;

    public Exercise(String exerciseId, String name, String description) {
        this.exerciseId = exerciseId;
        this.name = name;
        this.description = description;
    }

    public String getExerciseId() {
        return exerciseId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
