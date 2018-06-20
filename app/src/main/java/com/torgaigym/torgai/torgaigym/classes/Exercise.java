package com.torgaigym.torgai.torgaigym.classes;

public class Exercise {

    private String name;
    private String description;

    public Exercise() {

    }

    public Exercise(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
