package relations;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import lombok.extern.slf4j.Slf4j;
import relations.manytomany.Many2Many;
import relations.manytoone.Many2One;
import relations.onetomany.Member;
import relations.onetomany.Team;

@Slf4j
public class JpaMain {
    
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

    /**
     *
     */
    public static void template() {
        final EntityManager em = emf.createEntityManager();
        final EntityTransaction tx = em.getTransaction();

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

    /**
     *
     */
    public static void logicA() {
        final EntityManager em = emf.createEntityManager();
        final EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Team team = new Team();
            team.setName("TestA");
            em.persist(team);
            
            Member member = new Member();
            member.setUsername("member1");
            member.changeTeam(team); // member.setTeam(team); // 두 메서드의 차이점은?
            em.persist(member);

            // em.flush();
            // em.clear(); // EntityContext 초기화

            Member findMember = em.find(Member.class, member.getId());
            Team findTeam = findMember.getTeam();

            log.info("========");
            for (Member m : findTeam.getMembers()) {
                log.info("m = " + m.getUsername());
            }
            log.info("========");

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

    public static void main(String[] args) {
        log.info("+ Begin! +");
        
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // Many2One.work(em);
            Many2Many.work(em);

            tx.commit();
        }
        catch (Exception e) {
            tx.rollback();
        }
        finally {
            em.close();
        }

        emf.close();
        log.info("+ Complete! +");
    }
}
