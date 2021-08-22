package com.mastery.java.task.dao;

import com.mastery.java.task.model.Employee;
import com.mastery.java.task.model.Gender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@TestPropertySource(locations = "classpath:application-it.properties")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class EmployeeDaoTestITCase {

    @Autowired
    private EmployeeDao dao;

    @Test
    public void findAll() {
        //When
        final List<Employee> employees = dao.findAll();

        //Then
        assertNotNull(employees);
        assertTrue(employees.size() > 0);
    }

    @Test
    public void findById() {
        //Given
        final Employee employee = createFixtureAdd();
        final Employee savedEmployee = dao.save(employee);

        //When
        final Employee foundEmployee = dao.findById(savedEmployee.getEmployeeId()).get();

        //Then
        assertEquals(savedEmployee, foundEmployee);
    }

    @Test
    public void add() {
        //Given
        final int sizeBefore = dao.findAll().size();
        final Employee employee = createFixtureAdd();

        //When
        final Employee savedEmployee = dao.save(employee);

        //Then
        final int sizeAfter = dao.findAll().size();
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
        //Given
        final Employee employee = createFixtureAdd();
        final Employee savedEmployee = dao.save(employee);
        final Employee foundEmployee = dao.findById(employee.getEmployeeId()).get();

        foundEmployee.setSalary(new BigDecimal("450"));
        foundEmployee.setLastName("newLastName");

        //When
        dao.save(savedEmployee);

        //Then
        assertNotEquals(foundEmployee, dao.findById(employee.getEmployeeId()));
    }

    @Test
    public void delete() {
        //Given
        final Employee savedEmployee = dao.save(createFixtureAdd());
        final int sizeBefore = dao.findAll().size();

        //When
        dao.deleteById(savedEmployee.getEmployeeId());

        //Then
        final int sizeAfter = dao.findAll().size();
        assertEquals(sizeBefore - 1, sizeAfter);
    }


    private Employee createFixtureAdd() {
        final Employee employee = new Employee();

        employee.setFirstName("firstName");
        employee.setLastName("lastName");
        employee.setDepartmentId(1);
        employee.setJobTitle("developer");
        employee.setGender(Gender.MALE);
        employee.setDateOfBirth(LocalDate.now());
        employee.setSalary(new BigDecimal("400"));

        return employee;
    }

}
