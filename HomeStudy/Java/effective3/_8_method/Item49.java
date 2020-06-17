package _8_method;

import java.math.BigInteger;
import java.util.Objects;

public class Item49 {
    /**
     * [Item49] 매개변수가 유효한지 검사하라
     * 
     * [핵심]
     * 메서드나 생성자를 작성할 때면 그 매개변수들에 어떤 제약이 있을지 생각해야 한다.
     * 그 제약들을 문서화하고 메서드 코드 시작 부분에서 명시적으로 검사해야 한다.
     * 이런 습관을 반드시 기르도록 하자. 그 노력은 유효성 검사가 실제 오류를 처음 걸러낼 때
     * 충분히 보상받을 것이다.
     * 
     */

    // [1] 적절하게 메서드의 매개변수 제약을 표현하는 예시
    /**
     * (현재 값 mod m) 값을 반환한다. 이 메서드는
     * 항상 음이 아닌 BigInteger를 반환한다는 점에서 remainder 메서드와 다르다.
     * 
     * @param exp 지수
     * @param m 계수(양수여야 한다.)
     * @return 현재 값 mod m
     * @throws ArithmeticException m이 0보다 작거나 같으면 발생한다.
     */
    public BigInteger mod(BigInteger exp, BigInteger m) {
        if (m.signum() <= 0) {
            throw new ArithmeticException("계수 (m)은 양수여야 합니다. " + m);
        }

        return m.modPow(exp, m);
    }

    public static void main(String[] args) {
        // [2] Java7에 추가된 java.util.Objects.requireNonNull을 사용해 보자
        String val = null;
        try {
            String param = null;
            val = Objects.requireNonNull(param, "param이 null입니다!");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            System.out.println(val);
        }

        // [3]
        sort(null, 0, 1);
    }

    // [3] 단언문(assert)을 사용한 재귀 정렬용 private 도우미 함수
    private static void sort(long a[], int offset, int length) {
        assert a != null;
        assert offset >= 0 && offset <= a.length;
        assert length >= 0 && length <= a.length - offset;

        // 연산 수행...

        // 단언문들은 자신이 단언한 조건이 무조건 참이라고 선언한다.
        // 실패한 경우 AssertionError를 던진다.
        // 런타임에 -ea 혹은 --enableassertions 플래그를 설정하지 않으면 아무 일이 발생하지 않고
        // 성능 저하도 발생하지 않는다.
    }
}