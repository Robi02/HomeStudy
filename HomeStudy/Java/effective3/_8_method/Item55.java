package _8_method;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Item55 {
    /**
     * [Item55] 옵셔널 반환은 신중히 하라
     * 
     * [핵심]
     * 값을 반환하지 못할 가능성이 있고, 호출할 대마다 반환값이 없을 가능성을 염두에 둬야하는
     * 메서드라면 옵셔널을 반환해야 할 상황일 수 있다.
     * 하지만 옵셔널 바노한에는 성능 저하가 뒤따르니, 성능에 민감한 메서드라면 null을 반환하거나
     * 예외를 던지는 편이 나을 수 있다. 그리고 옵셔넝릉 반환값 이외의 용도로 쓰는 경우는 매우 드물다.
     * 
     */

    // [1] Item30의 최대값을 반환하는 코드를 Optional<E>를 반환하게 수정
    public static <E extends Comparable<E>> Optional<E> max(final Collection<E> c) {
        if (c.isEmpty()) {
            // throw new IllegalArgumentException("빈 컬렉션"); // 예외를 던지던 기존 코드
            return Optional.empty();
        }

        E result = null;
        for (final E e : c) {
            if (result == null || e.compareTo(result) > 0) {
                result = Objects.requireNonNull(e);
            }
        }

        // return result; // 결과를 반환하던 기존 코드
        return Optional.of(result);

        // 옵셔널을 반환하는 메서드에서는 절대 null을 반환하지 않는 것이 좋다.
        // 취지에 어긋나기 때문이다.
    }

    public static void main(final String[] args) {
        // [2] 옵셔널을 활용하기
        final List<String> list = new ArrayList<>();
        max(list).orElse("단어가 비었습니다...");           // 1) 기본값을 정해둘 수 있다.
        max(list).orElseThrow(NullPointerException::new);   // 2) 원하는 예외를 던질 수 있다.
        final String maxStr = max(list).get();              // 3) 항상 값이 채워져 있다고 가정한다.

        // [3] 심화 활용 (스트림)
        max(list).ifPresent(System.out::println); // 자바9 에서는 스트림으로 변환하여 사용할 수 있다!
                                                  // ('존재하는' == 'null 아닌 원소들'만 출력하는 메서드)

        // 주의: 옵셔널이 좋아보인다고 모든 경우에 컬렉션, 스트림,
        //       배열, 옵셔널 같은 컨테이너를 옵셔널로 감싸서 반환하면 안 된다.

        // 핵심은, 결과가 없을 수 있고 클라이언트가 이 상황을 특별하게 처리해야 한다면
        // Optional<T>를 반환하는게 좋다는 것이다.

        // 기본 타입을 반환할 대는 OptionalInt, OptionalLong, OptionalDouble 등을 활용하자.
        // (덜 중요한 기본 타입 Float, Boolean, Byte, Short, Character 은 예외)
    }
}