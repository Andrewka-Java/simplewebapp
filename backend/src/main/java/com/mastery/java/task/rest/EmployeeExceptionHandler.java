package com.mastery.java.task.rest;

import com.mastery.java.task.exception.ApiException;
import com.mastery.java.task.exception.EmployeeException;
import com.mastery.java.task.exception.NoEmployeeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZoneId;
import java.time.ZonedDateTime;


@RestControllerAdvice
public class EmployeeExceptionHandler {

    @ExceptionHandler(NoEmployeeException.class)
    public ResponseEntity<Object> handleNoEmployeeException(final Exception ex) {
        final HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        final ApiException apiException =
                new ApiException(ex.getMessage(), httpStatus, ZonedDateTime.now(ZoneId.of("Z")));

        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(EmployeeException.class)
    public ResponseEntity<Object> handleEmployeeException(final Exception ex) {
        final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        final ApiException apiException =
                new ApiException(ex.getMessage(), httpStatus, ZonedDateTime.now(ZoneId.of("Z")));

        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(final Exception ex) {
        final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        final ApiException apiException =
                new ApiException(ex.getMessage(), httpStatus, ZonedDateTime.now(ZoneId.of("Z")));

        return new ResponseEntity<>(apiException, httpStatus);
    }

}
