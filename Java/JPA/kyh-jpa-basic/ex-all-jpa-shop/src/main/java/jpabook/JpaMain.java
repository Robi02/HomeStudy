package jpabook;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JpaMain {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
    
    /**
     * 
     */
    public static void logicA() {
        final EntityManager em = emf.createEntityManager();
        final EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();



            tx.commit();
        }
        catch (Exception e) {
            tx.rollback();
        }
        finally {
            em.close();
        }
    }

    public static void main(String[] args) {
        log.info("Begin!");
        logicA();
        emf.close();
        log.info("End!");
    }
}
