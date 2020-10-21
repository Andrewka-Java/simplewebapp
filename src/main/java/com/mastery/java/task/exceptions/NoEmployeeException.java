package com.mastery.java.task.exceptions;


import lombok.ToString;

@ToString
public class NoEmployeeException extends Exception {

    private final String message;

    public NoEmployeeException(String message) {
        this.message = message;
    }

}
