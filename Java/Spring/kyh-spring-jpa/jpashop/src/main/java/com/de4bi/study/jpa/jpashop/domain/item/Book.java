package com.de4bi.study.jpa.jpashop.domain.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@DiscriminatorValue("B")
public class Book extends Item {
    
    private String author;

    private String isbn;
}
