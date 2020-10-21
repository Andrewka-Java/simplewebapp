package com.mastery.java.task.dto;


import com.mastery.java.task.model.Gender;
import lombok.Value;

@Value
public class EmployeeDto {

    Long employeeId;
    String firstName;
    Gender gender;

}
