/*
 *   Developed by Andrei Muryn© 2021
 */

package com.mastery.java.task.model.converter.exception;

public class ConverterException extends RuntimeException {

    public ConverterException(String message) {
        super(message);
    }

    public ConverterException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
