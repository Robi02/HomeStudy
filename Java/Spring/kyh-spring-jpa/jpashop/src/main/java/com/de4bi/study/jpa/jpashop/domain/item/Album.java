package com.de4bi.study.jpa.jpashop.domain.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@DiscriminatorValue("A")
public class Album extends Item {
    
    private String artist;
    
    private String etc;
}
