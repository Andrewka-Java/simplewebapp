package com.mastery.java.task.rest;

import com.mastery.java.task.exceptions.EmployeeException;
import com.mastery.java.task.model.Employee;
import com.mastery.java.task.service.EmployeeService;
import com.mastery.java.task.exceptions.NoEmployeeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class EmployeeController {

    private final EmployeeService service;

    @Autowired
    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping("/employees")
    public List<Employee> findAllEmployee() {
        return service.findAll();
    }

    @GetMapping("/employees/{id}")
    public Employee findEmployeeById(@PathVariable("id") Long id) throws NoEmployeeException {
        return service.findById(id);
    }

    @PostMapping("/employees")
    public void saveEmployee(@RequestBody Employee employee) throws EmployeeException {
        if (employee.getEmployeeId() != null) throw new EmployeeException("Such an employee already exists.");
        service.saveOrUpdate(employee);
    }

    @PutMapping("/employees/{id}")
    public void updateEmployee(@RequestBody Employee employee) throws EmployeeException {
        if (employee.getEmployeeId() == null) throw new EmployeeException("No such employee found.");
        service.saveOrUpdate(employee);
    }

    @DeleteMapping("/employees/{id}")
    public void deleteEmployee(@PathVariable("id") Long id) {
        service.delete(id);
    }

}
