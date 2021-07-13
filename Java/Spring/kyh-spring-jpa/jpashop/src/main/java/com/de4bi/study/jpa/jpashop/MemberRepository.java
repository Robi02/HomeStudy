package com.de4bi.study.jpa.jpashop;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.de4bi.study.jpa.jpashop.domain.Member;

import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {
    
    @PersistenceContext
    private EntityManager em;

    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    public Member find(Long id) {
        return em.find(Member.class, id);
    }
}