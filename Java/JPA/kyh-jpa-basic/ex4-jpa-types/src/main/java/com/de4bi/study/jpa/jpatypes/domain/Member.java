package com.de4bi.study.jpa.jpatypes.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@Entity
public class Member {
    
    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    private String name;

    @Embedded
    private Period workPeriod;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name ="city", column = @Column(name = "WORK_CITY")),
        @AttributeOverride(name ="street", column = @Column(name = "WORK_STREET")),
        @AttributeOverride(name ="zipcode", column = @Column(name = "WORK_ZIPCODE"))
    })
    private Address workAddress;

    @Embedded
    private Address homeAddress;
}
