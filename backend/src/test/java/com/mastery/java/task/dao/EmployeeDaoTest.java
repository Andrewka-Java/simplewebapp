package com.mastery.java.task.dao;

import com.mastery.java.task.model.Employee;
import com.mastery.java.task.model.Gender;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@Slf4j
@SpringBootTest
@Transactional
public class EmployeeDaoTest {

    @Autowired
    private EmployeeDao dao;

    @Test
    public void findAll() {
        log.debug("The test method findAll is starting");
        List<Employee> employees = dao.findAll();
        log.debug("The test method findAll with employees ({})", employees);

        assertNotNull(employees);
        assertTrue(employees.size() > 0);
    }

    @Test
    public void findById() {
        log.debug("The test method findById is starting");
        Employee employee = createFixtureAdd().get();
        Employee savedEmployee = dao.save(employee);

        log.debug("The test method findById with savedEmployee ({})", savedEmployee);
        Employee foundEmployee = dao.findById(savedEmployee.getEmployeeId()).get();
        log.debug("The test method findById with foundEmployee ({})", foundEmployee);

        assertEquals(savedEmployee, foundEmployee);
    }

    @Test
    public void add() {
        log.debug("The test method add is starting");
        int sizeBefore = dao.findAll().size();
        Employee employee = createFixtureAdd().get();
        log.debug("The test method add with employee and a size({}, {})", employee, sizeBefore);


        Employee savedEmployee = dao.save(employee);
        int sizeAfter = dao.findAll().size();
        log.debug("The test method add with savedEmployee and a size ({}, {})", savedEmployee, sizeAfter);

        assertEquals(sizeBefore + 1, sizeAfter);
        assertNotNull(savedEmployee.getEmployeeId());
        assertEquals(employee.getFirstName(), savedEmployee.getFirstName());
        assertEquals(employee.getLastName(), savedEmployee.getLastName());
        assertEquals(employee.getDepartmentId(), savedEmployee.getDepartmentId());
        assertEquals(employee.getJobTitle(), savedEmployee.getJobTitle());
        assertEquals(employee.getGender(), savedEmployee.getGender());
        assertEquals(employee.getDateOfBirth(), savedEmployee.getDateOfBirth());
    }

    @Test
    public void update() {
        log.debug("The test method update is starting");
        Employee employee = createFixtureAdd().get();

        Employee savedEmployee = dao.save(employee);
        Employee foundEmployee = dao.findById(employee.getEmployeeId()).get();
        log.debug(
                "The test method update with a savedEmployee and a foundEmployee({}, {})", savedEmployee, foundEmployee);

        foundEmployee.setSalary(new BigDecimal("450"));
        foundEmployee.setLastName("newLastName");

        dao.save(savedEmployee);

        assertNotEquals(foundEmployee, dao.findById(employee.getEmployeeId()));
    }

    @Test
    public void delete() {
        log.debug("The test method delete is starting");
        Employee savedEmployee = dao.save(createFixtureAdd().get());
        int sizeBefore = dao.findAll().size();
        log.debug("The test method delete with a savedEmployee and a size ({}, {})", savedEmployee, sizeBefore);


        dao.deleteById(savedEmployee.getEmployeeId());
        int sizeAfter = dao.findAll().size();
        log.debug("The test method delete with a size ({})", sizeBefore);

        assertEquals(sizeBefore - 1, sizeAfter);
    }


    private Optional<Employee> createFixtureAdd() {
        Employee employee = new Employee();

        employee.setFirstName("firstName");
        employee.setLastName("lastName");
        employee.setDepartmentId(1);
        employee.setJobTitle("developer");
        employee.setGender(Gender.MALE);
        employee.setDateOfBirth(LocalDate.now());
        employee.setSalary(new BigDecimal("400"));

        return Optional.of(employee);
    }
}
