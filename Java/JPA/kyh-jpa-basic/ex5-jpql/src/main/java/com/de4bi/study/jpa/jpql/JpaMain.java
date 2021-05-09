package com.de4bi.study.jpa.jpql;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.de4bi.study.jpa.jpql.domain.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JpaMain {

    final static EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

    /**
     * 기본적인 jpql select.
     */
    public static void test1(EntityManager em){

        // 테이블이 아닌 객체를 대상으로 검색하는 객체지향 쿼리
        List<Member> result = em.createQuery(
            // -> SQL을 추상화해서 특정 DB SQL에 의존하지 않음
            "select m from Member m where m.name like '%ki'",
            Member.class
        ).getResultList();

        log.info("result : " + result.toString());
    }

    /**
     * 동적 쿼리 Criteria. (JPQL 자바표준 빌더)
     *  java 표준 스팩이기는 하지만, 실무에서 사용하기에는 조금 어렵다...
     * -JPQL : Java Persistence Query Language.
     */
    public static void test2(EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Member> query = cb.createQuery(Member.class);

        Root<Member> m = query.from(Member.class);

        query.select(m).where(cb.like(m.get("name"), "kim"));

        List<Member> result = em.createQuery(query).getResultList();

        log.info("result : " + result.toString());
    }

    /**
     * JPQL 시작.
     * 1) Entity와 속성은 대소문자 구분.
     * 2) JPQL키워드(select, from, ...)은 대소문자 구분 안함.
     * 3) Entity의 이름을 사용, 테이블 이름X (select m from 'Member')
     * 4) 별칭(alias)는 필수, as는 생략 가능 (select 'm' from Member as m)
     */
    public static void test3(EntityManager em) {
        Member m = new Member();
        m.setName("member1");
        m.setAge(10);
        em.persist(m);

        // 반환 타입이 명확 (Member)
        TypedQuery<Member> query1 = em.createQuery("select m from Member as m", Member.class);

        // 반환 타입이 병확 (String)
        TypedQuery<String> query2 = em.createQuery("select m.name from Member as m where m.id = '1'", String.class);
        
        // 반환 타입이 불명확 (Member.name -> String, Member.age -> Integer)
        Query query3 = em.createQuery("select m.name, m.age from Member as m");
        
        // getResultList() :: 결과를 리스트로 획득 (결과가 없으면 빈 리스트 반환)
        List<Member> resultList = query1.getResultList();

        // getSingleResult() :: 결과가 명맥히 1개인 경우 (결과가 정확히 1개가 아닌 경우 예외 발생)
        // - 결과가 없음: javax.persistence.NoResultException
        // - 결과가 둘 이상: javax.persistence.NonUniqueResultException
        try {
            String singleResult = query2.getSingleResult();
        }
        catch (NoResultException | NonUniqueResultException e) {
            log.error("Exception!", e);
        }

        // 쿼리 바인딩
        TypedQuery<Member> bindedQuery = em.createQuery(
            "select m from Member as m where name = :name", // ':name'
            Member.class
        );

        bindedQuery.setParameter("name", "member1");
        List<Member> bindingQueryList = bindedQuery.getResultList();

        for (Member bm : bindingQueryList) {
            log.info("Member.id : {} / Member.name : {}", bm.getId(), bm.getName());
        }
    }

    /**
     * 
     */
    public static void test4(EntityManager em) {

    }

    public static void main(String[] args) {
        final EntityManager em = emf.createEntityManager();
        final EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // test1(em);
            // test2(em);
            test3(em);
            // test4(em);
            

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
