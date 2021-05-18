package com.de4bi.study.jpa.jpql.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@Entity
@NamedQuery(
    name = "MemberWithNamedQuery.findByName",
    query = "select m from MemberWithNamedQuery m where m.name = :name"
)
public class MemberWithNamedQuery {
    
    @Id @GeneratedValue
    private Long id;

    private MemberType type;
    
    private String name;

    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    private Team team;
}
