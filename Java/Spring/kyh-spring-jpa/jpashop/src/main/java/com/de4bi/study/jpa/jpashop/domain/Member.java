package com.de4bi.study.jpa.jpashop.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
// @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    
    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member") //  Order.java :: private Member member; 와 매핑
    private List<Order> orders = new ArrayList<>();
}
