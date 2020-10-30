package com.mastery.java.task.rest;

import com.mastery.java.task.exception.ApiException;
import com.mastery.java.task.exception.EmployeeException;
import com.mastery.java.task.exception.NoEmployeeException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;


@RestControllerAdvice
public class EmployeeExceptionHandler {

    @ExceptionHandler(NoEmployeeException.class)
    public ResponseEntity<Object> handleNoEmployeeException(Exception ex) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        ApiException apiException =
                new ApiException(ex.getMessage(), httpStatus, ZonedDateTime.now(ZoneId.of("Z")));

        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(EmployeeException.class)
    public ResponseEntity<Object> handleEmployeeException(Exception ex) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        ApiException apiException =
                new ApiException(ex.getMessage(), httpStatus, ZonedDateTime.now(ZoneId.of("Z")));

        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        ApiException apiException =
                new ApiException(ex.getMessage(), httpStatus, ZonedDateTime.now(ZoneId.of("Z")));

        return new ResponseEntity<>(apiException, httpStatus);
    }


}
