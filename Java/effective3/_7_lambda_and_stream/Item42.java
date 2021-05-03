package _7_lambda_and_stream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

public class Item42 {
    /**
     * [Item42] 익명 클래스보다는 람다를 사용하라
     * 
     * [핵심]
     * 자바가 8로 판올림되면서 작은 함수 객체를 구현하는 데 적합한 람다가 도입되었다.
     * 익명 클래스는 (함수형 인터페이스가 아닌) 타입의 인스턴스를 만들 때만 사용하라.
     * 람다는 작은 함수 객체를 아주 쉽게 표현할 수 있어 (이전 자바에서는 실용적이지 않던)
     * 함수형 프로그래밍의 지평을 열었다.
     * 
     */

    public static void main(String[] args) {
        // [1-1] 익명 클래스의 인스턴스를 함수 객체로 사용 - 낡은 기법!
        List<String> list = new ArrayList<>();
        Collections.sort(list, new Comparator<String>() {
            public int compare(String s1, String s2) {
                return Integer.compare(s1.length(), s2.length());
            }
        });

        // [1-2] 람다식을 함수 객체로 사용 - 익명 클래스 대체
        Collections.sort(list, (s1, s2) -> Integer.compare(s1.length(), s2.length()));

        // 컴파일러가 컴파일 타임에 s1, s2 타입을 추론해 주었다.
        // 타입을 명시해야 코드가 더 명확할 때만 제외하고는,
        // 람다의 모든 매개변수 타입을 생략하는걸 추천한다.

        // [1-3] 람다 자리에 비교자 생성 메서드를 사용할수도 있다
        Collections.sort(list, Comparator.comparingInt(String::length));

        // 람다는 이름이 없고 문서화를 할 수 없기에, 코드 자체로 동작이
        // 명확히 설명되지 않거나 코드 줄 수가 많아지면 람다를 쓰지 말아야 한다.
        // (길어야 3줄이고, 한줄로 끝낼 때 가장 좋다)

        // 또한, 람다도 익명 클래스처럼 직렬화 형태가 JVM별로 다를 수 있어서
        // 직렬화하는 일은 극히 삼가야 한다.
    }

    // [2] 함수 객체(람다)를 인스턴스 필드에 저장해 상수별 동작을 구현한 열거 타입
    public static enum Operation {
        PLUS("+", (x, y) -> x + y),
        MINUS("-", (x ,y) -> x - y),
        TIMES("*", (x, y) -> x * y),
        DIVIDE("/", (x, y) -> x / y);

        private final String symbol;
        private final DoubleBinaryOperator op;

        Operation(String symbol, DoubleBinaryOperator op) {
            this.symbol = symbol;
            this.op = op;
        }

        @Override public String toString() { return symbol; }

        public double apply(double x, double y) {
            return op.applyAsDouble(x, y);
        }
    }
}