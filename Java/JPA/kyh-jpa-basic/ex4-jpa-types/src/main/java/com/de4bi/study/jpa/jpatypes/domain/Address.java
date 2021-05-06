package com.de4bi.study.jpa.jpatypes.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter(value = AccessLevel.PRIVATE) @NoArgsConstructor @AllArgsConstructor
@Embeddable
public class Address {
    
    private String city;
    private String street;

    @Column(name = "ZIPCODE") // 컬럼도 사용 가능하다.
    private String zipcode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(city, address.getCity()) &&
                Objects.equals(street, address.getStreet()) &&
                Objects.equals(zipcode, address.getZipcode());

    }

    @Override
    public int hashCode() {
        return Objects.hash(city, street, zipcode);
    }
}
