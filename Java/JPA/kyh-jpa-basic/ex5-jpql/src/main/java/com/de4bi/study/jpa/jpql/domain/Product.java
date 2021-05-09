package com.de4bi.study.jpa.jpql.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@Entity
public class Product {
    
    @Id @GeneratedValue
    private Long id;

    private String name;

    private int price;

    private int stockAmount;
}
