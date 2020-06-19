package _3_common_methods;

import java.util.Objects;

public class Item11 {
   /**
     * [Item11] equals를 재정의하려거든 hashCode도 재정의하라
     * 
     * [핵심]
     * equals를 재정의할 때는 hashCode도 반드시 재정의해야 한다.
     * 그렇지 않으면 프로그램이 제대로 동작하지 않을 것이다.
     * 재정의한 hashCode는 Object의 API 문서에 기술된 일반 규약을 따라야 하며,
     * 서로 다른 인스턴스라면 되도록 해시코드도 서로 다르게 구현해야 한다.
     * 이렇게 구현하기가 어렵지는 않지만 조금 따분한 일이긴 하다.
     * AutoValue 프레임워크를 사용하면 멋진 equals와 hashCode를 자동으로 만들 수 있다.
     * 일부 IDE들도 이런 기능을 일부 제공한다.
     * 
     * [재정의하지 않을시의 문제점]
     * 해시맵등 hashCode값을 사용하는 곳에서 O(1)의 효율이 선형적으로 O(N)으로 나빠질 수 있다.
     * 
     * [좋은 해시코드를 작성하는 요령]
     * 1. int 변수 result를 선언한 후 값 c로 초기화한다. 이때 c는 해당 객체의 첫 번째
     *    핵심 필드를 단계 2.a 방식으로 계산한 해시코드다. (여기서 핵심 필드란 equals 비교에 사용되는 필드를 말함)
     * 2. 해당 객체의 나머지 핵심 필드 f 각가에 대해 다음 작업을 수행한다.
     *   a) 해당 필드의 해시코드 c를 계산한다.
     *     i) 기본 타입 필드라면, Type.hashCode(f)를 수행한다. (여기서 Type은 해당 기본 타입의 박싱 클래스임)
     *     ii) 참조 타입 필드면서 이 클래스의 equals메서드가 이 필드의 equals를 재귀적으로 호출해 비교한다면,
     *         이 필드의 hashCode를 재귀적으로 호출한다. 계산이 더 복잡해질 것 같으면,
     *         이 필드의 표준형을 만들어 그 표준형의 hashCode를 호출한다. 필드의 값이 null이면 0을 사용한다.
     *     iii) 필드가 배열이라면, 해깃ㅁ 원소 각각을 별도 필드처럼 다룬다. 이상의 규칙을 재귀적으로 적용해
     *          각 핵심 원소의 해시코드를 계산한 다음, 단계 2.b 방식으로 갱신한다.
     *          배열에 핵심 원소가 하나도 없다면 단순히 상수(0을 추천)를 사용한다.
     *          모든 원소가 핵심 원소라면 Arrays.hashCode를 사용한다.
     *   b) 단계 2.a에서 계산한 해시코들 c로 result를 갱신한다. (result = 32 * result + c;)
     * 3. result를 반환한다.
     * 
     **/

    // 전형적인 equals 메서드의 예
    public static class PhoneNumber {
        private final short areaCode, prefix, lineNum;

        public PhoneNumber(int areaCode, int prefix, int lineNum) {
            this.areaCode = rangeCheck(areaCode, 999, "지역코드");
            this.prefix = rangeCheck(prefix, 999, "프리픽스");
            this.lineNum = rangeCheck(lineNum, 9999, "가입자 번호");
        }

        private static short rangeCheck(int val, int max, String arg) {
            if (val < 0 || val > max) {
                throw new IllegalArgumentException(arg + ": " + val);
            }

            return (short) val;
        }

        @Override public boolean equals(Object o) {
            if (o == this) {
                return true;
            }

            if (!(o instanceof PhoneNumber)) {
                return false;
            }

            PhoneNumber pn = (PhoneNumber) o;

            return pn.lineNum == lineNum && pn.prefix == prefix && pn.areaCode == areaCode;
        }

        @Override public int hashCode() {
            // 31을 사용하는 이유는 홀수인 소수이기 때문이다.
            // 짝수를 사용하면 결과값이 무조건 짝수로 해시 절반이 사라진다.
            // (Tip. '31 * i' 를 '(i << 5) - i' 로 대체할수도 있는데, 일부 VM은 자동으로 최적화를 수행한다.)
            int result = Short.hashCode(areaCode);
            result = 31 * result + Short.hashCode(prefix);
            result = 31 * result + Short.hashCode(lineNum);
            return result;

            // Objects 클래스를 사용할 수도 있지만,
            // 위 방식보다 속도는 느려진다.
            // 성능에 민감하지 않은 상황이라면 사용해도 좋다.
            
            // Objects.hash(lineNum, prefix, areaCode);

            // 클래스가 불변이고 해시코드를 계산하는 비용이 크다면,
            // 캐싱 + 지연 초기화하는 방법을 고려해야 한다.
            //
            // private int hashCode;
            //
            // @Override pubilc int hashCode() {
            //     int result = hashCode;
            //
            //     if (result == 0) {
            //        result = 31 * result + Short.hashCode(prefix);
            //        result = 31 * result + Short.hashCode(lineNum);
            //        hashCode = result;
            //     }
            //
            //     return result;
            // }
            
            // 해시 충돌이 더욱 적은 방법을 꼭 써야한다면 구아바(Guava)의 해싱을 참고하는것이 제일 좋다.
            // -> com.google.common.hash.Hashing
        }
    }

    public static void main(String[] args) {
        
    }
}