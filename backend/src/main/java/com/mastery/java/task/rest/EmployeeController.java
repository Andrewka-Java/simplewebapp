package com.mastery.java.task.rest;

import com.mastery.java.task.dto.EmployeeDto;
import com.mastery.java.task.exception.EmployeeException;
import com.mastery.java.task.exception.NoEmployeeException;
import com.mastery.java.task.model.Employee;
import com.mastery.java.task.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Api(value = "Employee API", description = "Operations pertaining to an Employee")
@RestController
@RequestMapping("/v1")
@CrossOrigin("http://localhost:4200")
public class EmployeeController {

    private static final String DESTINATION_SAVE_OR_UPDATE = "saveOrUpdate";
    private static final String DESTINATION_DELETE_EMPLOYEE = "deleteEmployee";

    private static final String EMPLOYEE_NOT_FOUND_ERROR_MESSAGE = "Such an employee isn't correct.";

    private final EmployeeService service;
    private final JmsTemplate jmsTemplate;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public EmployeeController(
            final EmployeeService service,
            final JmsTemplate jmsTemplate,
            final PasswordEncoder passwordEncoder
    ) {
        this.service = service;
        this.jmsTemplate = jmsTemplate;
        this.passwordEncoder = passwordEncoder;
    }


    @ApiOperation(value = "Get a list of employees", response = List.class)
    @GetMapping(value = "/employees", produces = "application/json")
    public List<EmployeeDto> findAllEmployee() {
        return service.findAllEmployee();
    }

    @ApiOperation(value = "Get a employee by an id", response = Employee.class)
    @GetMapping(value = "/employees/{id}", produces = "application/json")
    public EmployeeDto findEmployeeById(@PathVariable("id") final long id) throws NoEmployeeException {
        return service.findEmployeeById(id);
    }

    @ApiOperation(value = "Save an employee", response = void.class)
    @PostMapping(value = "/employees", consumes = "application/json")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void saveEmployee(@Valid @RequestBody final Employee employee) throws EmployeeException {
        if (employee.getEmployeeId() != null) throw new EmployeeException(EMPLOYEE_NOT_FOUND_ERROR_MESSAGE);

        final String password = employee.getPassword();
        employee.setPassword(passwordEncoder.encode(password));

        jmsTemplate.convertAndSend(DESTINATION_SAVE_OR_UPDATE, employee);

    }

    @ApiOperation(value = "Update an employee", response = void.class)
    @PutMapping(value = "/employees/{id}", consumes = "application/json")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateEmployee(@PathVariable("id") final long id,
                               @RequestBody @Valid final Employee employee) throws EmployeeException {
        if (employee.getEmployeeId() == null || !employee.getEmployeeId().equals(id))
            throw new EmployeeException(EMPLOYEE_NOT_FOUND_ERROR_MESSAGE);

        jmsTemplate.convertAndSend(DESTINATION_SAVE_OR_UPDATE, employee);
    }

    @ApiOperation(value = "Delete an employee", response = void.class)
    @DeleteMapping(value = "/employees/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable("id") final long id) {
        jmsTemplate.convertAndSend(DESTINATION_DELETE_EMPLOYEE, id);
    }

}
