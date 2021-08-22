package com.mastery.java.task.rest;

import com.mastery.java.task.Application;
import com.mastery.java.task.model.Employee;
import com.mastery.java.task.model.Gender;
import com.mastery.java.task.service.EmployeeService;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;


@DirtiesContext
@SpringBootTest(classes = Application.class)
public abstract class AbstractTest {

    protected MockMvc mockMvc;

    @MockBean
    protected EmployeeService service;

    @MockBean
    protected JmsTemplate jmsTemplate;

    @Autowired
    private EmployeeController controller;

    @Autowired
    protected EmployeeExceptionHandler exceptionHandler;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    protected void before() {
        mockMvc = MockMvcBuilders.standaloneSetup(exceptionHandler, controller)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .build();
    }

    protected void after() {
        Mockito.reset(service);
    }

    protected Optional<Employee> createOptionalFixture() {
        return Optional.of(createFixture());
    }

    protected Employee createFixture() {
        final Employee employee = createFixtureAdd();
        employee.setEmployeeId(1L);

        return employee;
    }

    protected Employee createFixtureAdd() {
        final Employee employee = new Employee();

        employee.setFirstName("firstName");
        employee.setLastName("lastName");
        employee.setDepartmentId(1);
        employee.setJobTitle("developer");
        employee.setGender(Gender.MALE);
        employee.setDateOfBirth(LocalDate.now());
        employee.setSalary(new BigDecimal("400"));
        employee.setPassword("12345");

        return employee;
    }

}
