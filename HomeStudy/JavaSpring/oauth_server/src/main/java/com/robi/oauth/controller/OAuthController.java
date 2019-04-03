package com.robi.oauth.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OAuthController {

    private static final Logger logger = LoggerFactory.getLogger(OAuthController.class);

    @GetMapping("/test")
    public String test1() {
        logger.info("test1()");
        return "Hello REST!";
    }

    @GetMapping("/test/{echo}")
    public String test2(@PathVariable("echo") String echo) {
        logger.info("test2()");
        return "Hello REST! (echo:" + echo + ")";
    }
}