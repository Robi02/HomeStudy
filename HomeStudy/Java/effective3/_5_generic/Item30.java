package _5_generic;

import java.util.Collection;
import java.util.Objects;
import java.util.function.UnaryOperator;

public class Item30 {
    /**
     * [Item30] 이왕이면 제네릭 메서드로 만들라
     * 
     * [핵심]
     * 제네릭 타입과 마찬가지로, 클라이언트에서 입력 매개변수와 반환값을 명시적으로 형변환해야
     * 하는 메서드보다 제네릭 메서드가 더 안전하며 사용하기도 쉽다.
     * 타입과 마찬가지로, 메서드도 형변환 없이 사용할 수 있는 편이 좋으며,
     * 많은 경우 그렇게 하려면 제네릭 메서드가 되어야 한다.
     * 역시 타입과 마찬가지로, 형변환을 해줘야 하는 기존 메서드는 제네릭하게 만들자. (Item26)
     * 기존 클라이언트는 그대로 둔 채 새로운 사용자의 삶을 훨신 편하게 만들어줄 것이다.
     * 
     */
    
    // [1] 제네릭 싱글턴 팩터리 패턴
    private static UnaryOperator<Object> IDENTITY_FN = (t) -> t;

    @SuppressWarnings("unchecked")
    public static <T> UnaryOperator<T> identityFunction() {
        return (UnaryOperator<T>) IDENTITY_FN;
    }

    public static void main(String[] args) {
        String[] strings = { "삼베", "대마", "나일론" };
        UnaryOperator<String> sameString = identityFunction();
        for (String s : strings) {
            System.out.println(sameString.apply(s));
        }

        Number[] numbers = { 1, 2.0, 3L, 4.0f };
        UnaryOperator<Number> sameNumber = identityFunction();
        for (Number n : numbers) {
            System.out.println(sameNumber.apply(n));
        }
    }

    // [2] 재귀적 타입 한정을 이용한 상호 비교가능 표현
    public static <E extends Comparable<E>> E max(Collection<E> c) {
        //         ↑                    ↑ (재귀로 들어간 것을 확인할 수 있다!)
        if (c.isEmpty())
            throw new IllegalArgumentException("컬렉션이 비어 있습니다.");

        E result = null;
        for (E e : c)
            if (result == null || e.compareTo(result) > 0)
                result = Objects.requireNonNull(e);
            
        return result;
    }
}