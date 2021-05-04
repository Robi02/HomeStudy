package com.de4bi.study.jpaproxy.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@Entity
public class Parent {
    
    @Id @GeneratedValue
    @Column(name = "PARENT_ID")
    private Long id;

    private String name;

    // @OneToMany(mappedBy = "parent")
    // @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Child> childList = new ArrayList<>();

    // 연관관계 편의 메서드
    public void addChild(Child child) {
        this.childList.add(child);
        child.setParent(this);
    }
}
