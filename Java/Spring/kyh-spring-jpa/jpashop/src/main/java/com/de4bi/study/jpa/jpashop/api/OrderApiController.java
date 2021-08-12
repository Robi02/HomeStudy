package com.de4bi.study.jpa.jpashop.api;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.de4bi.study.jpa.jpashop.domain.Address;
import com.de4bi.study.jpa.jpashop.domain.Order;
import com.de4bi.study.jpa.jpashop.domain.OrderItem;
import com.de4bi.study.jpa.jpashop.domain.OrderStatus;
import com.de4bi.study.jpa.jpashop.repository.OrderRepository;
import com.de4bi.study.jpa.jpashop.repository.OrderSearch;
import com.de4bi.study.jpa.jpashop.repository.order.query.OrderQueryDto;
import com.de4bi.study.jpa.jpashop.repository.order.query.OrderQueryRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OrderApiController {
    
    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;

    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        for (Order order : all) {
            // Lazy필드 강제 초기화
            order.getMember().getName();
            order.getDelivery().getAddress();
            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.stream().forEach(o -> o.getItem().getName());
        }

        return all;    
    }

    @GetMapping("/api/v2/orders")
    public List<OrderDto> ordersV2() {
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        List<OrderDto> collect = orders.stream()
            .map(o -> new OrderDto(o))
            .collect(Collectors.toList());

        return collect;
    }

    @GetMapping("/api/v3/orders")
    public List<OrderDto> ordersV3() {
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        List<OrderDto> collect = orders.stream()
            .map(o -> new OrderDto(o))
            .collect(Collectors.toList());

        return collect;
    }

    /**
     * @apiNote @xxxToOne 관계는 fetch join해도 페이징에 영향을 주지 않는다.
     * 따라서, ToOne관계는 fetch join으로 쿼리 수를 줄이도록 하고, 나머지는
     * hibernate.default_batch_fetch_size로 최적화를 한다.
     */
    @GetMapping("/api/v3.1/orders")
    public List<OrderDto> ordersV3_page(
        @RequestParam(value = "offset", defaultValue = "0") int offset,
        @RequestParam(value = "limit", defaultValue = "100") int limit
    ) {
        List<Order> orders = orderRepository.findAllWithMemberDelivery(offset, limit);
        List<OrderDto> collect = orders.stream()
            .map(o -> new OrderDto(o))
            .collect(Collectors.toList());

        return collect;
    }

    @GetMapping("/api/v4/orders")
    public List<OrderQueryDto> ordersV4() {
        return orderQueryRepository.findOrderQueryDtos();
    }

    @GetMapping("/api/v5/orders")
    public List<OrderQueryDto> ordersV5() {
        return orderQueryRepository.findOrderQueryDtos2();
    }

    @Data
    static class OrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;
        private List<OrderItemDto> orderItems; // OrderItem @Entity가 아닌, OrderItemDto를 출력해야 함.

        public OrderDto(Order o) {
            orderId = o.getId();
            name = o.getMember().getName();
            orderDate = o.getOrderDate();
            orderStatus = o.getStatus();
            address = o.getDelivery().getAddress();
            o.getOrderItems().stream().forEach(o2 -> o2.getItem().getName());
            orderItems = o.getOrderItems().stream()
                            .map(orderItem -> new OrderItemDto(orderItem))
                            .collect(Collectors.toList());
        }
    }

    @Data
    static class OrderItemDto {
        private String itemName; // 상품명
        private int orderPrice; // 주문 가격
        private int count; // 주문 수량

        public OrderItemDto(OrderItem oi) {
            itemName = oi.getItem().getName();
            orderPrice = oi.getOrderPrice();
            count = oi.getCount();
        }
    }
}
