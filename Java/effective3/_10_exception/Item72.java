package _10_exception;

public class Item72 {
    /**
     * [Item72] 표준 예외를 사용하라
     * 
     * [핵심]
     * Exception, RuntimeException, Throwable, Error는 직접 재사용하지 말자.
     * 아래 표를 참고하여 각 상황에 맞는 예외를 던지도록 하자.
     * 부가적인 정보를 위해 위 예외를 상속하여 확장 예외를 만들어도 좋지만,
     * 가능한 표준 예외를 사용하여 메모리적인 측면이나 다른 개발자를 위한
     * 관용/범용적인 부분을 지켜줄 수 있도록 하자.
     * 
     * [널리 재사용되는 예외들]
     * 1. IllegalArgumentException        : 허용하지 않는 값이 인수로 건네졌을 때 (null은 따로 NullPointerException으로 처리)
     * 2. IllegalStateException           : 객체가 메서드를 수행하기에 적절하지 않은 상태일 때
     * 3. NullPointerException            : null을 허용하지 않는 메서드에 null을 건넸을 때
     * 4. IndexOutOfBoundsException       : 인덱스가 범위를 넘어섰을 때
     * 5. ConcurrentModificationException : 허용하지 않는 동시 수정이 발견됐을 때
     * 6. UnsupportedOperationException   : 호출한 메서드를 지원하지 않을 때
     * 
     * [State vs Argument]
     * 인수 값이 무엇이었든 어차피 실패했을 거라면 IllegalStateException을,
     * 그렇지 않으면 IllegalArgumentException을 추천한다.
     * 
     */
}