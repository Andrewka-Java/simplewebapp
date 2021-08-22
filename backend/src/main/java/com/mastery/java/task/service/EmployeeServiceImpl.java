package com.mastery.java.task.service;

import com.mastery.java.task.dao.EmployeeDao;
import com.mastery.java.task.dto.EmployeeDto;
import com.mastery.java.task.exception.NoEmployeeException;
import com.mastery.java.task.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mastery.java.task.dto.mapper.EmployeeMapper.toEmployeeDto;
import static com.mastery.java.task.dto.mapper.EmployeeMapper.toListEmployeeDto;
import static java.lang.String.format;


@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final String NO_ONE_EMPLOYEE_WAS_FOUND = "No one employee with id [%s] was found.";

    private final EmployeeDao employeeDao;

    @Autowired
    public EmployeeServiceImpl(final EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }


    @Override
    public List<EmployeeDto> findAllEmployee() {
        return toListEmployeeDto(employeeDao.findAll());
    }

    @Override
    public EmployeeDto findEmployeeById(final long id) throws NoEmployeeException {
        final Employee employee = employeeDao.findById(id)
                .orElseThrow(() -> new NoEmployeeException(format(NO_ONE_EMPLOYEE_WAS_FOUND, id)));
        return toEmployeeDto(employee);
    }

    @Override
    @JmsListener(destination = "saveOrUpdate")
    public void saveOrUpdate(final Employee employee) {
        employeeDao.save(employee);
    }

    @Override
    @JmsListener(destination = "deleteEmployee")
    public void deleteEmployee(final long id) throws NoEmployeeException {
        try {
            employeeDao.deleteById(id);
        } catch (final Exception ex) {
            throw new NoEmployeeException(format(NO_ONE_EMPLOYEE_WAS_FOUND, id));
        }
    }

}
