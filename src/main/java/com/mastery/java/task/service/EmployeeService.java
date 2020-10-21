package com.mastery.java.task.service;

import com.mastery.java.task.exception.EmployeeException;
import com.mastery.java.task.exception.NoEmployeeException;
import com.mastery.java.task.model.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> findAll();
    Employee findById(Long id) throws NoEmployeeException;
    Employee saveOrUpdate(Employee employee);
    void delete(Long id);
}
