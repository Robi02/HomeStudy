package com.de4bi.study.jpa.jpatypes;

import java.time.LocalDateTime;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.de4bi.study.jpa.jpatypes.domain.Address;
import com.de4bi.study.jpa.jpatypes.domain.Member;
import com.de4bi.study.jpa.jpatypes.domain.Period;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JpaMain {

    final static EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

    /**
     * '@Embeded' 사용하기.
     */
    public static void test1(EntityManager em){

        Address homeAddress = new Address();
        homeAddress.setCity("서울");
        homeAddress.setStreet("동대문구");
        homeAddress.setZipcode("?");

        Period workPeriod = new Period();
        workPeriod.setStartDate(LocalDateTime.of(2019, 4, 2, 0, 0, 0));
        workPeriod.setEndDate(LocalDateTime.now());

        Member member = new Member();
        member.setName("태훈");
        member.setHomeAddress(homeAddress);
        member.setWorkPeriod(workPeriod);

        em.persist(member);
        em.flush();
        em.clear();
    }

    public static void main(String[] args) {
        final EntityManager em = emf.createEntityManager();
        final EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            test1(em);            

            tx.commit();
        }
        catch (Exception e) {
            log.error("{}", e);
            tx.rollback();
        }

        em.close();
        emf.close();
    }
}
