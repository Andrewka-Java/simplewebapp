package com.mastery.java.task.rest;

import com.mastery.java.task.dto.EmployeeDto;
import com.mastery.java.task.dto.mapper.EmployeeMapper;
import com.mastery.java.task.exception.EmployeeException;
import com.mastery.java.task.exception.NoEmployeeException;
import com.mastery.java.task.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static com.mastery.java.task.util.Util.getContent;
import static com.mastery.java.task.util.Util.mapToJson;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
public class EmployeeControllerTest extends AbstractTest {

    @BeforeEach
    @Override
    public void before() {
      log.debug("The test method before is starting");
      super.before();
      log.debug("The test method before was executed");
    }

    @AfterEach
    @Override
    public void after() {
        log.debug("The test method after is starting");
        super.after();
        log.debug("The test method after was executed");
    }


    @Test
    public void findAllEmployees() throws Exception {
        EmployeeDto employeeDto = EmployeeMapper.toEmployeeDto(createFixture().get());

        Mockito.when(service.findAllEmployee())
                .thenReturn(Collections.singletonList(employeeDto));

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get("/v1/employees")
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertEquals(mapToJson(employeeDto), getContent(result), () -> "The bodies isn't equivalent");

        Mockito.verify(service, Mockito.times(1)).findAllEmployee();
    }

    @Test
    public void findEmployeeById() throws Exception {
        EmployeeDto employeeDto = EmployeeMapper.toEmployeeDto(createFixture().get());

        Mockito.when(service.findEmployeeById(1L)).thenReturn(employeeDto);

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get("/v1/employees/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertEquals(mapToJson(employeeDto), getContent(result), () -> "The bodies isn't equivalent");

        Mockito.verify(service, Mockito.times(1)).findEmployeeById(1L);
    }

    @Test
    public void addEmployee() throws Exception {
        Employee employee = createFixtureAdd().get();

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapToJson(employee))
        )
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    public void updateEmployee() throws Exception {
        Employee employee = createFixture().get();

        mockMvc.perform(
                MockMvcRequestBuilders.put("/v1/employees/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapToJson(employee))
        )
                .andExpect(status().isNoContent());

        Mockito.verify(jmsTemplate, Mockito.times(1))
                .convertAndSend("saveOrUpdate", employee);
    }

    @Test
    public void deleteEmployee() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/v1/employees/{id}", 1)
        )
                .andExpect(status().isNoContent());

        Mockito.verify(jmsTemplate, Mockito.times(1))
                .convertAndSend("deleteEmployee", 1L);
    }



    @Test
    public void findEmployeeByIdBad() throws Exception {
        Mockito.when(service.findEmployeeById(100L)).thenThrow(new NoEmployeeException("No one employee was found."));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/v1/employees/{id}", 100)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NoEmployeeException))
                .andExpect(result ->
                        assertEquals("No one employee was found.",
                                result.getResolvedException().getMessage()));

        Mockito.verify(service, Mockito.times(1)).findEmployeeById(100L);
    }


    @Test
    public void addEmployeeBad() throws Exception {
        Employee employee = createFixture().get();

        mockMvc.perform(
                MockMvcRequestBuilders.post("/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapToJson(employee))
        )
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EmployeeException))
                .andExpect(result ->
                        assertEquals("Such an employee isn't correct.",
                                result.getResolvedException().getMessage()));;

        Mockito.verify(jmsTemplate, Mockito.times(0))
                .convertAndSend("saveOrUpdate", employee);
    }

    @Test
    public void updateEmployeeBad() throws Exception {
        Employee employee = createFixtureAdd().get();

        mockMvc.perform(
                MockMvcRequestBuilders.put("/v1/employees/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapToJson(employee))
        )
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EmployeeException))
                .andExpect(result ->
                        assertEquals("Such an employee isn't correct.",
                                result.getResolvedException().getMessage()));;

        Mockito.verify(jmsTemplate, Mockito.times(0))
                .convertAndSend("saveOrUpdate", employee);
    }

}
