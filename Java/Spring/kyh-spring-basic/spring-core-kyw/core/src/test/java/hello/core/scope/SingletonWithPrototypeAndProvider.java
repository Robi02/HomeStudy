package hello.core.scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

public class SingletonWithPrototypeAndProvider {
    
    @Test
    void prototypeFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean pb1 = ac.getBean(PrototypeBean.class);
        pb1.addCount();
        Assertions.assertThat(pb1.getCount()).isEqualTo(1);

        PrototypeBean pb2 = ac.getBean(PrototypeBean.class);
        pb2.addCount();
        Assertions.assertThat(pb2.getCount()).isEqualTo(1);
    }

    @Test
    void singletonClinetUsePrototype() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);
        
        // 싱글톤 빈 안의 프로토타입 빈은 싱글톤 빈
        // 특성 때문에 1회만 생성되지만, ObjectProvider를
        // 사용하면 싱글톤 빈 안에서도 프로토타입 빈을 생성할 수 있다.
        ClientBean cb1 = ac.getBean(ClientBean.class);
        int count1 = cb1.logic();
        Assertions.assertThat(count1).isEqualTo(1);

        ClientBean cb2 = ac.getBean(ClientBean.class);
        int count2 = cb2.logic();
        Assertions.assertThat(count2).isEqualTo(1); // !!
    }

    @Scope("singleton") // 생략 가능
    static class ClientBean {

        // @1
        // private final ObjectProvider<PrototypeBean> prototypeBeanProvider;

        // @2 :: JSR-330 자바 표준으로, build.gradle에 [implementation 'javax.inject:javax.inject:1'] 추가가 필요하다.
        private final Provider<PrototypeBean> prototypeBeanProvider;

        @Autowired // @2
        public ClientBean(Provider<PrototypeBean> prototypeBeanProvider) {
            this.prototypeBeanProvider = prototypeBeanProvider;
        }

        /* @Autowired // @1
        public ClientBean(ObjectProvider<PrototypeBean> prototypeBeanProvider) {
            this.prototypeBeanProvider = prototypeBeanProvider;
        }*/

        public int logic() {
            // PrototypeBean prototypeBean = prototypeBeanProvider.getIfAvailable(); // @1
            PrototypeBean prototypeBean = prototypeBeanProvider.get(); // @2
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }

    @Scope("prototype")
    static class PrototypeBean {
        
        private int count = 0;

        public void addCount() {
            ++count;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init " + this);
        }

        @PreDestroy
        public void destory() {
            System.out.println("PrototypeBean.destory " + this);
        }
    }
}
