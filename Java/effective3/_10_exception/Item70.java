package _10_exception;

public class Item70 {
    /**
     * [Item70] 복구할 수 있는 상황에서는 검사 예외를, 프로그래밍 오류에는 런타임 오류를 사용하라
     * 
     * [핵심]
     * 복구할 수 있는 상황이면 검사 예외를, 프로그래밍 오류라면 비검사 예외를 던지자.
     * 확실하지 않다면 비검사 예외를 던지자. 검사 예외도 아니고 런타임 예외도 아닌
     * throwable은 정의하지도 말자. 검사 예외라면 복구에 필요한 정보를 알려주는 메서드도 제공하자.
     * 
     * - 자바 Throwable은 (1)검사 예외, (2)런타임 예외, (3)에러 세 가지를 제공한다.
     * 1. 호출하는 쪽에서 복구하리라 여겨지는 상황 -> 검사 예외
     * 2. 프로그래밍 오류 -> 런타임 예외
     * 3. AssertionError를 제외하고 사용하지 말자
     * 
     */
}