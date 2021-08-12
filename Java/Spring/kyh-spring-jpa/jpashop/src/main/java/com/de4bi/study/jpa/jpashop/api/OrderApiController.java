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
import com.de4bi.study.jpa.jpashop.repository.order.query.OrderFlatDto;
import com.de4bi.study.jpa.jpashop.repository.order.query.OrderItemQueryDto;
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

    @GetMapping("/api/v6/orders")
    public List<OrderQueryDto> ordersV6() {
        List<OrderFlatDto> flats = orderQueryRepository.findOrderQueryDtos3Flat();

        return flats.stream()
            .collect(Collectors.groupingBy(o -> new OrderQueryDto(o.getOrderId(), o.getName(), o.getOrderDate(), o.getOrderStatus(), o.getAddress()),
                    Collectors.mapping(o -> new OrderItemQueryDto(o.getOrderId(), o.getItemName(), o.getOrderPrice(), o.getCount()), Collectors.toList())
            )).entrySet().stream().map(e -> new OrderQueryDto(e.getKey().getOrderId(), e.getKey().getName(), e.getKey().getOrderDate(), e.getKey().getOrderStatus(), e.getKey().getAddress(), e.getValue()))
            .collect(Collectors.toList());
    }

    /**
     * [정리]
     *  - V1 : 엔티티를 조회해서 그대로 반환.
     *  - V2 : 엔티티 조회 후 DTO로 변환.
     *  - V3 : 페치 조인으로 쿼리 수 최적화.
     *  - V3.1 : 컬렉션 페이징과 한계 돌파.
     *   -> @xxxToOne관계는 페치 조인으로 쿼리 수 최적화.
     *   -> 컬렉션(@OneToMany)은 페치 조인 시 페이징 불가능.
     *   -> 컬렉션은 페치 조인 대신 지연로딩 유지하고, @BatchSize로 최적화.
     *  - V4 : JPA에서 DTO를 직접 조회.
     *  - V5 : 컬렉션 조회 최적화 - 일대다 관계의 컬렉션은 IN 절을 활용하여 메모리에 선조회로 최적화.
     *  - V6 : 플렛 데이터 최적화 - JOIN 결과를 그대로 조회 후 애플리케이션에서 원하는 모양으로 직접 변환.
     * 
     * [권장]
     *  1. DTO대신 엔티티 조회 방식으로 우선 접근.
     *   -> 페치 조인으로 쿼리수 최적화.
     *   -> 컬렉션 최적화. (페이징 필요 시 @BatchSize로 최적화, 아니면 페치조인으로 최적화.)
     *  2. 엔티티 조회 방식으로 해결이 안되면 DTO 조회 방식 사용.
     *  3. DTO 조회 방식으로 해결이 안되면 NativeSQL이나 스플이 JDBC Template 활용.
     */

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
