package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderServiceImpl;

public class ConfigurationSingletonTest {
    
    @Test
    void configurationTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberServiceImpl ms = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl os = ac.getBean("orderService", OrderServiceImpl.class);

        MemberRepository mr1 = ac.getBean("memberRepository", MemberRepository.class);
        MemberRepository mr2 = ms.getMemberRepository();
        MemberRepository mr3 = os.getMemberRepository();

        System.out.println("mr1 : " + mr1);
        System.out.println("mr2 : " + mr2);
        System.out.println("mr3 : " + mr3);

        Assertions.assertThat(mr1).isSameAs(mr2).isSameAs(mr3);
    }

    @Test
    void configurationDeep() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig bean = ac.getBean(AppConfig.class);

        System.out.println("bean = " + bean.getClass());
    }
}
