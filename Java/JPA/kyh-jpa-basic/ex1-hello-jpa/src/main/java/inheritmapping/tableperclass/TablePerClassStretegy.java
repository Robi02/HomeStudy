package inheritmapping.tableperclass;

import javax.persistence.EntityManager;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TablePerClassStretegy {

    public static void work(EntityManager em) {

        Movie movie = new Movie();
        movie.setDirector("Bong June Ho");
        movie.setActor("Kim Tae Hwe");
        movie.setName("Gone With The Wind");
        movie.setPrice(10000);
        em.persist(movie);
        em.flush();
        em.clear();

        // No problems
        // Movie findMovie = em.find(Movie.class, movie.getId());
        // log.info("findMovie : " + findMovie.getName());

        // Has problems (Long SQL query -> Check log: hibernate were using 'union' syntax)
        Item findItem = em.find(Item.class, movie.getId());
        log.info("findItem : " + findItem.getName());

        /**
         Hibernate: 
            select
                item0_.ITEM_ID as ITEM_ID1_2_0_,
                item0_.name as name2_2_0_,
                item0_.price as price3_2_0_,
                item0_.artist as artist1_0_0_,
                item0_.actor as actor1_9_0_,
                item0_.director as director2_9_0_,
                item0_.author as author1_1_0_,
                item0_.isbn as isbn2_1_0_,
                item0_.clazz_ as clazz_0_ 
            from
                ( select
                    ITEM_ID,
                    name,
                    price,
                    artist,
                    null as actor,
                    null as director,
                    null as author,
                    null as isbn,
                    1 as clazz_ 
                from
                    Album 
                union
                all select
                    ITEM_ID,
                    name,
                    price,
                    null as artist,
                    actor,
                    director,
                    null as author,
                    null as isbn,
                    2 as clazz_ 
                from
                    Movie 
                union
                all select
                    ITEM_ID,
                    name,
                    price,
                    null as artist,
                    null as actor,
                    null as director,
                    author,
                    isbn,
                    3 as clazz_ 
                from
                    Book 
            ) item0_ 
        where
            item0_.ITEM_ID=?
        */
    }
}