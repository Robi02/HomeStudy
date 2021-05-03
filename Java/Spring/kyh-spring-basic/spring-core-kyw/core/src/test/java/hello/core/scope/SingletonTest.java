package hello.core.scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

public class SingletonTest {

    @Test
    void singletonBeanFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SingletonBean.class);
        SingletonBean sb1 = ac.getBean(SingletonBean.class);
        SingletonBean sb2 = ac.getBean(SingletonBean.class);

        System.out.println(">> sb1 = " + sb1);
        System.out.println(">> sb2 = " + sb2);

        Assertions.assertThat(sb1).isSameAs(sb2);

        ac.close();
    }

    @Scope("singleton")
    static class SingletonBean {
        
        @PostConstruct
        public void init() {
            System.out.println(">>> SingletonBean.init()");
        }

        @PreDestroy
        public void distroy() {
            System.out.println(">>> SingletonBean.destroy()");
        }
    }
}