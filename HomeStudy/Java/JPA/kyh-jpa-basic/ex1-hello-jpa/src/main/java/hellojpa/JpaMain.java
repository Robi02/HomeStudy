package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JpaMain {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
    
    public static void testB() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        try {
            tx.begin();

            MemberOld member = new MemberOld();
            member.setId(102L);
            member.setName("HelloJPA");

            // EntityManager(영속 컨텍스트) 1차 캐시에 저장
            // System.out.println("=== BEFORE PERSIST ===");
            // em.persist(member);
            // System.out.println("=== AFTER PERSIST ===");

            // EntityManager 1차 캐시에서 조회 -> 캐시에 없으면, DB에서 조회
            MemberOld findMemberOldA = em.find(MemberOld.class, 1L);
            MemberOld findMemberOldB = em.find(MemberOld.class, 1L);

            // 1차 캐시는 "한 트랜잭션 사이의 데이터"를 캐싱하는데,
            // 아주 복잡한 비즈니스로직의 쿼리인 경우 이득을 볼 수 있다.
            // 위 find()를 테스트하면, DB로 1회만 select하는것을 확인할 수 있음.

            System.out.println("findMemberOldA.id = " + findMemberOldA.getId());
            System.out.println("findMemberOldA.name = " + findMemberOldA.getName());
            System.out.println("findMemberOldB.id = " + findMemberOldB.getId());
            System.out.println("findMemberOldB.name = " + findMemberOldB.getName());

            // 놀랍게도 A == B 의 결과가 true이다.
            // 따라서, 1차 캐시에서 생성된 같은 객체를 참조하는것을 확인할 수 있다.
            // 1차 캐시를 사용하면 반복 가능한 읽기(REPETABLE READ)등급의
            // 트랜잭션 격리 수준을 DB가 아닌 애플리케이션 차원에서 제공한다.
            System.out.println("Is A == B ? : " + (findMemberOldA == findMemberOldB));
            
            tx.commit();
        }
        catch (Exception e) {
            tx.rollback();
            log.error("{}", e);
        }
        finally {
            em.close();
        }
    }

    public static void testC() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        try {
            tx.begin();

            MemberOld member1 = new MemberOld();
            MemberOld member2 = new MemberOld();

            member1.setId(150L);
            member1.setName("A");
            member2.setId(160L);
            member2.setName("B");

            System.out.println("=== persist begin ===");
            em.persist(member1); // 이 시점에는 EntityManager의 1차 캐시에 추가되면서
            em.persist(member2); // EntityManager의 쓰기 지연 SQL저장소에 쿼리가 추가된다.
            System.out.println("=== persist end ===");
            
            tx.commit();
            // tx.commit()을 호출할 때 쓰기 지연 SQL저장소의 쿼리를 DB로 밀어넣는다. (JDBC Batch)
            // persistence.xml의 hibernate.jdbc.batch_size옵션에서 배치 크기를 지정할 수 있다.
        }
        catch (Exception e) {
            tx.rollback();
            log.error("{}", e);
        }
        finally {
            em.close();
        }
    }

    /**
     * 엔티티 수정 감지
     */
    public static void testD() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        try {
            tx.begin();

            MemberOld member = em.find(MemberOld.class, 150L);
            member.setName("testD");
            // em.flush() 메서드로 commit전에 강제로 쿼리 전송도 가능하다.

            tx.commit();

            // EntityManager(영속 컨텍스트)의 1차 캐시에서 데이터는 아래와 같이 관리된다
            /**
             *      @Id     | Entity    | Snapshot
             *      150L    | memberA   | 변경작업이 있기 전 원본
             *      160L    | memberB   | 변경작업이 있기 전 원본
             */
            // 위 member.setName() 메서드를 호출하면 memberA객체가 변경되게 되고, Snapshot과
            // 비교하여 차이점이 생겼기 때문에 tx.commit()의 시점에 update쿼리가 DB로 전송된다.
            // 따라서, em.persist(member); 이 코드를 사용하면 안되는 점에 유의해야 한다.
        }
        catch (Exception e) {
            tx.rollback();
            log.error("{}", e);
        }
        finally {
            em.close();
        }
    }

    /**
     * 준영속 상태
     */
    public static void testE() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        try {
            tx.begin();
            
            MemberOld member = em.find(MemberOld.class, 150L);
            em.clear();
            // 영속성 컨텍스트를 초기화하여 캐시가 사라졌으므로,
            // 아래 find문에서도 DB로 select쿼리를 날린다.

            member = em.find(MemberOld.class, 150L);
            member.setName("testE");
            
            em.detach(member);
            // member가 영속성 컨텍스트에서 제외되면서 update 쿼리가 실행되지 않는것을 확인할 수 있다.
            
            tx.commit();
        }
        catch (Exception e) {
            tx.rollback();
            log.error("{}", e);
        }
        finally {
            em.close();
        }
    }

    /**
     * 엔티티 매핑
     */
    public static void testF() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        try {
            tx.begin();
            
            MemberOld member = new MemberOld();

            member.setId(1L);
            member.setName("UserA");
            member.setAge(0);
            member.setRoleType(RoleType.ADMIN);

            em.persist(member);
            tx.commit();
        }
        catch (Exception e) {
            tx.rollback();
            log.error("{}", e);
        }
        finally {
            em.close();
        }
    }

    /**
     * 키 매핑
     */
    public static void testG() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        try {
            tx.begin();
            

            
            tx.commit();
        }
        catch (Exception e) {
            tx.rollback();
            log.error("{}", e);
        }
        finally {
            em.close();
        }
    }

    public static void main( String[] args ) {
        System.out.println("Begin!");
        
        //testB();
        //testC();
        //testD();
        //testE();
        //testF();
        testG();

        emf.close(); // When JVM terminated...
        System.out.println("End!");
    }  
}
