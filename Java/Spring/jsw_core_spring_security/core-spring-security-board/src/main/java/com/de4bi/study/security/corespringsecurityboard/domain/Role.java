package com.de4bi.study.security.corespringsecurityboard.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
public class Role {
    
    @Id @GeneratedValue
    private Long roleId;

    private String roleDesc;

    private String roleName;
}
