package com.de4bi.study.jpa.jpashop.service;

import javax.persistence.EntityManager;

import com.de4bi.study.jpa.jpashop.domain.Address;
import com.de4bi.study.jpa.jpashop.domain.Member;
import com.de4bi.study.jpa.jpashop.domain.Order;
import com.de4bi.study.jpa.jpashop.domain.OrderStatus;
import com.de4bi.study.jpa.jpashop.domain.item.Book;
import com.de4bi.study.jpa.jpashop.exception.NotEnoughtStockException;
import com.de4bi.study.jpa.jpashop.repository.OrderRepository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        // given
        Member member = createMember("회원1");
        Book book = createBook("JPA책", 10000, 10);

        // when
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // then
        Order getOrder = orderRepository.findOne(orderId);

        Assert.assertEquals("상품 주문시 상태는 ORDER이다.", OrderStatus.ORDER, getOrder.getStatus());
        Assert.assertEquals("주문한 상품 종류 수가 정확해야 한다.", 1, getOrder.getOrderItems().size());
        Assert.assertEquals("주문 가격은 가격 * 수량이다.", 10000 * orderCount, getOrder.getTotalPrice());
        Assert.assertEquals("주문 수량만큼 재고가 줄어야 한다.", 8, book.getStockQuantity());
    }

    @Test(expected = NotEnoughtStockException.class)
    public void 상품주문_재고수량초과() throws Exception {
        // given
        int stockQuantity = 10;
        Member member = createMember("회원1");
        Book book = createBook("JPA책", 10000, stockQuantity);
 
        // when
        int orderCount = stockQuantity + 1;
        orderService.order(member.getId(), book.getId(), orderCount);

        // then
        Assert.fail("재고수량 부족 오류가 발생해야 한다.");
    }

    @Test
    public void 주문취소() throws Exception {
        // given
        int stockQuantity = 10;
        Member member = createMember("회원1");
        Book book = createBook("JPA책", 10000, stockQuantity);
        int orderCount = stockQuantity;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        
        // when
        orderService.cancelOrder(orderId);

        // then
        Order getOrder = orderRepository.findOne(orderId);
        Assert.assertEquals("주문 취소시 상태는 CANCEL 이다.", OrderStatus.CANCEL, getOrder.getStatus());
        Assert.assertEquals("주문이 취소된 상품은 그만큼 재고가 증가해야 한다.", stockQuantity, book.getStockQuantity());
    }

    private Member createMember(String name) {
        Member member = new Member();
        member.setName(name);
        member.setAddress(new Address("서울", "경기", "123-123"));
        em.persist(member);
        return member;
    }

    private Book createBook(String name, int price, int stockQuantity){
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }
}
