package hello.core.scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

public class PrototypeTest {
    
    @Test
    void PrototypeBeanFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean pb1 = ac.getBean(PrototypeBean.class);
        PrototypeBean pb2 = ac.getBean(PrototypeBean.class);

        System.out.println(">> pb1 = " + pb1);
        System.out.println(">> pb2 = " + pb2);

        Assertions.assertThat(pb1).isNotSameAs(pb2);

        ac.close();
    }

    @Scope("prototype")
    static class PrototypeBean {
        
        @PostConstruct
        public void init() {
            System.out.println(">>> PrototypeBean.init()");
        }

        @PreDestroy
        public void distroy() {
            System.out.println(">>> PrototypeBean.destroy()");
        }
    }
}
