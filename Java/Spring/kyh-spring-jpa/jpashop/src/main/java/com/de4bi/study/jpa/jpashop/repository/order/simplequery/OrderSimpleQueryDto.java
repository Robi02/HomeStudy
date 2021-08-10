package com.de4bi.study.jpa.jpashop.repository.order.simplequery;

import java.time.LocalDateTime;

import com.de4bi.study.jpa.jpashop.domain.Address;
import com.de4bi.study.jpa.jpashop.domain.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class OrderSimpleQueryDto {

    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
}
