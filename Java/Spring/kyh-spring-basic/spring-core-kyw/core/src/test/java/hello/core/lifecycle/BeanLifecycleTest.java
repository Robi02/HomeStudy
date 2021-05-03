package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

public class BeanLifecycleTest {
    
    /**
     * - 스프링 빈의 이벤트 라이프사이클
     * 1. 스프링 컨테이너 생성
     * 2. 스프링 빈 생성(ConstructorInjection은 이 단계)
     * 3. 의존관계 주입 (SetterInjection, FieldInjection)
     * 4. 초기화 콜백
     * 5. 사용
     * 6. 소멸 전 콜백
     * 7. 스프링 종료
     * 
     * [Tip] 객체의 초기화 작업이 무거울 수록(커넥션 생성 등) 생성과 초기화를 분리하는것이 좋다.
     * 
     * - 스프링의 3가지 빈 생명주기 콜백 지원
     * 1. 인터페이스: InitializingBean, DisposableBean -> 단점: 스프링 전용 인터페이스이기에 초기화/소멸 메서드 이름 변경 불가, 외부 라이브러리에 적용 불가.
     * 2. @Bean설정 정보에 초기화 메서드, 종료 메서드 지정 -> destoryMethod의 기본값은 "(inffered)"로, close(), shutdown()등의 이름을 가진 메서드를 자동으로 추적해주는 기능이 있다.
     * 3. 어노테이션: @PostConstruct, @PreDestory -> 장점: javax클래스로 JSR-250이라는 자바 표준이다. 단점: 외부 라이브러리에 적용할 수 없다.
     * 
     * -> (1)은 스프링 초기에(2003년) 나온 방법으로 지금은 거의 사용하지 않음.
     * -> (2)는 외부 라이브러리의 초기화에 사용할 때 유리하다.
     * -> 대부분 경우 스프링 공식 권고인 (3)을 사용하는것을 권장한다.
     */

    @Test
    public void lifecycleTest() {
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifecycleTest.class);
        NetworkClient nc = ac.getBean(NetworkClient.class);
        ac.close();
    }

    static class LifecycleTest {
        //@Bean(initMethod = "init", destroyMethod = "close") // @2
        @Bean
        public NetworkClient networkClient() {
            //NetworkClient nc = new NetworkClient(); // @0
            //nc.setUrl("https://de4bi.com");
            //return nc;
            return new NetworkClient();
        }
    }
}
