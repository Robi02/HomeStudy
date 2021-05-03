package inheritmapping;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import inheritmapping.joined.JoinedStretegy;
import inheritmapping.singletable.SingleTableStretegy;
import inheritmapping.tableperclass.TablePerClassStretegy;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JpaMain {

    /**
     * ----------------------------------------------------------------
     * + JPA의 3가지 상속 매핑 전략 +
     * ----------------------------------------------------------------
     * 
     * 1) JOINED
     *  [ITEM] - ITEM_ID(PK), NAME, PRICE
     *  [ALBUM] - ITEM_ID(PK,FK), AUTHOR, ISBN
     *  [MOVIE] - ITEM_ID(PK,FK), ACTOR, DIRECTOR
     *  [BOOK] - ITEM_ID(PK,FK), ARTIST
     * 
     * * (Good)
     * -> Normalizated Table.
     * -> Possible to use 'FK'.
     * -> Storage effectvie.
     * 
     * * (Bad)
     * -> Many 'join' query -> Low performance.
     * -> Complexed 'join' query.
     * -> Double 'insert' query for saving each data. (insert into ITEM, insert into ALBUM)
     * 
     * ----------------------------------------------------------------
     * 
     * 2) SINGLE_TABLE (JPA DEFAULT)
     *  [ITEM] - ITEM_ID(PK), NAME, PRICE, AUTHOR, ISBN, ACTOR, DIRECTOR, ARTIST
     * 
     * * (Good)
     * -> No 'join' -> Quick search.
     * -> Simple 'join' query.
     * 
     * * (Bad)
     * -> CRIT: Child @Entity's column should be nullable. (When you insert 'ALBUM', ACTOR, DIRECTOR, AUTHOR, ISBN values: null)
     * -> LARGE Table -> Low performance sometime. (But, case by case)
     * 
     * ----------------------------------------------------------------
     * 
     * 3) TABLE_PER_CLASS (NOT RECOMMENDED: Hard to management, Do not use...)
     *  [ALBUM] - ITEM_ID(PK), AUTHOR, ISBN
     *  [MOVIE] - ITEM_ID(PK), ACTOR, DIRECTOR
     *  [BOOK] - ITEM_ID(PK), ARTIST
     * 
     * * (Good)
     * -> Subtype effective.
     * -> Not null constraint.
     * 
     * * (Bad)
     * -> When multi-search, using 'UNION' Query -> Low performence.
     * -> Hard to query with child tables.
     * -> Hard to change.
     * 
     */

    public static void main(String[] args) {
        log.info("- JPA proccess begin!");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // JoinedStretegy.work(em);
            // SingleTableStretegy.work(em);
            TablePerClassStretegy.work(em);

            tx.commit();
        }
        catch (Exception e) {
            tx.rollback();
            log.error("{}", e);
        }
        finally {
            em.close();
        }

        emf.close();
        log.info("- JPA process finished.");
    }
}
