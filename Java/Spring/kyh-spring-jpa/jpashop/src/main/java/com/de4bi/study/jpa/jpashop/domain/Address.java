package com.de4bi.study.jpa.jpashop.domain;

import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter // @Setter -> 값 타입에서는 Getter를 제공하지 않고 불변객체로 만드는 것이 유리하다.
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA 스팩에서 @Embeddable가 달린 클래스는 public/protected기본 생성자가 필요하다.
@AllArgsConstructor
@Embeddable
public class Address {

    private String city;
    private String street;
    private String zipcode;
}
