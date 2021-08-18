package com.de4bi.study.jpa.jpashop.repository;

import java.util.List;

import com.de4bi.study.jpa.jpashop.domain.Member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepositorySDJ extends JpaRepository<Member, Long> {
    
    // "select m from Member m where m.name = ?" 을 자동으로 짜버린다!
    List<Member> findByName(String name);
}
