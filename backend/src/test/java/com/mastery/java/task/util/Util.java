package com.mastery.java.task.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;

public class Util {

    public static String mapToJson(final Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }

    public static String getContent(final MvcResult result) throws UnsupportedEncodingException {
        return result.getResponse().getContentAsString().replaceAll("[\\[\\]]", "");
    }

    public static String getContent(final String content){
        return content.replaceAll("[\\[\\]]", "");
    }

}
