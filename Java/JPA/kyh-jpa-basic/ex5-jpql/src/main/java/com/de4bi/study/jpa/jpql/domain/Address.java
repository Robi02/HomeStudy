package com.de4bi.study.jpa.jpql.domain;

import javax.persistence.Embeddable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @EqualsAndHashCode
@Embeddable
public class Address {
    
    private String city;
    private String street;
    private String zipcode;
}
