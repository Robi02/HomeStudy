package com.de4bi.study.jpaproxy.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@Entity
public class Team {
    
    @Id @GeneratedValue
    @Column(name = "TEAM_NAME")
    private Long id;

    private String name;
}
