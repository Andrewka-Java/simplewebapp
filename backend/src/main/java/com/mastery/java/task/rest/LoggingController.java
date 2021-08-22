package com.mastery.java.task.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@Api(value = "Logging controller API", description = "Logging controller API")
@RestController
@RequestMapping("/log")
public class LoggingController {

    private static final String RESPONSE = "See the log for details";
    private static final String LOG_LEVEL_MESSAGE_PATTERN = "This is {} level message.";


    @ApiOperation(value = "Get a logger level", response = List.class)
    @GetMapping()
    public String log() {
        log.trace(LOG_LEVEL_MESSAGE_PATTERN, "TRACE");
        log.debug(LOG_LEVEL_MESSAGE_PATTERN, "DEBUG");
        log.info(LOG_LEVEL_MESSAGE_PATTERN, "INFO");
        log.warn(LOG_LEVEL_MESSAGE_PATTERN, "WARN");
        log.error(LOG_LEVEL_MESSAGE_PATTERN, "ERROR");
        return RESPONSE;
    }
}
