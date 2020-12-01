package com.mastery.java.task.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;


@ApiModel(value = "Employee")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employees", schema = "public")
public class Employee {

    @ApiModelProperty(value = "An identifier", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    @ApiModelProperty(value = "First name", example = "Andrei")
    @NotNull(message = "First name cannot be null")
    private String firstName;

    @ApiModelProperty(value = "Last name", example = "Muryn")
    @NotNull(message = "Last name cannot be null")
    private String lastName;

    @ApiModelProperty(value = "The number of department", example = "7")
    @Min(1)
    @NotNull(message = "Department cannot be null")
    private Integer departmentId;

    @ApiModelProperty(value = "The position name", example = "Java developer")
    @NotNull(message = "Job title cannot be null")
    @Size(min = 2, max = 50, message = "Job title size must be between 2 and 20 symbols")
    private String jobTitle;

    @ApiModelProperty(value = "Gender", example = "MALE")
    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ApiModelProperty(value = "Birth day of an employee", example = "1997-06-30")
    @NotNull
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dateOfBirth;


// TODO: It using for schema-migration with help liquibase
//
    @ApiModelProperty(value = "Employee's salary", example = "450")
    @NotNull(message = "Salary cannot be less than 400$")
    @Min(400)
    private BigDecimal salary;


    private String role;
    private String password;

}
