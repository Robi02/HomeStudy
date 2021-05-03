package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import hello.core.AppConfig;
import hello.core.member.MemberService;

public class SingletonTest {
    
    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    void springContainer() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberService ms1 = ac.getBean("memberService", MemberService.class);
        MemberService ms2 = ac.getBean("memberService", MemberService.class);

        System.out.println("ms1 : " + ms1);
        System.out.println("ms2 : " + ms2);

        Assertions.assertThat(ms1).isSameAs(ms2);
    }
}
