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
     * 페치 조인.
     * 연관된 데이터를 한번의 쿼리로 함께 조회. 성능상 중요하다!
     */
    public static void test2(EntityManager em) {
        // 테스트 초기화
        Team[] teams = new Team[3];

        for (int i = 0; i < 3; ++i) {
            Team team = new Team();
            team.setName("팀" + (i + 1));
            teams[i] = team;
            em.persist(team);
        }

        for (int i = 0; i < 4; ++i) {
            Member m = new Member();
            m.setName("회원" + (i + 1));
            m.setAge(30 + i);
            m.setTeam(teams[i % teams.length]);
            em.persist(m);
        }

        Member m0 = new Member();
        m0.setName("회원0");
        m0.setAge(30);
        m0.setTeam(null);
        em.persist(m0);

        em.flush();
        em.clear();

        // [1] 페치 조인
        log.info("===== [1] =====");
        String q1 = "select m from Member m join fetch m.team"; // 지연 조인 (FetchType.LAZY)보다 'join fetch' 구문이 우선적
        /*
            select M.* T.* from Member M
            inner join team T on M.TEAM_ID = T.ID
        */
        List<Member> r1 = em.createQuery(
            q1, Member.class
        ).getResultList();

        for (Member m : r1) {
            log.info("m.name = " + m.getName() + ", m.team.name = " + m.getTeam().getName()); // 팀 정보를 캐시에서 가져옴
        }
        log.info("/===== [1] =====");
        em.clear();

        // [2] 일반 조회
        log.info("===== [2] =====");
        String q2 = "select m from Member m";
        /**
         * select * from Member;
         */
        List<Member> r2 = em.createQuery(
            q2, Member.class
        ).getResultList();

        for (Member m : r2) { // 최악의 경우 N+1(N:조회된 팀의 종류, 1:맴버 조회 쿼리) 수 만큼 쿼리를 수행...
            Team t = m.getTeam();
            /*
                select * from Team t where t.id = 1
            */
            /*
                select * from Team t where t.id = 2
            */
            /*
                select * from Team t where t.id = 3
            */
            log.info("m.name = " + m.getName() + ", m.team.name = " + (t != null ? t.getName() : "null")); // 팀 정보를 조회하여 가져옴
        }
        log.info("/===== [2] =====");
        em.clear();

        // -> [1]과 [2]의 하이버네이트 쿼리를 잘 확인해 보자...
        // 페치 조인을 꼭 필요에 맞게 쓰는것이 중요하다.

        // [3] 컬렉션 페치 조인 (일대 다의 데이터 증폭?)
        log.info("===== [3] =====");
        String q3 = "select t from Team t join fetch t.members";
        /*
            select * from Team t inner join Member m on t.id = m.team_id
         */
        List<Team> r3 = em.createQuery(q3, Team.class).getResultList();

        for (Team t : r3) {
            log.info("team.name = " + t.getName() + ", team = " + t);
            for (Member m : t.getMembers()) {
                // 페치 조인으로 팀과 회원을 함께 조회해서 지연로딩 발생 안 함
                log.info("  -> member.name = " + m.getName() + ", member = " + m); // 객체 주소를 잘 보자... 팀1이 두번 등장한다!
            }
        }
        log.info("/===== [3] =====");

        // [4] 컬렉션 페치 조인 중복 제거
        log.info("===== [4] =====");
        String q4 = "select distinct t from Team t join fetch t.members";
        // 일반 SQL이였다면, distinct명령어로는 부족하다(결과 테이블이 다름) 하지만, JPQL에서는 후조치로 데이터를 삭제해준다!
        /*
            select distinct * from Team t inner join Member m on t.id = m.team_id
         */
        List<Team> r4 = em.createQuery(q4, Team.class).getResultList();

        for (Team t : r4) {
            log.info("team.name = " + t.getName() + ", team = " + t);
            for (Member m : t.getMembers()) {
                log.info("  -> member.name = " + m.getName() + ", member = " + m); // 객체 주소를 잘 보자. 중복이던 팀1이 사라졌음!
            }
        }
        log.info("/===== [4] =====");

        // -> 다대 일의 경우는 발생하지 않는다.

        // 일반 조인과 페치 조인과의 차이
        // 일반 조인은 연관된 엔티티를 같이 조회하지 않는다. (JPQL은 결과 반환에 연관관계를 고려하지 않는다.)
        // 페치 조인은 연관된 엔티티를 같이 조회한다. (연관관계를 고려한다.)
    }

    /**
     * 페치 조인의 한계.
     */
    public static void test3(EntityManager em) {
        // 테스트 초기화
        Team[] teams = new Team[3];

        for (int i = 0; i < 3; ++i) {
            Team team = new Team();
            team.setName("팀" + (i + 1));
            teams[i] = team;
            em.persist(team);
        }

        for (int i = 0; i < 4; ++i) {
            Member m = new Member();
            m.setName("회원" + (i + 1));
            m.setAge(30 + i);
            m.setTeam(teams[i % teams.length]);
            em.persist(m);
        }

        Member m0 = new Member();
        m0.setName("회원0");
        m0.setAge(30);
        m0.setTeam(null);
        em.persist(m0);

        em.flush();
        em.clear();

        // [1] 페치조인 대상에는 별칭을 줄 수 없다. (하이버네이트는 가능하지만, 가급적 쓰면 안된다!)
        String q1 = "select t from Team t join fetch t.members as m"; // 'as m' 쓰지 말자...

        // [2] 둘 이상의 컬렉션을 페치조인 할 수 없다.

        // [3] 컬렉션을 페치 조인하면 페이징 API (setFirstResult, setMaxResult)를 사용할 수 없다.
        // test2()의 (3)번 예제를 확인해 보면 데이터가 늘어난 것을 볼 수 있는데, 여기에 setFirst/Max를 사용하면
        // 원하는 데이터를 가져올 수 있다는 보장이 '없다'.
        // 하이버네이트는 경고 문구를 로그로 남겨주고, 모든 데이터를 메모리에 끌고와서 긁어온다. (DB과부하 발생)

        // [3]번 보충부터 시작. @@
    }

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
            log.error("Exception!", e);
            tx.rollback();
        }

        em.close();
        emf.close();
    }
}
