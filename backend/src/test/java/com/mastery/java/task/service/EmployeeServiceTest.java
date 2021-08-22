package com.mastery.java.task.service;

import com.mastery.java.task.dao.EmployeeDao;
import com.mastery.java.task.dto.EmployeeDto;
import com.mastery.java.task.exception.NoEmployeeException;
import com.mastery.java.task.model.Employee;
import com.mastery.java.task.model.Gender;
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
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeDao employeeDao;

    @InjectMocks
    private EmployeeServiceImpl employeeService;


    @Test
    public void findAllEmployee() {
        //Given
        Mockito.when(employeeDao.findAll()).thenReturn(Collections.singletonList(createFixture()));

        //When
        final List<EmployeeDto> employeeDtoList = employeeService.findAllEmployee();

        //Then
        assertNotNull(employeeDtoList);
        assertEquals(1, employeeDtoList.size());
        verify(employeeDao).findAll();
    }

    @Test
    public void findByIdEmployee() throws NoEmployeeException {
        //Given
        Mockito.when(employeeDao.findById(1L))
                .thenReturn(createOptionalFixture());

        //When
        employeeService.findEmployeeById(1L);

        //Then
        verify(employeeDao).findById(1L);
    }

    @Test
    public void addEmployee() {
        //When
        employeeService.saveOrUpdate(createFixture());

        //Then
        verify(employeeDao).save(createFixture());
    }

    @Test
    public void updateEmployee() {
        //When
        employeeService.saveOrUpdate(createFixture());

        //Then
        verify(employeeDao).save(createFixture());
    }

    @Test
    public void deleteEmployee() throws NoEmployeeException {
        //When
        employeeService.deleteEmployee(1L);

        //Then
        verify(employeeDao).deleteById(1L);
    }

    @Test
    public void findByIdEmployeeUnhappyPath() throws NoEmployeeException {
        //Given
        doThrow(NoEmployeeException.class).when(employeeDao.findById(1000L));

        //When
        employeeService.findEmployeeById(1000L);

        //Then
        verify(employeeDao, Mockito.times(0)).findById(100L);
    }

    private Optional<Employee> createOptionalFixture() {
        return Optional.of(createFixture());
    }

    private Employee createFixture() {
        final Employee employee = createFixtureAdd();

        employee.setEmployeeId(1L);
        return employee;
    }

    private Employee createFixtureAdd() {
        final Employee employee = new Employee();

        employee.setFirstName("firstName");
        employee.setLastName("lastName");
        employee.setDepartmentId(1);
        employee.setJobTitle("developer");
        employee.setGender(Gender.MALE);
        employee.setDateOfBirth(LocalDate.now());

        return employee;
    }

}
