package com.de4bi.study.jpa.jpashop;

import com.de4bi.study.jpa.jpashop.domain.Member;
import com.de4bi.study.jpa.jpashop.repository.MemberRepositoryOld;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTests {

    @Autowired MemberRepositoryOld memberRepository;

    @Test
    @Transactional // @Test 어노테이션이 있는 경우 rollback을 수행함
    // @Rollback(false) // 이 어노테이션을 키면 @Test환경에서도 rollback을 수행하지 않음
    public void testMember() throws Exception {
        // given
        Member member = new Member();
        member.setName("memberA");
        
        // when
        Long savedId = member.getId();
        memberRepository.save(member);
        Member findMember = memberRepository.findOne(savedId);

        // then
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getName()).isEqualTo(member.getName());
        Assertions.assertThat(findMember).isEqualTo(member);
    }
}
