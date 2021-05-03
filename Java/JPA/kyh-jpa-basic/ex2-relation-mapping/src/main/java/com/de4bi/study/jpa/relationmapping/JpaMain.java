package com.de4bi.study.jpa.relationmapping;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JpaMain {
 
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

    public static void testA() {
        final EntityManager em = emf.createEntityManager();
        final EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            /* // 1. 양방향 일반
            Order order = new Order();
            em.persist(order);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            em.persist(orderItem);
            */

            /* // 2. 양방향 편의메서드 사용
            Order order = new Order();
            order.addOrderItem(new OrderItem());
            em.persist(order);
            */

            tx.commit();
        }
        catch (Exception e) {
            log.error("{}", e);
            tx.rollback();
        }
        finally {
            em.close();
        }
    }

    public static void main(String[] args) {
        log.info("### Begin! ###");
        testA();
        emf.close();
        log.info("### End! ###");
    }
}
