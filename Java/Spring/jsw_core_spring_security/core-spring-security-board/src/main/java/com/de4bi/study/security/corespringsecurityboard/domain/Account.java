package com.de4bi.study.security.corespringsecurityboard.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.de4bi.study.security.corespringsecurityboard.security.UserRoles;

import lombok.Data;

@Entity
@Data
public class Account {
    
    @Id @GeneratedValue
    private Long id;

    private String username;
    
    private String password;
    
    private String email;
    
    private int age;
    
    @Enumerated(EnumType.STRING)
    private UserRoles role = UserRoles.USER;
}
