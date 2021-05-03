package _4_class_and_interface;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Item15 {
    /**
     * [Item15] 클래스와 맴버의 접근 권한을 최소화하라
     * 
     * [핵심]
     * 프로그램 요소의 접근성은 가능한 한 최소한으로 하라. 꼭 필요한 것만 골라 최소한의 public API를 설계하자.
     * 그 외에는 클래스 ,인터페이스, 멤퍼가 의도치 않게 API로 공개되는 일이 없도록 해야 한다.
     * public 클래스는 상수용 public static final 필드 외에는 어떠한 public 필드도 가져서는 안 된다.
     * public static final 필드가 참조하는 객체가 불변인지 확인하라.
     * 
     */
    
    public static class A {
        // [a] 이 코드는 위험하다. 배열을 public static final로 선언하면 외부에서 변경할 수 있다.
        public static final C[] C_ARY = { new C(1), new C(2) };

        // [a] 위 코드를 안전하게 사용하려면 다음과 같은 방식을 권장한다.
        private static final C[] C_ARY_SAFE = { new C(1), new C(2) }; // [a] prvate 접근 지정자 사용
        public static final List<C> C_LIST_SAFE = Collections.unmodifiableList(Arrays.asList(C_ARY_SAFE));

        public static class AA {
            // [a] 외부의 다른 클래스에서 이런 메서드가 있다면...
            public static void dangerousArray() {
                C_ARY[0] = new C(99); // [a] C_ARY는 final이지만, 내부 원소는? 참조를 변경해버릴 수 있다!
            }

            // [a] 외부에서 접근하더라도 변경할 수 없다!
            public static void safeArray() {
                // C_ARY_SAFE[0] = new C(99); // [a] compile error (지금은 inner-class여서 접근이 가능하지만 다른 클래스라면 cannot visible 접근 불가)
                C_LIST_SAFE.set(0, new C(99)); // [a] throws java.lang.UnsupportedOperationException
            }
        }

        // [a] 아니면 이 방식도 있다 (방어적 복사)
        private static final C[] C_ARY_SAFE2 = { new C(1), new C(2) }; // [a] prvate 접근 지정자 사용
        
        public static final C[] values() {
            return C_ARY_SAFE2.clone(); // [a] 방어적 복사는 똑같이 배열을 반환하는것은 장점이지만, clone()에 따른 부하는 피할 수 없다...
        }

        // ---------------------------------------------------------------------------------

        private int A; // [b]

        public static void accessTest() {
            B b = new B();
            int bb = b.B; // [b] 하위 클래스의 private에는 접근할 수 있다.

            C c = new C(1);
            int cc = c.C; // [b] 같은 패키지내의 package-private에도 접근할 수 있다.
        }
    }

    public static class B extends A {
        private int B;

        public static void accessTest() {
            C c = new C(2);
            int cc = c.C; // [b] 같은 패키지내의 package-private에도 접근할 수 있다.
        }
    }

    public static class C {
        int C;

        public C(int c) {
            this.C = c;
        }
    }

    public static void main(String[] args) {
        A.AA.safeArray();
    }        
}