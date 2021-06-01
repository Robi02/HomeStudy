package com.de4bi.study.security.corespringsecurity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {
    
    @GetMapping("/")
    public String index() {
        return "home";
    }
}
