package com.mastery.java.task.rest;

import com.mastery.java.task.dto.EmployeeDto;
import com.mastery.java.task.exception.EmployeeException;
import com.mastery.java.task.exception.NoEmployeeException;
import com.mastery.java.task.model.Employee;
import com.mastery.java.task.service.EmployeeService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Slf4j
@Api(value = "Employee API", description = "Operations pertaining to an Employee")
@RestController
@RequestMapping("/v1")
@CrossOrigin("http://localhost:4200")
public class EmployeeController {

    private final EmployeeService service;
    private final JmsTemplate jmsTemplate;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public EmployeeController(EmployeeService service, JmsTemplate jmsTemplate, PasswordEncoder passwordEncoder) {
        this.service = service;
        this.jmsTemplate = jmsTemplate;
        this.passwordEncoder = passwordEncoder;
    }


    @ApiOperation(value = "Get a list of employees", response = List.class)
    @GetMapping(value = "/employees", produces = "application/json")
    public List<EmployeeDto> findAllEmployee() {
        log.debug("The method findAllEmployee is starting");
        List<EmployeeDto> employeeDtos = service.findAllEmployee();

        log.debug("The method findAllEmployee was executed with response ({})", employeeDtos);
        return employeeDtos;
    }

    @ApiOperation(value = "Get a employee by an id", response = Employee.class)
    @GetMapping(value = "/employees/{id}", produces = "application/json")
    public EmployeeDto findEmployeeById(@PathVariable("id") Long id) throws NoEmployeeException {
        log.debug("The method findEmployeeById is starting with param ({})", id);
        EmployeeDto employeeDto = service.findEmployeeById(id);

        log.debug("The method findEmployeeById was executed with response ({})", employeeDto);
        return employeeDto;
    }

    @ApiOperation(value = "Save an employee", response = void.class)
    @PostMapping(value = "/employees", consumes = "application/json")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void saveEmployee(@Valid @RequestBody Employee employee) throws EmployeeException {
        log.debug("The method saveEmployee is starting with param ({})", employee);

        if (employee.getEmployeeId() != null) throw new EmployeeException("Such an employee isn't correct.");

        String password = employee.getPassword();
        employee.setPassword(passwordEncoder.encode(password));

        jmsTemplate.convertAndSend("saveOrUpdate", employee);

        log.debug("The method saveEmployee was executed with response ({})", employee);
    }

    @ApiOperation(value = "Update an employee", response = void.class)
    @PutMapping(value = "/employees/{id}", consumes = "application/json")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateEmployee(@PathVariable("id") Long id,
                                @RequestBody @Valid Employee employee) throws EmployeeException {
        log.debug("The method updateEmployee is starting with param ({})", employee);

        if (employee.getEmployeeId() == null || !employee.getEmployeeId().equals(id))
            throw new EmployeeException("Such an employee isn't correct.");

        jmsTemplate.convertAndSend("saveOrUpdate", employee);

        log.debug("The method updateEmployee was executed with response ({})", employee);
    }

    @ApiOperation(value = "Delete an employee", response = void.class)
    @DeleteMapping(value = "/employees/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable("id") Long id) {
        log.debug("The method deleteEmployee is starting with param ({})", id);
        jmsTemplate.convertAndSend("deleteEmployee", id);
        log.debug("The method deleteEmployee was executed");
    }

}
