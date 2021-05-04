package jpabook.jpashop.domain;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@Entity
public class Movie extends Item {
    
    private String director;
    private String actor;
}
