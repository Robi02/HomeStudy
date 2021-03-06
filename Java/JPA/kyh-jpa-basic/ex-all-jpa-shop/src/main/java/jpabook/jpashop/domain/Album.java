package jpabook.jpashop.domain;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@Entity
public class Album extends Item {
    
    private String artist;
    private String etc;
}
