package com.mastery.java.task.exceptions;

import lombok.ToString;

@ToString
public class EmployeeException extends Exception {

    private final String message;

    public EmployeeException(String message) {
        this.message = message;
    }

}
