package com.de4bi.study.jpa.jpatypes;

import java.time.LocalDateTime;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.de4bi.study.jpa.jpatypes.domain.Address;
import com.de4bi.study.jpa.jpatypes.domain.AddressEntity;
import com.de4bi.study.jpa.jpatypes.domain.Member;
import com.de4bi.study.jpa.jpatypes.domain.MemberEx;
import com.de4bi.study.jpa.jpatypes.domain.Period;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JpaMain {

    final static EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

    /**
     * '@Embeded' 사용하기.
     */
    public static void test1(EntityManager em){

        Address homeAddress = new Address("서울", "동대문구", "?");
        // homeAddress.setCity("서울");
        // homeAddress.setStreet("동대문구");
        // homeAddress.setZipcode("?");

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

    /**
     * 객체 타입의 한계: 객체의 공유참조.
     */
    public static void test2(EntityManager em) {

        Address address = new Address("city", "street", "zipcode");
        // address.setCity("city");
        // address.setStreet("street");
        // address.setZipcode("zipcode");
        
        Member m1 = new Member();
        m1.setName("m1");
        m1.setHomeAddress(address);
        em.persist(m1);

        Member m2 = new Member();
        m2.setName("m2");
        m2.setHomeAddress(address);
        em.persist(m2);

        // m1.getHomeAddress().setCity("bad-city"); // -> 불변객체로 설계하여 (1)문제를 원천으로 막음
        em.persist(m1);
        em.flush(); // (1) update구문이 2번 나간다... (m1, m2)
        em.clear();

        // 핵심: 값 타입(@Embeddable)은 항상 불변객체로 만들어야 한다.
    }

    /**
     * 값 타입의 비교.
     */
    public static void test3(EntityManager em) {

        int i1 = 10;
        int i2 = 10;

        log.info("i1 == i2 : {}", (i1 == i2)); // true

        Address a1 = new Address("city", "street", "zipcode");
        Address a2 = new Address("city", "street", "zipcode");

        log.info("a1 == a2 : {}", (a1 == a2)); // false
        log.info("a1.equals(a2) : {}", (a1.equals(a2))); // 기본적: false, equals @Override하여 true.
    }

    /**
     * 값 타입 컬렉션.
     */
    public static void test4(EntityManager em) {

        MemberEx m1 = new MemberEx();
        m1.setName("Member1");
        m1.setHomeAddress(new Address("homeCity", "street", "10000"));

        m1.getFavoriteFoods().add("치킨");
        m1.getFavoriteFoods().add("족발");
        m1.getFavoriteFoods().add("피자");

        m1.getAddressHistory().add(new Address("old1", "street", "10001"));
        m1.getAddressHistory().add(new Address("old2", "street", "10002"));

        em.persist(m1);
        em.flush();
        em.clear();

        log.info("==== Starting find ====");
        MemberEx findMember = em.find(MemberEx.class, m1.getId());
        log.info("==== Lazy loading ====");
        for (Address ad : findMember.getAddressHistory()) {
            log.info(ad.getCity());
        }

        findMember.getAddressHistory().remove(new Address("old1", "street", "10001")); // equals()가 구현되어 있어야 함에 유의!
        findMember.getAddressHistory().add(new Address("newCity1", "street", "10003"));

        // 위 메서드의 쿼리를 잘 보자... delete + insert가 아니라, delete + insert + insert가 호출되었다!
        // 값 타입 컬렉션에 변경 사항이 발생하면, 주인 엔티티와 연관된 '모든' 데이터를 삭제하고,
        // 값 타입 컬렉션에 있는 현재 값을 모두 다시 저장하기 때문.

        // 대안: 값 타입 컬렉션 대신, 일대 다 관계를 고려하는게 낫다.

        AddressEntity ae = new AddressEntity();
        ae.setAddress(new Address("cityex", "streetex", "zipcodeex"));
        findMember.getAddressHistoryEx().add(ae);

        // [정리]
        //
        // 1.엔티티 타입의 특징
        // 1) 식별자 있음
        // 2) 생명 주기가 관리됨
        // 3) 참조로 공유됨
        //
        // 2. 값 타입의 특징
        // 1) 식별자 없음
        // 2) 생명 주기를 엔티티에 의존함
        // 3) 공유하지 않는 불변으로 사용하는것이 안전 (복사해서 사용)

    }

    public static void main(String[] args) {
        final EntityManager em = emf.createEntityManager();
        final EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // test1(em);
            // test2(em);
            // test3(em);
            test4(em);

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
