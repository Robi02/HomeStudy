package com.de4bi.study.jpa.jpatypes.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@Embeddable
public class Address {
    
    private String city;
    private String street;

    @Column(name = "ZIPCODE") // 컬럼도 사용 가능하다.
    private String zipcode;

    // private Member member; // Enbeded에 Entity도 들어올 수 있다.
}
