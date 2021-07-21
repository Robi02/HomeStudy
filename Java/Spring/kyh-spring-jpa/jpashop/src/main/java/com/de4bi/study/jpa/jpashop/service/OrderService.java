package com.de4bi.study.jpa.jpashop.service;

import java.util.List;

import com.de4bi.study.jpa.jpashop.domain.Delivery;
import com.de4bi.study.jpa.jpashop.domain.Member;
import com.de4bi.study.jpa.jpashop.domain.Order;
import com.de4bi.study.jpa.jpashop.domain.OrderItem;
import com.de4bi.study.jpa.jpashop.domain.item.Item;
import com.de4bi.study.jpa.jpashop.repository.ItemRepository;
import com.de4bi.study.jpa.jpashop.repository.MemberRepository;
import com.de4bi.study.jpa.jpashop.repository.OrderRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문.
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        // 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // 배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        // 주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        // 주문 저장
        orderRepository.save(order);

        return order.getId();
    }

    /**
     * 주문 취소.
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findOne(orderId);
        order.cancel();
    }

    /**
     * 주문 검색.
     */
    // public List<Order> findOrders(OrderSearch orderSearch) {
    //     return order
    // }
}
