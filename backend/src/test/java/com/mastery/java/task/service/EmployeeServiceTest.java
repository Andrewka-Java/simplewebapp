package com.mastery.java.task.service;

import com.mastery.java.task.dao.EmployeeDao;
import com.mastery.java.task.dto.EmployeeDto;
import com.mastery.java.task.exception.NoEmployeeException;
import com.mastery.java.task.model.Employee;
import com.mastery.java.task.model.Gender;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@Slf4j
@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeDao employeeDao;

    @InjectMocks
    private EmployeeServiceImpl employeeService;


    @Test
    public void findAllEmployee() {
        log.debug("Test method findAllEmployee is starting execute");

        Mockito.when(employeeDao.findAll()).thenReturn(Collections.singletonList(createFixture().get()));

        List<EmployeeDto> employeeDtoList = employeeService.findAllEmployee();

        assertNotNull(employeeDtoList);
        assertEquals(1, employeeDtoList.size());

        Mockito.verify(employeeDao).findAll();

        log.debug("Test method findAllEmployee was executed");
    }

    @Test
    public void findByIdEmployee() throws NoEmployeeException {
        log.debug("Test method findByIdEmployee is starting execute");

        Mockito.when(employeeDao.findById(1L))
                .thenReturn(createFixture());

        employeeService.findEmployeeById(1L);

        Mockito.verify(employeeDao).findById(1L);

        log.debug("Test method findByIdEmployee was executed");
    }

    @Test
    public void addEmployee() {
        log.debug("Test method addEmployee is starting execute");

        employeeService.saveOrUpdate(createFixtureAdd().get());

        Mockito.verify(employeeDao).save(createFixtureAdd().get());

        log.debug("Test method addEmployee was executed");
    }

    @Test
    public void updateEmployee() {
        log.debug("Test method updateEmployee is starting execute");

        employeeService.saveOrUpdate(createFixture().get());

        Mockito.verify(employeeDao).save(createFixture().get());

        log.debug("Test method updateEmployee was executed");
    }

    @Test
    public void deleteEmployee() throws NoEmployeeException {
        log.debug("Test method deleteEmployee is starting execute");

        employeeService.deleteEmployee(1L);

        Mockito.verify(employeeDao).deleteById(1L);

        log.debug("Test method deleteEmployee was executed");
    }

    @Test
    public void findByIdEmployeeBad() throws NoEmployeeException {
        log.debug("Test method findByIdEmployee is starting execute");


        try {
            Mockito.when(employeeDao.findById(1000L)).thenThrow(new NoEmployeeException("No one employee was found."));
            employeeService.findEmployeeById(1000L);

        } catch (Exception ex) {
            log.debug("The exception was thrown in bad test case. Message: {}", ex.getMessage());
        }

        Mockito.verify(employeeDao, Mockito.times(0)).findById(100L);

        log.debug("Test method findByIdEmployee was executed");
    }



    private Optional<Employee> createFixture() {
        Employee employee = new Employee();

        employee.setEmployeeId(1L);
        employee.setFirstName("firstName");
        employee.setLastName("lastName");
        employee.setDepartmentId(1);
        employee.setJobTitle("developer");
        employee.setGender(Gender.MALE);
        employee.setDateOfBirth(LocalDate.now());

        return Optional.of(employee);
    }

    private Optional<Employee> createFixtureAdd() {
        Employee employee = new Employee();

        employee.setFirstName("firstName");
        employee.setLastName("lastName");
        employee.setDepartmentId(1);
        employee.setJobTitle("developer");
        employee.setGender(Gender.MALE);
        employee.setDateOfBirth(LocalDate.now());

        return Optional.of(employee);
    }
}
