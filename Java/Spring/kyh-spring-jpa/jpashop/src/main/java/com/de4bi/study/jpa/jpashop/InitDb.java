package com.de4bi.study.jpa.jpashop;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import com.de4bi.study.jpa.jpashop.domain.Address;
import com.de4bi.study.jpa.jpashop.domain.Delivery;
import com.de4bi.study.jpa.jpashop.domain.Member;
import com.de4bi.study.jpa.jpashop.domain.Order;
import com.de4bi.study.jpa.jpashop.domain.OrderItem;
import com.de4bi.study.jpa.jpashop.domain.item.Book;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InitDb {
    
    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;
        
        public void dbInit1() {
            Member member = createMember("User1", "서울", "1", "1111");
            em.persist(member);

            Book book1 = createBook("JPA Book1", 10000, 100);
            em.persist(book1);

            Book book2 = createBook("JPA Book2", 20000, 100);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Delivery delivery = createDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        public void dbInit2() {
            Member member = createMember("User2", "서울", "2", "2222");
            em.persist(member);

            Book book1 = createBook("Spring Book1", 20000, 100);
            em.persist(book1);

            Book book2 = createBook("Spring Book2", 40000, 100);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 20000, 3);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 4);

            Delivery delivery = createDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        private Member createMember(String name, String city, String street, String zipcode) {
            Member member = new Member();
            member.setName(name);
            member.setAddress(new Address(city, street, zipcode));
            return member;
        }

        private Book createBook(String name, int price, int stockQuantity) {
            Book book1 = new Book();
            book1.setName(name);
            book1.setPrice(price);
            book1.setStockQuantity(stockQuantity);
            return book1;
        }

        private Delivery createDelivery(Member member) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }
    }
}
