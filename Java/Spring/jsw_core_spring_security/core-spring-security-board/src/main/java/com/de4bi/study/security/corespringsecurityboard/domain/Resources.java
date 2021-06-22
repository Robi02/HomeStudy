package com.de4bi.study.security.corespringsecurityboard.domain;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;

import lombok.Data;

@Entity
@Data
public class Resources {
    
    @Id @GeneratedValue
    private Long resourceId;

    private String httpMethod;

    private int orderNum;

    private String resourceName;

    private String resourceType;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "role_resources",
               joinColumns = { @JoinColumn(name = "resource_id") },
               inverseJoinColumns = { @JoinColumn(name = "role_id") })
    private Set<Role> roleSet;
}
