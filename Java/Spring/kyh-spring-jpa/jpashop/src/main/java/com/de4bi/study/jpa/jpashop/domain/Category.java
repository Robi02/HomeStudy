package com.de4bi.study.jpa.jpashop.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.de4bi.study.jpa.jpashop.domain.item.Item;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class Category {
    
    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "category_item", // N:M관계로 생성 된 중간 테이블과 매핑
        joinColumns = @JoinColumn(name = "category_id"), // (실전에서는 그냥 1:N으로 쪼개는것을 추천. 그래야 값의 추가등 관리가 편해진다.)
        inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    // 연관관계 메서드
    public void addChildCategory(Category child) {
        this.child.add(child);
        child.setParent(this);
    }
}
