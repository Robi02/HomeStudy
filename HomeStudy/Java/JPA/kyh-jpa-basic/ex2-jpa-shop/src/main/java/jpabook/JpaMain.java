package jpabook;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import jpabook.jpashop.domain.Book;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JpaMain {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-shop");
    
    /**
     * 
     */
    public static void testA() {
        final EntityManager em = emf.createEntityManager();
        final EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Book book = new Book();
            book.setName("JPA 길라잡이");
            book.setAuthor("김영한");
            em.persist(book);

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
        testA();
        emf.close();
        log.info("End!");
    }
}
