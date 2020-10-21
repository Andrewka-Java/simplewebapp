package com.mastery.java.task.dto.mapper;

import com.mastery.java.task.dto.EmployeeDto;
import com.mastery.java.task.model.Employee;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class EmployeeMapper {

    public static EmployeeDto toEmployeeDto(Employee employee) {
        return EmployeeDto.builder()
                .employeeId(employee.getEmployeeId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .jobTitle(employee.getJobTitle())
                .gender(employee.getGender())
                .build();
    }

    public static List<EmployeeDto> toListEmployeeDto(List<Employee> employees) {
        return employees.stream()
                .map(EmployeeMapper::toEmployeeDto)
                .collect(Collectors.toList());
    }

}
