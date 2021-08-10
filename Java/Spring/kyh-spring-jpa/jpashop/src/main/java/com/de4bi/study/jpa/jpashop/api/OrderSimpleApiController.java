package com.de4bi.study.jpa.jpashop.api;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.de4bi.study.jpa.jpashop.domain.Address;
import com.de4bi.study.jpa.jpashop.domain.Order;
import com.de4bi.study.jpa.jpashop.domain.OrderStatus;
import com.de4bi.study.jpa.jpashop.repository.OrderRepository;
import com.de4bi.study.jpa.jpashop.repository.OrderSearch;
import com.de4bi.study.jpa.jpashop.repository.order.simplequery.OrderSimpleQueryDto;
import com.de4bi.study.jpa.jpashop.repository.order.simplequery.OrderSimpleQueryRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;
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
    private final OrderSimpleQueryRepository orderSimpleQueryRepository; // 특수쿼리들을 담은 레포지토리를 별도의 패키지로 분리하여 관리에 용이하게 함

    /**
     * [!] 쿼리 방식 선택 권장 순서
     * 
     * 1. Entity를 DTO로 변환하는 방법 선택. (V2)
     * 2. 필요 시 fetch join으로 성능 최적화 -> 대부분 성능이슈 해결 (V3)
     * 3. 그래도 안되면 DTO로 직접 조회 선택 (V4)
     * 4. 최후의 방법은 JPA가 제공하는 NativeSQL이나 스프링 JDBCTemplate를 사용해서 SQL을 직접 사용.
     */

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        return all;
    }
    
    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2() {
        return orderRepository.findAllByString(new OrderSearch())
            .stream()
            .map(SimpleOrderDto::new)
            .collect(Collectors.toList());
    }

    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> ordersV3() {
        return orderRepository.findAllWithMemberDelivery() // 'fetch join'으로 N+1 문제 해결
            .stream()
            .map(SimpleOrderDto::new)
            .collect(Collectors.toList());
    }

    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4() {
        return orderSimpleQueryRepository.findOrderDtos();
    }

    @Data
    static class SimpleOrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName(); // V2::Lazy Loading (N+1 문제 발생)
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress(); // V2::Lazy Loading (N+1 문제 발생)
        }
    }
}
