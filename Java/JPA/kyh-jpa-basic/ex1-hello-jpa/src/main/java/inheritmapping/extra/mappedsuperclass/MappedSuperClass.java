package inheritmapping.extra.mappedsuperclass;

import javax.persistence.EntityManager;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MappedSuperClass {

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
        Movie findMovie = em.find(Movie.class, movie.getId());
        log.info("findMovie : " + findMovie.getName());
    }
}