package _11_concurrency;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Item82 {
    /**
     * [Item82] 스레드 안정성 수준을 문서화하라
     * 
     * [핵심]
     * 모든 클래스가 잔신의 스레드 안전성 정보를 명확히 문서화해야 한다.
     * 정확한 언어로 명확히 설명하거나 스레드 안전성 애너테이션을 사용할 수 있다.
     * synchronized 한정자는 문서화와 관련이 없다. 조건부 스레드 안전 클래스는
     * 메서드를 어떤 순서로 호출할 때 외부 동기화가 요구되고,
     * 그 때 어떤 락을 얻어야 하는지도 알려줘야 한다.
     * 무조건적 슷레드 안전 클래스를 작성할 때는 synchroznied 메서드가 아닌 비공개 락 객체를 사용하자.
     * 이렇게 해야 클라이언트나 하위 클래스에서 동기화 메커니즘을 깨뜨리는 걸 예방할 수 있고,
     * 필요하다면 다음에 더 정교한 동시성을 제어 메커니즘으로 재구현할 여지가 생긴다.
     * 
     * [스레드 안전성이 높은 순서]
     *  (안전) ┬ @Immutable [불변](String, Long, BigInteger) : 외부 동기화가 전혀 필요 없음
     *         | @ThreadSafe [무조건적 스레드 안전](AtomicLong, ConcurrentHashMap) : 값의 변화가 가능하지만 외부 동기화 없어도 안전
     *         | @ThreadSafe [조건부 스레드 안전](Collections.synchronized) : 일부 메서드 사용에 외부 동기화가 필요
     *         | @NotThreadSafe [스레드 안전하지 않음](ArrayList, HashMap) : 수정될 수 있고, 외부 동기화가 필수
     *  (위험) ┴ @NotThreadSafe [스레드 적대적] : 모든 메서드 호출을 외부 동기화로 감싸도 위험
     * 
     */

    public static void main(String[] args) {
        // [1] 조건부 스레드 안전
        // synchronizedMap이 반환한 맵의 컬렛녀 뷰를 순회하려면
        // 반드시 그 맵을 락으로 사용해 수동으로 동기화해야 한다.
        Map<String, Object> m = Collections.synchronizedMap(new HashMap<>());
        Set<String> s = m.keySet(); // 동기화 블록 밖에 있어도 된다

        // ...

        synchronized (m) { // s가 아닌 m을 사용해 동기화해야 한다
            for (String key : s) {
                // ...
            }
        }

        // 이대로 따르지 않으면 동작을 예측할 수 없다.
        // (차라리 java.util.concurrent를 사용하는게 날 듯 하다...)
    }

    // [2] 비공개 락 객체 관용구 - 서비스 거부 공격(클라이언트가 공개된 락을 오래 쥐고 놓아주지 않음)을 막아준다
    private final Object lock = new Object();
    // 락 필드는 final로 선언하는것이 좋다. 실수로라도 lock 필드가 교체된다면
    // 락이 해제되지 않는 대재앙이 펼쳐질 것이다.

    public void foo() {
        synchronized (lock) {
            // ...
        }
    }
}