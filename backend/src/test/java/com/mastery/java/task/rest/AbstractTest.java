package com.mastery.java.task.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mastery.java.task.Application;
import com.mastery.java.task.service.EmployeeService;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.UnsupportedEncodingException;


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
    private EmployeeExceptionHandler exceptionHandler;


    protected void before() {
        mockMvc = MockMvcBuilders.standaloneSetup(exceptionHandler, controller)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .build();
    }

    protected void after() {
        Mockito.reset(service);
    }

    protected String mapToJson(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }

    protected String getContent(MvcResult result) throws UnsupportedEncodingException {
        return result.getResponse().getContentAsString().replaceAll("[\\[\\]]", "");
    }


}
