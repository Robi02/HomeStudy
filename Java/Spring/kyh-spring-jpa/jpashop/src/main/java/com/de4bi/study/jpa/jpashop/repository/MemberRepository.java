package com.de4bi.study.jpa.jpashop.repository;

import java.util.List;

import javax.persistence.EntityManager;

import com.de4bi.study.jpa.jpashop.domain.Member;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    
//    @PersistenceContext // SpringDataJpa를 사용하면 EntityManager를 @Autowired로 주입이 가능해서, @PersistenceContext생략 후 생성자 주입방식으로 통일할 수 있다.
    private final EntityManager em;

//    @PersistenceUnit
//    private EntityManagerFactory emf; // EMF를 직접 주입받을수도 있다.

    public void save(Member member){
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name).getResultList();
    }
}
