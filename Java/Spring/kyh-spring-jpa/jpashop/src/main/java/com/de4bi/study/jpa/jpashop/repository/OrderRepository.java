package com.de4bi.study.jpa.jpashop.repository;

import java.util.List;

import javax.persistence.EntityManager;

import com.de4bi.study.jpa.jpashop.domain.Order;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
    
    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    // public List<Order> findAll(OrderSearch orderSearch) {}
}
