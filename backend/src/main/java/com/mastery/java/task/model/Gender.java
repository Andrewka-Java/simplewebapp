package com.mastery.java.task.model;

public enum Gender implements NameParameter {

    MALE("MALE"),
    FEMALE("FEMALE");

    private final String name;

    Gender(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
