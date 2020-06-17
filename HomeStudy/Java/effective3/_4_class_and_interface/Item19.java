package _4_class_and_interface;

import java.time.Instant;

public class Item19 {
    /**
     * [Item19] 상속을 고려해 설계하고 문서화하라. 그러지 않았다면 상속을 금지하라
     * 
     * [핵심]
     * 상속용 클래스를 설계하기란 결코 만만치 않다. 클래스 내부에서 스스로를 어떻게 사용하는지
     * (자기사용 패턴) 모두 문서로 남겨야 하며, 일단 문서화한 것은 그 클래스가 쓰이는 한 반드시 지켜야 한다.
     * 그러지 않으면 그 내부 구현 방식을 믿고 활용하던 하위 클래스를 오동작하게 만들 수 있다.
     * 다른 이가 효율 좋은 하위 클래스를 만들 수 있도록 일부 메서드를 protected로 제공해야 할 수도 있다.
     * 그러므로 클래스를 확장할 명확한 이유가 떠오르지 않으면 상속을 금지하는 편이 나을 것이다.
     * 상속을 금지하려면 클래스를 final로 선언하거나 생성자 모두를 외부에서 접근할 수 없도록 만들면 된다.
     * 
     */

    public static class Super {
        // 잘못된 예 - 생성자가 재정의 가능 메서드를 호출한다
        public Super() {
            overrideMe();
        }

        public void overrideMe() {}
    }

    public static final class Sub extends Super {
        // 초기화되지 않은 final 필드. 생성자에서 초기화된다.
        private final Instant instant;
        
        Sub() {
            instant = Instant.now();
        }

        // 재정의 가능 메서드. 상위 클래스의 생성자가 호출한다!
        @Override public void overrideMe() {
            System.out.println("I'm not safe... : " + instant);
        }
    }

    public static class SafeSuper {
        // 괜찮은 예 - 생성자가 도우미 메서드를 호출해서 @Override에 안전해진다
        public SafeSuper() {
            overrideMeSafe();
        }

        public void overrideMe() { overrideMeSafe(); }

        private void overrideMeSafe() {
            // ...
        }
    }

    public static final class SafeSub extends SafeSuper {
        // 초기화되지 않은 final 필드. 생성자에서 초기화된다.
        private final Instant instant;
        
        SafeSub() {
            instant = Instant.now();
        }

        @Override public void overrideMe() {
            System.out.println("I'm safe! : " + instant);
        }
    }

    public static void main(String[] args) {
        // 디버그 모드로 돌려보자
        // 출력 결과에 "null"과 "현재 시간"이 나온다.
        // Sub() 에서 인스턴스가 생성자로 초기화 되기도 전에
        // Super() 에서 Sub()의 overrideMe() 를 호출해서 이런 일이 발생했다.
        Sub sub = new Sub();
        sub.overrideMe();

        // 비슷하게, 새로운 객체를 생성하는 clone()과 readObject() 모두
        // 직접적으로든 간접적으로든 재정의 가능 메서드를 호출해서는 안 된다.

        // 헬퍼 메서드를 사용하는 SafeSub 클래스같은 설계를 추천한다
        SafeSub safeSub = new SafeSub();
        safeSub.overrideMe();
    }
}