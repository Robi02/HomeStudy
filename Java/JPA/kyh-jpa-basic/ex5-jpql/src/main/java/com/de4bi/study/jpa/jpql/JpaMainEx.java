package com.de4bi.study.jpa.jpql;

import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.de4bi.study.jpa.jpql.domain.Member;
import com.de4bi.study.jpa.jpql.domain.Team;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JpaMainEx {

    final static EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

    /**
     * 경로 표현식.
     */
    public static void test1(EntityManager em) {
        /**
         * select
         *  m.name -> 상태 필드
         * from
         *  Member m
         *  join m.team t -> 단일 값 연관 필드
         *  join m.orders o -> 컬렉션 값 연관 필드
         * where
         *  t.name = 'TeamA';
         * 
         * -> 어떤 연관 필드로 가냐에 따라 내부적인 동작 방식이 달라진다.
         *    따라서, 세 가지의 구분이 꼭 필요하다.
         * 
         * 1) 상태 필드: 단순히 값을 저장하기 위한 필드.
         * 2) 연관 필드: 연관관계를 위한 필드
         *  2-1) 단일 값 연관 필드
         *   -> @ManyToOne, @OneToOne (대상이 엔티티)
         *  2-2) 컬렉션 값 연관 필드
         *   -> @OneToMany, @ManyToMany (대상이 컬렉션)
         * 
         *  (1) 상태 필드 특징: 경로 탐색의 끝, 더이상 탐색하지 않음.
         *  (2) 단일 값 연관 경로: 
         */

        // 테스트 초기화
        Team team1 = new Team();
        team1.setName("팀1");
        em.persist(team1);

        Member m1 = new Member();
        m1.setName("관리자1");
        m1.setAge(30);
        m1.setTeam(team1);
        em.persist(m1);

        Member m2 = new Member();
        m1.setName("관리자2");
        m1.setAge(34);
        em.persist(m2);

        em.flush();
        em.clear();

        // 상태 필드 (생략)
        // String query0 = "select m.name from Member m";
        /*
            select
                member0_.name as col_0_0_
            from
                Member member0_
        */

        // 단일 값 연관 필드
        String query1 = "select m.team.name from Member m";
        /*
            select
                team1_.name as col_0_0_
            from
                Member member0_ cross
            join                            -> [!!] m.team.name으로 인한 묵시적인 inner join 발생. (잘 모르고 사용하면 심각한 부하 초래)
                Team team1_
            where
                member0_.TEAM_ID=team1_.id
        */
        List<String> result1 = em.createQuery(query1, String.class).getResultList();

        log.info("==== T1 Begin ====");
        for (String str : result1) {
            log.info("str = " + str);
        }
        log.info("==== T1 End ====");

        // 컬렉션 값 연관 필드
        String query2 = "select t.members from Team t where t.id = 1";
        /*
            select
                members1_.id as id1_0_,
                members1_.age as age2_0_,
                members1_.name as name3_0_,
                members1_.TEAM_ID as team_id5_0_,
                members1_.type as type4_0_
            from
                Team team0_
                inner join                           -> [!!] t.members으로 인한 묵시적인 inner join 발생.
                Member members1_
                on team0_.id=members1_.TEAM_ID
            where
                team0_.id=1
        */
        Collection result2 = em.createQuery(query2).getResultList();

        log.info("==== T2 Begin ====");
        for (Object obj : result2) {
            log.info("obj = " + obj);
        }
        log.info("==== T2 End ====");

        // -> 항상 명시적 join을 사용하는것이 사고 예방에 좋다.
    }

    /**
     * 
     */
    public static void test2(EntityManager em) {

    }

    public static void main(String[] args) {
        final EntityManager em = emf.createEntityManager();
        final EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // test1(em);
            test2(em);

            tx.commit();
        }
        catch (Exception e) {
            log.error("Exception!", e);
            tx.rollback();
        }

        em.close();
        emf.close();
    }
}
