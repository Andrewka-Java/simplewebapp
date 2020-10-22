package com.mastery.java.task.service;

import com.mastery.java.task.dao.EmployeeDao;
import com.mastery.java.task.dto.EmployeeDto;
import com.mastery.java.task.exception.EmployeeException;
import com.mastery.java.task.exception.NoEmployeeException;
import com.mastery.java.task.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mastery.java.task.dto.mapper.EmployeeMapper.toEmployeeDto;
import static com.mastery.java.task.dto.mapper.EmployeeMapper.toListEmployeeDto;


@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeDao employeeDao;

    @Autowired
    public EmployeeServiceImpl(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }


    @Override
    public List<EmployeeDto> findAllEmployee() {
        log.debug("The method findAllEmployee is starting");
        List<EmployeeDto> employeeDtos = toListEmployeeDto(employeeDao.findAll());

        log.debug("The method findAllEmployee was executed with response ({})", employeeDtos);
        return employeeDtos;
    }

    @Override
    public EmployeeDto findEmployeeById(Long id) throws NoEmployeeException {
        log.debug("The method findEmployeeById is starting with param ({})", id);
        Employee employee = employeeDao.findById(id)
                .orElseThrow(() -> new NoEmployeeException("No one employee was found."));

        EmployeeDto employeeDto = toEmployeeDto(employee);
        log.debug("The method findEmployeeById was executed with response ({})", employeeDto);

        return employeeDto;
    }

    @Override
    public Employee saveOrUpdate(Employee employee) {
        log.debug("The method saveOrUpdate is starting with param ({})", employee);
        Employee newEmployee = employeeDao.save(employee);

        log.debug("The method saveOrUpdate was executed with response ({})", newEmployee);
        return newEmployee;
    }

    @Override
    public void deleteEmployee(Long id) {
        log.debug("The method deleteEmployee is starting with param ({})", id);
        employeeDao.deleteById(id);
        log.debug("The method deleteEmployee was executed");
    }
}
