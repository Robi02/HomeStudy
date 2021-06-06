package com.de4bi.study.security.corespringsecurityboard.domain;

import com.de4bi.study.security.corespringsecurityboard.security.UserRoles;

import lombok.Data;

@Data
public class AccountDto {
    
    private String username;
    private String password;
    private String email;
    private int age;
    private UserRoles role;
}
