package com.de4bi.study.jpa.jpql;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.OrderColumn;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.de4bi.study.jpa.jpql.domain.Address;
import com.de4bi.study.jpa.jpql.domain.Member;
import com.de4bi.study.jpa.jpql.domain.MemberType;
import com.de4bi.study.jpa.jpql.domain.Order;
import com.de4bi.study.jpa.jpql.domain.Product;
import com.de4bi.study.jpa.jpql.domain.Team;

import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
     * 프로젝션: SELECT절 조회 대상을 지정.
     */
    public static void test4(EntityManager em) {
        // 테스트 초기화
        Product p = new Product();
        p.setName("Product1");
        p.setPrice(1000);
        p.setStockAmount(40);
        em.persist(p);

        Address addr = new Address();
        addr.setCity("city");
        addr.setStreet("street");
        addr.setZipcode("zipcode");

        Order o = new Order();
        o.setAddress(addr);
        o.setOrderAmount(30);
        o.setProduct(p);
        em.persist(o);

        Team t = new Team();
        t.setName("T1");
        em.persist(t);

        Member m = new Member();
        m.setName("M1");
        m.setAge(10);
        m.setTeam(t);
        em.persist(m);
        em.flush();
        em.clear();

        // 엔티티 프로젝션
        List<Member> result = em.createQuery("select m from Member as m", Member.class).getResultList();
        Member findMember = result.get(0);
        findMember.setAge(20); // -> EntityManager에서 관리되므로 tx.commit시점에 값이 변경됨.
        
        // 엔티티 프로젝션: 조인
        // 위 쿼리보다는 아래 쿼리가 조금 더 안정적(DB쿼리랑 비슷한게 좋다.)
        String q1 = "select m.team from Member m";          // 묵시적 Join
        String q2 = "select t from Member m join m.team t"; // 명시적 Join
        List<Team> resultTeam = em.createQuery(q2, Team.class).getResultList();
        Team findTeam = resultTeam.get(0);
        log.info("findTeam.name = " + findTeam.getName());

        // 임베디드 타입 프로젝션
        List<Address> resultAddr = em.createQuery("select o.address from Order o", Address.class).getResultList();
        log.info("resultAddr.city = " + resultAddr.get(0).getCity());
        
        // 스칼라 타입 프로젝션
        // 1) 제너릭을 사용하여 조회
        List<Object[]> resultScalar = em.createQuery("select m.name, m.age from Member m", Object[].class).getResultList();
        Object[] resultAry = resultScalar.get(0);
        log.info("member.name = " + resultAry[0]); // m.name
        log.info("member.age = " + resultAry[1]); // m.age
        
        // 2) new 명령어로 조회
        List<MemberDto> resultScalar2 = em.createQuery("select new com.de4bi.study.jpa.jpql.JpaMain$MemberDto(m.name, m.age) from Member m", MemberDto.class).getResultList();
        for (MemberDto dto : resultScalar2) {
            log.info("dto.name = " + dto.getName());
            log.info("dto.age = " + dto.getAge());
        }
    }

    @AllArgsConstructor @Getter @Setter // 조회쿼리와 순서/타입이 일치하는 생성자가 필요.
    public static class MemberDto {
        private String name;
        private int age;
    };

    /**
     * 페이징.
     * 다음 두 API로 페이징을 추상화.
     *  1) setFirstResult(int startPosition) : 조회 시작 위치 (0부터)
     *  2) setMaxResults(int maxResult) : 조회할 데이터 수
     */
    public static void test5(EntityManager em) {
        // 테스트 초기화
        for (int i = 0; i < 100; ++i) {
            Member m = new Member();
            m.setName("M" + i);
            m.setAge((i + 1) * 2);
            m.setTeam(null);
            em.persist(m);
        }

        em.flush();
        em.clear();

        List<Member> result = em.createQuery("select m from Member m order by m.age desc", Member.class)
            .setFirstResult(10)
            .setMaxResults(20)
            .getResultList();

        log.info("ReulstSize = " + result.size());
        for (Member m : result) {
            log.info("m.id = " + m.getId() + " / m.name = " + m.getName() + " / m.age = " + m.getAge());
        }
    }

    /**
     * 조인.
     */
    public static void test6(EntityManager em) {
        // 테스트 초기화
        Team t = new Team();
        t.setName("T");
        em.persist(t);

        for (int i = 0; i < 5; ++i) {
            Member m = new Member();
            m.setName("M" + i);
            m.setAge((i + 1) * 2);
            m.setTeam(t);
            em.persist(m);
        }

        String query = "select m from Member m inner join m.team t";
        // String query = "select m from Member m left outer join m.team t";
        List<Member> result = em.createQuery(query, Member.class).getResultList();
        // String thetaJoinQuery = "select m from Member m, Team t where m.name = t.name";
        // List<Member> result = em.createQuery(thetaJoinQuery, Member.class).getResultList();

        for (Member m : result) {
            log.info("m.name = " + m.getName() + " / m.team.name = " + m.getTeam().getName());                        
        }

        // ON 절을 사용한 조인 (JPA2.1+ / Hibernate5.1+)
        String qy = "select m from Member m left join m.team t on t.name = 'T'";
        List<Member> rst = em.createQuery(query, Member.class).getResultList();

        for (Member m : rst) {
            log.info("m.name = " + m.getName() + " / m.team.name = " + m.getTeam().getName());
        }

        // 현재, 서브쿼리는 JPQL에서 지원하지 않음. (2021.05.10)
        // 조인으로 풀 수 있으면 풀어서 해결... 또는 쿼리를 2번 쓰는 방식을 사용
        // JPA 표준은 WHERE, HAVING 절에서만 서브쿼리를 지원하지만,
        // 하이버네이트에서 select절도 지원한다.
    }

    /**
     * JPQL 타입 표현.
     */
    public static void test7(EntityManager em) {
        
        Member member = new Member();
        member.setName("M1");
        member.setType(MemberType.USER);
        member.setAge(10);
        em.persist(member);
        em.flush();
        em.clear();

        // String 'HELLO', Boolean 'TRUE', Enum 'm.type'
        String query1 = "select m.name, m.type, 'HELLO', TRUE From Member m " +
                        "where m.type = :type"; // 또는 풀 패키지명 (com.de4bi.study.jpa.jpql.domain.MemberType.USER)
        List<Object[]> result1 = em.createQuery(query1, Object[].class)
            .setParameter("type", MemberType.USER)
            .getResultList();

        for (Object[] obj : result1) {
            log.info("obj[0] = " + obj[0]);
            log.info("obj[1] = " + obj[1]);
            log.info("obj[2] = " + obj[2]);
            log.info("obj[3] = " + obj[3]);
        }
    }

    /**
     * 조건식.
     */
    public static void test8(EntityManager em) {
        // 테스트 초기화
        for (int i = 0; i < 10; ++i) {
            Member member = new Member();
            member.setName((i == 5 ? null : ("M" + i)));
            member.setType(MemberType.USER);
            member.setAge(i + 10);
            em.persist(member);
        }
        em.flush();
        em.clear();

        // case, when-then, else 구문
        String query1 = "select case when m.age <= 10 then '어린이 요금' " +
                                    "when m.age >= 20 then '학생 요금' " +
                                    "else '일반요금' " +
                                "end " +
                        "from Member m";
        List<String> result1 = em.createQuery(query1, String.class).getResultList();

        for (String str : result1) {
            log.info("str : " + str);
        }
        log.info("================================");

        // COALESCE, NULLIF구문
        // 1) COALESCE: 하나씩 조회해서 null이 아니면 반환.
        // 2) NULLIF: 두 값이 같으면 null, 다르면 첫 번째 값 반환.
        String query2 = "select coalesce(m.name, '이름 없는 회원') as name1, " +
                        "NULLIF(m.name, 'M0') as name2 from Member m";
        List<Object[]> result2 = em.createQuery(query2, Object[].class).getResultList();

        for (Object[] str : result2) {
            log.info("str[0] : " + str[0] + " / str[1] : " + str[1]);
        }
    }

    /**
     * JPQL 기본 함수/사용자 정의 함수.
     */
    public static void test9(EntityManager em) {
        // 테스트 초기화
        Team team = new Team();
        team.setName("T0");
        em.persist(team);

        for (int i = 0; i < 10; ++i) {
            Member member = new Member();
            member.setName("M" + i);
            member.setType(i % 2 == 0 ? MemberType.USER : MemberType.ADMIN);
            member.setAge(i + 10);
            member.setTeam(i % 2 == 0 ? team : null);
            em.persist(member);
        }
        em.flush();
        em.clear();

        // 1. || : 문자열 연결
        String query1 =
            "select 'a' || 'b' from Member m where m.id = 1";
        List<String> result1= em.createQuery(query1, String.class).getResultList();

        log.info("<=== t1 ===>");
        for (String str : result1) {
            log.info("str = " + str);
        }
        log.info("</=== t1 ===>");

        // 2. concat : 문자열 연결
        String query2 =
        "select concat('c', 'd') from Member m where m.id = 1";
        List<String> result2= em.createQuery(query2, String.class).getResultList();

        log.info("<=== t2 ===>");
        for (String str : result2) {
            log.info("str = " + str);
        }
        log.info("</=== t2 ===>");

        // 3. substring : 문자열 자르기
        String query3 =
        "select substring(m.name, 2, 1) from Member m where m.id = 1";
        List<String> result3= em.createQuery(query3, String.class).getResultList();

        log.info("<=== t3 ===>");
        for (String str : result3) {
            log.info("str = " + str); // str = 0 이 출력됨 (subsstring(m.name, 2, 1)) -> 2번째부터, 1글자
        }
        log.info("</=== t3 ===>");

        // 4. trim : 양쪽 공백 제거 (생략)
        // 5. lower, upper : 대소문자로 치환 (생략)
        // 6. length : 문자열의 길이 (생략)
        
        // 7. locate : 부분문자열의 위치를 출력
        String query4 =
            "select locate('de', 'abcdefg') from Member m where m.id = 1";
        List<Integer> result4= em.createQuery(query4, Integer.class).getResultList();

        log.info("<=== t4 ===>");
        for (Integer integer : result4) {
            log.info("integer = " + integer); // 4 출력 (abc'de'fg -> 4번째 위치)
        }
        log.info("</=== t4 ===>");
        
        // 8. abs, sqrt, mod : 딱봐도 수학 함수 (생략)

        // 9. size, index : 컬랙션의 크기, (JPA용도)
        String query5 =
            "select size(t.members) from Team t where t.id = 1";
        List<Integer> result5= em.createQuery(query5, Integer.class).getResultList();

        log.info("<=== t5 ===>");
        for (Integer integer : result5) {
            log.info("integer = " + integer); // 컬랙션의 크기 반환
        }
        log.info("</=== t5 ===>");

        // index -> 값 타입 컬랙션에서 @OrderColumn 로 정한 컬랙션의 위치값을 반환 (안쓰는게 좋음 - 생략)

        // 10. function : 사용자 정의 함수
        String theLastQuery =
            "select function('group_concat', m.name) from Member m";
            // "select group_concat(m.name) from Member m"; -> 동일한 출력으로 JPQL표준은 아니지만, 하이버네이트에서는 지원
        List<String> theLastResult = em.createQuery(
            theLastQuery, String.class
        ).getResultList();

        log.info("<=== Last ===>");
        for (String str : theLastResult) {
            log.info("str = " + str);
        }
        log.info("</=== Last ===>");
    }

    /* 사용자 정의 함수 등록 */
    public static class MyH2Dialect extends H2Dialect {
        public MyH2Dialect() {
            // persistence.xml에서 'hibernate.dialect'의 값을 변경해야 함.
            // 클래스 선언부를 보면, 다른 방언들이 어떻게 설정되어있나 확인이 가능하다.
            registerFunction("group_concat", new StandardSQLFunction("group_concat", StandardBasicTypes.STRING));
        }
    }

    public static void main(String[] args) {
        final EntityManager em = emf.createEntityManager();
        final EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // test1(em);
            // test2(em);
            // test3(em);
            // test4(em);
            // test5(em);
            // test6(em);
            // test7(em);
            // test8(em);
            test9(em);

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
