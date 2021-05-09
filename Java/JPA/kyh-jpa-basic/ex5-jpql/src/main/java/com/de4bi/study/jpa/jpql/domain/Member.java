package com.de4bi.study.jpa.jpql.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@Entity
public class Member {
    
    @Id @GeneratedValue
    private Long id;

    private String name;

    private int age;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;
}
