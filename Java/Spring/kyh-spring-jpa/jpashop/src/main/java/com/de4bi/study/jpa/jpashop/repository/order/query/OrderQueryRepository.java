package com.de4bi.study.jpa.jpashop.repository.order.query;

import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {
    
    private final EntityManager em;
    
    /**
     * @apiNote 1+N 쿼리로 처리.
     */
    public List<OrderQueryDto> findOrderQueryDtos() {
        List<OrderQueryDto> result = findOrders(); // 1쿼리
        
        result.forEach(o -> {
            o.setOrderItems(findOrderItems(o.getOrderId())); // N쿼리
        });
        
        return result;
    }
    
    /**
     * @apiNote 2쿼리로 처리.
     */
    public List<OrderQueryDto> findOrderQueryDtos2() {
        List<OrderQueryDto> result = findOrders(); // 1쿼리

        List<Long> orderIds = toOrderIds(result);
        
        Map<Long, List<OrderItemQueryDto>> orderItemMap = findOrderItemMap(orderIds); // 1쿼리

        result.forEach(o -> o.setOrderItems(orderItemMap.get(o.getOrderId())));

        return result;
    }

    private Map<Long, List<OrderItemQueryDto>> findOrderItemMap(List<Long> orderIds) {
        List<OrderItemQueryDto> orderItems = em.createQuery(
            "select new com.de4bi.study.jpa.jpashop.repository.order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)" +
            " from OrderItem oi" + 
            " join oi.item i" + 
            " where oi.order.id in :orderIds", OrderItemQueryDto.class
        ).setParameter("orderIds", orderIds).getResultList();

        Map<Long, List<OrderItemQueryDto>> orderItemMap = orderItems.stream()
            .collect(Collectors.groupingBy(orderItemQueryDto -> orderItemQueryDto.getOrderId())); 
        return orderItemMap;
    }

    private List<Long> toOrderIds(List<OrderQueryDto> result) {
        List<Long> orderIds = result.stream()
            .map(o -> o.getOrderId())
            .collect(Collectors.toList());
        return orderIds;
    }

    private List<OrderQueryDto> findOrders() {
        return em.createQuery(
            "select new com.de4bi.study.jpa.jpashop.repository.order.query.OrderQueryDto(o.id, m.name, o.orderDate, o.status, d.address)" +
            " from Order o" +
            " join o.member m" +
            " join o.delivery d", OrderQueryDto.class
        ).getResultList();
    }

    private List<OrderItemQueryDto> findOrderItems(Long orderId) {
        return em.createQuery(
            "select new com.de4bi.study.jpa.jpashop.repository.order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)" +
            " from OrderItem oi" + 
            " join oi.item i" + 
            " where oi.order.id = :orderId", OrderItemQueryDto.class
        ).setParameter("orderId", orderId).getResultList();
    }
}
