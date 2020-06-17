package _5_generic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Item32 {
    /**
     * [Item32] 제네릭과 가변인수를 함께 쓸 때는 신중하라
     * 
     * [핵심]
     * 가변인수와 제네릭은 궁합이 좋지 않다.
     * 가변인수 기능은 배열을 노출하여 추상화가 완벽하지 못하고,
     * 배열과 제네릭의 타입 규칙이 서로 다르기 때문이다.
     * 제네릭 varargs 매개변수는 타입 안전하지는 않지만, 허용된다.
     * 메서드에 제네릭 (혹은 매개변수화된) varargs 매개변수를 사용하고자 한다면,
     * 먼저 그 메서드가 타입 안전한지 확인한 다음 @SafeVarargs 애너테이션을 달아
     * 사용하는데 불편함이 없게끔 하자.
     * 
     * [가변인수 메서드 개발 시 확인]
     * 1) varargs 매개변수 배열에 아무것도 저장하지 않는다.
     * 2) 그 배열(혹은 복제본)을 신뢰할 수 없는 코드에 노출하지 않는다.
     * 
     */

    // [1] 제네릭과 varargs를 혼용하면 타입 안정성이 깨진다!
    static void dangerous(List<String>... stringLists) {
        List<Integer> intList = Arrays.asList(42);
        Object[] objects = stringLists;
        objects[0] = intList;               // 힙 오염 발생!
        String s = stringLists[0].get(0);   // Integer -> String (ClassCastException!)

        // varargs가 배열 기반으로 동작하는데, Item28의 교훈처럼
        // 배열과 제네릭은 같이 쓰기에는 위험하다.

        // 하지만, varargs 매개변수를 받는 메서드가 실무에서 매우 유용하기에
        // 언어 설계자는 이 모순을 수용하기로 했다.
        // 자바 라이브러리의 Arrays.asList()가 그 예시다.
    }

    // [2] 자신의 제네릭 매개변수 배열의 참조를 노출 - 안전하지 않은 코드
    static <T> T[] toArray(T... args) {
        return args;

        // 이 메서드가 반환하는 배열의 타입은 이 메서드에 인수를 넘기는
        // 컴파일 타임에 결정되는데, 그 시점에는 컴파일러에게 충분한 정보가
        // 주어지지 않아 타입을 잘못 판단할 수 있다.
    }

    // [2] 구체적인 예시
    static <T> T[] pickTwo(T a, T b, T c) {
        switch (ThreadLocalRandom.current().nextInt(3)) {
            case 0: return toArray(a, b);
            case 1: return toArray(a, c);
            case 2: return toArray(b, c);
        }
        throw new AssertionError(); // 도달할 수 없다
    }

    public static void main(String[] args) {
        // [2]
        String[] attrs = pickTwo("Good", "Fast", "Cheap"); // Object[] -> String[] (ClassCastException!)

        // pickTwo()의 반환값을 attr에 저장하기 위해 String[]으로
        // 형변화하는 코드를 컴파일러가 자동으로 생성한다!

        // 위처럼, 제네릭 varargs 매개변수 배열에 다른 메서드가 접근하도록 허용하면 위험하다.
        // - 예외
        // 1) @SafeVarargs로 제대로 애노테이트된 또 다른 varargs 메서드에 넘기는것은 괜찮다.
        // 2) 그저 이 배열 내용의 일부 함수를 호출만 하는 (varargs를 받지 않는) 일반 메서드에 넘기는 것도 안전하다.
    }

    @SafeVarargs
    static <T> List<T> flatten(List<? extends T>... lists) {
        List<T> result = new ArrayList<>();
        for (List<? extends T> list : lists) {
            result.addAll(list);
        }
        return result;
    }

}