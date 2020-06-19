package _9_generic_programing_principle;

public class Item68 {
    /**
     * [Item68] 일반적으로 통용되는 명명 규칙을 따르라
     * 
     * [핵심]
     * 표준 명명 규칙을 체화하여 자연스럽게 베어 나오도록 하자.
     * 철자 규칙은 직관적이라 모호한 부분이 적은 데 반해, 문법 규칙은 더 복잡하고 느슨하다.
     * 자바 언어 명세[JLS, 6.1]의 말을 인용하자면 "오랫동안 따라온 규칙과 충돌한다면
     * 그 규칙을 맹종해서는 안 된다." 상식이 이끄는 대로 따르자.
     * 
     * [식별자 타입]        | [예]
     * ---------------------+------------------------------------------------------------------------------
     * 패키지와 모듈        | org.junit.jupiter.api, com.google.common.collect
     * 클래스와 인터페이스  | Stream, FutureTask, LinkedHashMap, HttpClient
     * 메서드와 필드        | remove, groupingBy, getCrc
     * 상수 필드            | MIN_VALUE, NEGATIVE_INFINITY
     * 지역변수             | i, denom, houseNum
     * 타입 매개변수        | T, E, K, V, X, R, U, V, T1, T2 (타입,컬렉션 원소타입,맵키,맵값,예외,메서드 반환타입)
     * 
     * [문법 규칙]
     * 1. 패키지 -> 딱히 없음
     * 2. 객체 생성가능한 클래스 -> 단수 명사나 명사구 (Thread, PriorityQueue, ChessPiece, ...)
     * 3. 객체 생성불가한 클래스 -> 복수형 명사 (Collectors, Collections, ...)
     * 4. 인터페이스 이름은 클래스와 같거나 -able/-ible로 끝냄 -> (Runnable, Iterable, Accessible, ...)
     * 5. 애너테이션은 지배적인 규칙이 없음 -> (@BindingAnnotation, @Intect, @Singleton, ...)
     * 6. 동작을 수행하는 메서드 -> 동사나 목적어를 포함한 동사구 (append, drawImage, ...)
     * 7. boolean 값을 반환하는 메서드 -> is나 has로 시작하고 명사나 명사구, 혹은 형용사 단어나 구로 끝냄
     *    (isDigit, isProbablePrime, isEmpty, isEnabled, hasSiblings, ...)
     * 8. 반환 타입이 boolean이 아닌 경우나 인스턴스의 속성을 반환하는 경우
     *    -> 보통명사, 명사구, 혹은 get으로 시작하는 동사구 (size, hashCode, getTime, ...)
     * 9. 객체의 타입을 바꿔서, 다른 타입의 또 다른 객체를 반환하는 인스턴스 메서드
     *    -> toType (toString, toArray, ...)
     * 10. 객체의 내용을 다른 뷰로 보여주는 메서드 -> asType (asList, ...)
     * 11. 객체의 값을 기본 타입 값으로 반환하는 메서드 -> typeValue (intValue, ...)
     * 12. 정적 팩터리 -> (form, of, valueOf, instance, getInstance, newInstance, getType, newType)
     * 13. 필드와 지역변수는 위 규칙에 비해 비교적 느슨
     * 
     */
}