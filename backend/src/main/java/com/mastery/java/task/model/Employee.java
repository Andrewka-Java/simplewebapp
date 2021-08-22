package com.mastery.java.task.model;

import com.mastery.java.task.model.converter.GenderConverter;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@Entity
@Table(name = "employees", schema = "public")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    @NotNull(message = "First name cannot be null")
    private String firstName;

    @NotNull(message = "Last name cannot be null")
    private String lastName;

    @Min(1)
    @NotNull(message = "Department cannot be null")
    private Integer departmentId;

    @NotNull(message = "Job title cannot be null")
    @Size(min = 2, max = 50, message = "Job title size must be between 2 and 20 symbols")
    private String jobTitle;

    @NotNull
//    @Enumerated(EnumType.STRING)
    @Convert(converter = GenderConverter.class)
    private Gender gender;

    @NotNull
    private LocalDate dateOfBirth;

    @NotNull(message = "Salary can't be less than 400$")
    @Min(400)
    private BigDecimal salary;


    private String role;
    private String password;

}
