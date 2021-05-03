package _11_concurrency;

public class Item83 {
    /**
     * [Item83] 지연 초기화는 신중히 사용하라
     * 
     * [핵심]
     * ※ 대부분의 상황에서 일반적인 초기화가 지연 초기화보다 낫다.
     * 성능 때문에 혹은 위험한 초기화 순환을 막기 위해 꼭 지연 초기화를 써야 한다면
     * 올바른 지연 초기화 기법을 사용하자. 인스턴스 필드에는 이중검사 관용구를,
     * 정적 필드에는 지연 초기화 홀더 클래스 관련 관용구를 사용하자.
     * 반복해 초기화해도 괜찮은 인스턴스 필드에는 단일검사 관용구도 고려 대상이다.
     * 
     */

    // [1-1] 인스턴스 필드를 초기화하는 일반적인 방법
    private final Integer field = computeFieldValue();

    private static Integer computeFieldValue() {
        return 1;
    }

    // [1-2] 지연 초기화를 사용하는 초기화 방법
    private Integer fieldLazy;

    private synchronized Integer getField() {
        if (this.fieldLazy == null) {
            this.fieldLazy = computeFieldValue();
        }

        return this.fieldLazy;
    }

    // [1-3] 지연 초기화 홀더 클래스 사용
    // 성능 때문에 정적 필드를 지연 초기화해야 한다면
    // 지연 초기화 홀더 클래스 관용구를 사용하는것을 권장한다.
    private static class FieldHolder {
        static final Integer field = computeFieldValue();
    }

    // getField2() 가 처음 호출되는 순간 FieldHolder.field가 처음 읽히면서 초기화를 촉발한다!
    private static Integer getField2() {
        return FieldHolder.field;
    }
    
    // getField2()에 synchronized가 없으니 성능이 느려질 거리가 전혀 없다!
    // 최초 초기화 후, VM이 동기화 코드를 제거하여 2차 초기화등이 이루어지지 않는다.

    // [2] 성능 때문에 지연 초기화해야 한다면 이중검사(double-check) 관용구를 쓸 수 있다
    private volatile Integer fieldDouble;
    // 필드 초기화 후 동기화되지 않으므로 volatile 키워드가 필수이다

    private Integer getField3() {
        Integer result = this.fieldDouble;
        if (result != null) { // 첫 번째 검사시에는 락 검사를 하지 않는다
            return result;
        }

        synchronized (this) {
            if (this.fieldDouble == null) { // 두 번째 검사시에는 락을 사용한다
                this.fieldDouble = computeFieldValue();
            }

            return this.fieldDouble;
        }
    }

    // [3] 중복 초기화가 허용된다면 단일검사(single-check) 관용구를 쓸 수 있다
    private volatile Integer fieldSingle;

    private Integer getField4() {
        Integer result = this.fieldSingle;
        if (result == null) {
            this.fieldSingle = result = computeFieldValue();
        }
        
        return result;
    }
}