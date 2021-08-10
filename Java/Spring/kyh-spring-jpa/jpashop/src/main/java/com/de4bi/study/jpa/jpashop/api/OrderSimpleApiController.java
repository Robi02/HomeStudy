package com.de4bi.study.jpa.jpashop.api;

import java.util.List;

import com.de4bi.study.jpa.jpashop.domain.Order;
import com.de4bi.study.jpa.jpashop.repository.OrderRepository;
import com.de4bi.study.jpa.jpashop.repository.OrderSearch;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

/**
 * @xToOne(ManyToOne, OneToOne)
 * Order
 * Order -> Member
 * Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {
    
    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        return all;
    }
}
