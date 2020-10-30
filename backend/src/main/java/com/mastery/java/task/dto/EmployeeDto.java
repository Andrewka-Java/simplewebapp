package com.mastery.java.task.dto;


import com.mastery.java.task.model.Gender;
import lombok.Builder;
import lombok.Value;


@Value
@Builder
public class EmployeeDto {

    Long employeeId;
    String firstName;
    String lastName;
    String jobTitle;
    Gender gender;

}
