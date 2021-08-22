package com.mastery.java.task.service;

import com.mastery.java.task.dto.EmployeeDto;
import com.mastery.java.task.exception.NoEmployeeException;
import com.mastery.java.task.model.Employee;

import java.util.List;


public interface EmployeeService {

    List<EmployeeDto> findAllEmployee();

    EmployeeDto findEmployeeById(long id) throws NoEmployeeException;

    void saveOrUpdate(Employee employee);

    void deleteEmployee(long id) throws NoEmployeeException;

}
