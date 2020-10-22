package com.mastery.java.task.rest;

import com.mastery.java.task.dto.EmployeeDto;
import com.mastery.java.task.exception.EmployeeException;
import com.mastery.java.task.exception.NoEmployeeException;
import com.mastery.java.task.model.Employee;
import com.mastery.java.task.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1")
public class EmployeeController {

    private final EmployeeService service;

    @Autowired
    public EmployeeController(EmployeeService service) {
        this.service = service;
    }


    @GetMapping("/employees")
    public List<EmployeeDto> findAllEmployee() {
        log.debug("The method findAllEmployee is starting");
        List<EmployeeDto> employeeDtos = service.findAllEmployee();

        log.debug("The method findAllEmployee was executed with response ({})", employeeDtos);
        return employeeDtos;
    }

    @GetMapping("/employees/{id}")
    public EmployeeDto findEmployeeById(@PathVariable("id") Long id) throws NoEmployeeException {
        log.debug("The method findEmployeeById is starting with param ({})", id);
        EmployeeDto employeeDto = service.findEmployeeById(id);

        log.debug("The method findEmployeeById was executed with response ({})", employeeDto);
        return employeeDto;
    }

    @PostMapping("/employees")
    public void saveEmployee(@Valid @RequestBody Employee employee) throws EmployeeException {
        log.debug("The method saveEmployee is starting with param ({})", employee);

        if (employee.getEmployeeId() != null) throw new EmployeeException("Such an employee isn't correct.");
        Employee savedEmployee = service.saveOrUpdate(employee);

        log.debug("The method saveEmployee was executed with response ({})", savedEmployee);
    }

    @PutMapping("/employees/{id}")
    public void updateEmployee(@RequestBody @Valid Employee employee) throws EmployeeException {
        log.debug("The method updateEmployee is starting with param ({})", employee);

        if (employee.getEmployeeId() == null) throw new EmployeeException("No such employee found.");
        Employee updatedEmployee = service.saveOrUpdate(employee);

        log.debug("The method updateEmployee was executed with response ({})", updatedEmployee);
    }

    @DeleteMapping("/employees/{id}")
    public void deleteEmployee(@PathVariable("id") Long id) {
        log.debug("The method deleteEmployee is starting with param ({})", id);
        service.deleteEmployee(id);
        log.debug("The method deleteEmployee was executed");
    }

}
