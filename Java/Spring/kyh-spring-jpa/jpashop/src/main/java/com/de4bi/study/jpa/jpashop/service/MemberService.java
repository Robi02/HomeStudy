package com.de4bi.study.jpa.jpashop.service;

import java.util.List;

import com.de4bi.study.jpa.jpashop.domain.Member;
import com.de4bi.study.jpa.jpashop.repository.MemberRepository;
import com.de4bi.study.jpa.jpashop.repository.MemberRepositoryOld;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true) // SpringFramework을 쓸 때는 javax의 @Transactional을 사용하지 않도록 주의!
@RequiredArgsConstructor // final 필드만 생성자 생성
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원 가입.
     */
    @Transactional // 쓰기 작업이 있기 때문에 (readOnly = false)가 되어야 한다.
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 전체 회원 조회.
     */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    /**
     * 단건 조회.
     */
    public Member findOne(Long memberId) {
        //return memberRepository.findOne(memberId); // old
        return memberRepository.findById(memberId).get();
    }

    /**
     * 중복 회원 검증.
     */
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 회원정보 수정.
     */
    @Transactional
    public void update(Long id, String name) {
        // Member member = memberRepository.findOne(id); // old
        Member member = memberRepository.findById(id).get();
        member.setName(name);
    }
}
