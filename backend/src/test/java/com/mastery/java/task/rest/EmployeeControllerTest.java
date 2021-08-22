package com.mastery.java.task.rest;

import com.mastery.java.task.dto.EmployeeDto;
import com.mastery.java.task.dto.mapper.EmployeeMapper;
import com.mastery.java.task.exception.EmployeeException;
import com.mastery.java.task.exception.NoEmployeeException;
import com.mastery.java.task.model.Employee;
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

public class EmployeeControllerTest extends AbstractTest {

    @BeforeEach
    @Override
    public void before() {
        super.before();
    }

    @AfterEach
    @Override
    public void after() {
        super.after();
    }


    @Test
    public void findAllEmployees() throws Exception {
        //Given
        final EmployeeDto employeeDto = EmployeeMapper.toEmployeeDto(createFixture());
        Mockito.when(service.findAllEmployee())
                .thenReturn(Collections.singletonList(employeeDto));

        //When
        final MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get("/v1/employees")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        //Then
        assertEquals(mapToJson(employeeDto), getContent(result), () -> "The bodies isn't equivalent");
        Mockito.verify(service, Mockito.times(1)).findAllEmployee();
    }

    @Test
    public void findEmployeeById() throws Exception {
        //Given
        final EmployeeDto employeeDto = EmployeeMapper.toEmployeeDto(createFixture());
        Mockito.when(service.findEmployeeById(1L)).thenReturn(employeeDto);

        //When
        final MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get("/v1/employees/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        //Then
        assertEquals(mapToJson(employeeDto), getContent(result), () -> "The bodies isn't equivalent");
        Mockito.verify(service, Mockito.times(1)).findEmployeeById(1L);
    }

    @Test
    public void addEmployee() throws Exception {
        //Given
        final Employee employee = createFixtureAdd();

        //When
        final MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapToJson(employee))
        )
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    public void updateEmployee() throws Exception {
        //Given
        final Employee employee = createFixture();

        //When
        mockMvc.perform(
                MockMvcRequestBuilders.put("/v1/employees/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapToJson(employee))
        )
                .andExpect(status().isNoContent());

        //Then
        Mockito.verify(jmsTemplate, Mockito.times(1))
                .convertAndSend("saveOrUpdate", employee);
    }

    @Test
    public void deleteEmployee() throws Exception {
        //When
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/v1/employees/{id}", 1)
        )
                .andExpect(status().isNoContent());

        //Then
        Mockito.verify(jmsTemplate, Mockito.times(1))
                .convertAndSend("deleteEmployee", 1L);
    }


    @Test
    public void findEmployeeByIdBad() throws Exception {
        //Given
        Mockito.when(service.findEmployeeById(100L)).thenThrow(new NoEmployeeException("No one employee was found."));

        //When
        mockMvc.perform(
                MockMvcRequestBuilders.get("/v1/employees/{id}", 100)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NoEmployeeException))
                .andExpect(result ->
                        assertEquals("No one employee was found.",
                                result.getResolvedException().getMessage()));

        //Then
        Mockito.verify(service, Mockito.times(1)).findEmployeeById(100L);
    }


    @Test
    public void addEmployeeBad() throws Exception {
        //Given
        final Employee employee = createFixture();

        //When
        mockMvc.perform(
                MockMvcRequestBuilders.post("/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapToJson(employee))
        )
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EmployeeException))
                .andExpect(result ->
                        assertEquals("Such an employee isn't correct.",
                                result.getResolvedException().getMessage()));

        //Then
        Mockito.verify(jmsTemplate, Mockito.times(0))
                .convertAndSend("saveOrUpdate", employee);
    }

    @Test
    public void updateEmployeeBad() throws Exception {
        //Given
        final Employee employee = createFixtureAdd();

        //When
        mockMvc.perform(
                MockMvcRequestBuilders.put("/v1/employees/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapToJson(employee))
        )
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EmployeeException))
                .andExpect(result ->
                        assertEquals("Such an employee isn't correct.",
                                result.getResolvedException().getMessage()));
        ;

        //Then
        Mockito.verify(jmsTemplate, Mockito.times(0))
                .convertAndSend("saveOrUpdate", employee);
    }

}
