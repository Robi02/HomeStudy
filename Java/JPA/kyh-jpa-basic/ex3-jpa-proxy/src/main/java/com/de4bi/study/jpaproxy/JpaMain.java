package com.de4bi.study.jpaproxy;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.de4bi.study.jpaproxy.domain.Member;

import org.hibernate.Hibernate;
import org.hibernate.LazyInitializationException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JpaMain {

    /**
     * [ em.find() vs. em.getReference() ]
     */
    public static void test1(final EntityManager em) {
        
        Member member = new Member();
        member.setName("맴버1");
        em.persist(member);
        em.flush();
        em.clear();

        /**
         * em.find() 로 찾는 경우, 메서드 실행과 동시에
         * select 쿼리를 날려서 DB에서 조회해 온다.
         */
        // Member findMember = em.find(Member.class, member.getId());

        /**
         * em.getReference() 로 찾는 경우, 메서드 실행과 동시에
         * 프록시 엔티티 객체를 조회해 온다. 이 객체에서
         * getter로 필드에 접근하는 경우에 DB에 select 쿼리를 날린다.
         * 
         * [MemberProxy]           [Member]
         * - Member target; -----> - Long id;
         * -------------^--        - String name;
         * - getId() -- | --|     ---------------
         * - getName()  |   +----> - getId()
         *       |      | (2)      - getName()
         *       v (1)  |
         * +-------------------------------------+
         * |       Entity Context에서 생성       |
         * +-------------------------------------+
         */
        Member findMember = em.getReference(Member.class, member.getId());
        log.info("findMember: " + findMember.getClass());
        log.info("findMember.name: " + findMember.getName());
        log.info("findMember.team: " + findMember.getTeam());
    }

    /**
     * [ 프록시 특징 ]
     * 
     * 1. 프록시 객체는 처음 사용시 1회만 초기화한다.
     * 2. 프록시 객체를 초기화 한 후, 실제 엔티티로 바뀌는것은 아니다.
     * 3. 프록시 객체는 원본 엔티티를 상속받는다. 타입 체크시 '==' 대신에 'instnace of'을 사용해야한다.
     * 4. EntityContext에 찾는 엔티티가 이미 있으면 em.getReference()로도 실제 엔티티를 반환한다.
     * 5. EntityManager가 관리하지 않는 준영속 상태일때 프록시를 초기화하면 LazyInitializationException예외를 던짐.
     */
    public static void test2(final EntityManager em) {
        
        Member member1 = new Member();
        member1.setName("M1");
        em.persist(member1);
        
        Member member2 = new Member();
        member1.setName("M2");
        em.persist(member2);
        
        em.flush();
        em.clear();

        // (1)번 검증
        Member m1r = em.getReference(Member.class, member1.getId());
        Member m1rr = em.getReference(Member.class, member1.getId());

        if (m1r == m1rr) {
            log.info("m1r == m1rr"); // O
        }
        else {
            log.info("m1r != m1rr");
        }

        em.flush();
        em.clear();

        // (2,3)번 검증
        Member m1 = em.find(Member.class, member1.getId());
        Member m2 = em.getReference(Member.class, member2.getId());

        if (m1 instanceof Member) {
            log.info("m1 is isntaceof Member"); // O
        }
        else {
            log.info("m1 isn't isntaceof Member");
        }
        
        if (m2.getClass() == Member.class) {
            log.info("m2.getClass() == Member.class");
        }
        else {
            log.info("m2.getClass() != Member.class"); // O
        }

        em.flush();
        em.clear();

        // (4)번 검증
        m1 = em.find(Member.class, member1.getId());
        m1r = em.getReference(Member.class, m1.getId());
        
        if (m1 == m1r) {
            log.info("m1 == m1r"); // O
        }
        else {
            log.info("m1 != m1r");
        }

        em.flush();
        em.clear();

        // (5)번 검증
        em.detach(m2); // 준영속상태로 변경

        try {
            log.info("m2.name = " + m2.getName());
        }
        catch (LazyInitializationException e) {
            log.error("Exception!", e);
        }
    }

    /**
     * [ Proxy Utils ]
     */
    public static void test3(EntityManager em) {

        Member member1 = new Member();
        member1.setName("M1");
        em.persist(member1);
        em.flush();
        em.clear();
        
        // 1. 프록시 인스턴스 초기화 여부 확인
        Member findMember = em.getReference(Member.class, member1.getId());
        log.info("1. findMemberLoaded: " + emf.getPersistenceUnitUtil().isLoaded(findMember)); // false

        // 2. 프록시 클래스 확인
        log.info("2. findMember: " + findMember.getClass().getName());

        // 3. 프록시 강제 초기화 (JPA표준이 아닌, Hibernate구현)
        Hibernate.initialize(findMember);
        log.info("3. findMemberLoaded: " + emf.getPersistenceUnitUtil().isLoaded(findMember)); // true
    }

    final static EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

    public static void main(String[] args) {
        final EntityManager em = emf.createEntityManager();
        final EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // test1(em);
            // test2(em);
            test3(em);

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
