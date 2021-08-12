package com.de4bi.study.jpa.jpashop.service;

import javax.transaction.Transactional;

import com.de4bi.study.jpa.jpashop.domain.Member;
import com.de4bi.study.jpa.jpashop.repository.MemberRepositoryOld;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional // 같은 영속성 콘텍스트를 사용하기 위함
public class MemberServiceTest {
    
    @Autowired MemberService memberService;
    @Autowired MemberRepositoryOld memberRepository;
    
    @Test
    @Rollback(false) // @Rollback(false)를 빼버리면 sql로그에서 insert되는것을 확인할 수 없다.
    public void 회원가입() throws Exception {
        // given
        Member member = new Member();
        member.setName("lee");
        
        // when
        Long memberId = memberService.join(member);
 
        // then
        Assert.assertEquals(member, memberRepository.findOne(memberId));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_에러() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("lee");

        Member member2 = new Member();
        member2.setName("lee");

        // when
        memberService.join(member1);
        memberService.join(member2); // 중복 회원: 예외가 발생해야 함

        // then
        Assert.fail("중복 회원 에러가 발생해야 함.");
    }
}
