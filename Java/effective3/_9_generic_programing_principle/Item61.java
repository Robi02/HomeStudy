package _9_generic_programing_principle;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Item61 {
    /**
     * [Item61] 박싱된 기본 타입보다는 기본 타입을 사용하라
     * 
     * [핵심]
     * 기본 타입과 박싱된 타입 중 하나를 선택해야 한다면 가능하면 기본 타입을 사용하라.
     * 기본 타입은 간단하고 빠르다. 박싱된 기본 타입을 써야 한다면 주의를 기울이자.
     * 오토박싱이 박싱된 기본 타입을 사용할 때의 번거로움을 줄여주지만, 그 위험까지 없애주지는 않는다.
     * 두 박싱된 기본 타입을 == 연산자로 비교한다면 식별성 비교가 이뤄지는데,
     * 이는 여러분이 원할 게 아닌 가능성이 크다. 같은 연산에서 기본 타입과 박싱된 기본 타입을 혼용하면
     * 언박싱이 이뤄지며, 언박싱 과정에서 NullPointerException을 던질 수 있다.
     * 마지막으로, 기본 타입을 박싱하는 작업은 필요 없는 객체를 생성하는 부작용을 나을 수 있다.
     * 
     */

    // [1] 잘못 구현된 비교자 - 문제를 찾아보자!
    public static void sort(List<Integer> list) {
        list.sort((i, j) -> (i < j) ? -1 : (i == j ? 0 : 1));

        // 별다른 문제가 없어 보이지만... 문제가 있다.
        // 첫 번째 삼항 연산자의 조건식 (i < j) 에서는
        // 오토박싱된 Integer가 int로 변환되어 수행되지만...
        // (i == j ? 0 : 1) 에서는 Integer == Integer
        // 즉, 객체의 참조 비교가 이뤄진다.
        // 비록 값이 같더라도 i == j 는 false 가 되어 1이 반환된다.
        // 아래 코드를 실행시켜 보자...

        Comparator<Integer> naturalOrder = (i, j) -> (i < j) ? -1 : (i == j ? 0 : 1);
        System.out.println(naturalOrder.compare(new Integer(42), new Integer(42)));
        // 같은 42를 비교했으므로 0이 출력되야 하지만... 1이 출력되는걸 볼 수 있다!
    }

    // [1] 개선된 비교자
    public static void sort2(List<Integer> list) {
        list.sort((iBoxed, jBoxed) -> {
            int i = iBoxed, j = jBoxed; // 오토 언박싱
            return (i < j) ? -1 : (i == j ? 0 : 1);
        });

        Comparator<Integer> naturalOrder = (iBoxed, jBoxed) -> {
            int i = iBoxed, j = jBoxed;
            return (i < j) ? -1 : (i == j ? 0 : 1);
        };
        System.out.println(naturalOrder.compare(new Integer(42), new Integer(42)));
        // 이번에는 0이 출력된다!
    }

    // [2] 사소한 실수로 끔찍히 느린 코드가 탄생할 수 있다.
    public static void mistake(int mistake) {
        if (mistake == 0) {
            Long sum = 0L;
            for (long i = 0; i <= Integer.MAX_VALUE - 1; ++i) {
                sum += i;
            }
            System.out.println(sum);
        }
        else if (mistake == 1) {
            long sum = 0L;
            for (long i = 0; i <= Integer.MAX_VALUE - 1; ++i) {
                sum += i;
            }
            System.out.println(sum);
        }
        else {
            final long limit = (long) Integer.MAX_VALUE - 1L;
            long sum = 0L;
            for (long i = 0; i <= limit; ++i) {
                sum += i;
            }
            System.out.println(sum);
        }
    }

    public static void main(String[] args) {
        // [1]
        System.out.println("[1]");
        List<Integer> list = Arrays.asList(5, 4, 3, 3, 2, 1);
        sort2(list);
        System.out.println(list.toString());

        // [2]
        System.out.println("[2]");
        for (int test = 0; test < 3; ++test) {
            long bgnTime = System.currentTimeMillis();
            mistake(test);
            System.out.println("TestCase#" + (test + 1) + " Elapsed Time: " + (System.currentTimeMillis() - bgnTime) + "ms.");
        }

        // 2305843005992468481
        // TestCase#1 Elapsed Time: 14239ms.
        // 2305843005992468481
        // TestCase#2 Elapsed Time: 811ms.
        // 2305843005992468481
        // TestCase#3 Elapsed Time: 713ms.

        // 박싱-언박싱 실수 하나로 어림잡아도 15배는 느린 코드가 되었다!
        // 추가로 #3 테스트는 반복문 조건절 안의 상수가 어떤 영향이 있는지 확인해보고 싶었다.
        // 위 코드처럼 -1 을 넣은 경우, 상수를 사용하는 것이 더 빨랐고,
        // -1이 없는 경우 오히려 #2 가 더 빨랐다! 아마 컴파일러 단에서 자동으로 상수화 시키는 것으로 판단된다.
    }
}