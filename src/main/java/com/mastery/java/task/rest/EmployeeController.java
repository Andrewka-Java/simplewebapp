package com.mastery.java.task.rest;

import com.mastery.java.task.dto.EmployeeDto;
import com.mastery.java.task.exception.EmployeeException;
import com.mastery.java.task.exception.NoEmployeeException;
import com.mastery.java.task.model.Employee;
import com.mastery.java.task.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.mastery.java.task.dto.mapper.EmployeeMapper.toEmployeeDto;
import static com.mastery.java.task.dto.mapper.EmployeeMapper.toListEmployeeDto;

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
        return toListEmployeeDto(service.findAll());
    }

    @GetMapping("/employees/{id}")
    public EmployeeDto findEmployeeById(@PathVariable("id") Long id) throws NoEmployeeException {
        return toEmployeeDto(service.findById(id));
    }

    @PostMapping("/employees")
    public void saveEmployee(@Valid @RequestBody Employee employee) throws EmployeeException {
        if (employee.getEmployeeId() != null) throw new EmployeeException("Such an employee isn't correct.");
        service.saveOrUpdate(employee);

    }

    @PutMapping("/employees/{id}")
    public void updateEmployee(@RequestBody @Valid Employee employee) throws EmployeeException {
        if (employee.getEmployeeId() == null) throw new EmployeeException("No such employee found.");
        service.saveOrUpdate(employee);
    }

    @DeleteMapping("/employees/{id}")
    public void deleteEmployee(@PathVariable("id") Long id) {
        service.delete(id);
    }

}
