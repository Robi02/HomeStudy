package _8_method;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Item52 {
    /**
     * [Item52] 다중정의는 신중히 사용하라
     * 
     * [핵심]
     * 프로그래밍 언어가 다중정의를 허용한다고 해서 다중정의를 꼭 활용하란 뜻은 아니다.
     * 일반적으로 매개변수 수가 같을 때는 다중정의를 피하는게 좋다.
     * 상황에 따라, 특히 생성자라면 이 조언을 따르기가 불가능할 수도 있다.
     * 그럴 때는 헷갈릴 만한 매개변수는 형변환하여 정확한 다중정의 메서드가 선택되도록 해야 한다.
     * 이것이 불가능하다면, 예컨데 기존 클래스를 수정해 새로운 인터페이스를 구현해야 할 때는
     * 같은 객체를 입력받는 다중정의 메서드들이 모두 동일하게 동작하도록 만들어야 한다.
     * 그렇지 못하면 프로그래머들은 다중정의된 메서드나 생성자를 효과적으로 사용하지 못할 것이고,
     * 의도대로 동작하지 않는 이유를 이해하지도 못할 것이다.
     * 
     */

    public static class CollectionClassifier {
        // [1]
        public static String classify(final Set<?> s) {
            return "집합";
        }

        public static String classify(final List<?> lst) {
            return "리스트";
        }

        public static String classify(final Collection<?> c) {
            return "그 외";
        }

        // [1-2] 오버라이딩으로 인한 문제 해결방안
        public static String classify(boolean isNew, final Collection<?> c) {
            return c instanceof Set ? "집합" :
                   c instanceof List ? "리스트" : "그 외";
        }

        // [2]
        public static class Wine {
            String name() { return "포도주"; }
        }
        public static class SparklingWine extends Wine {
            @Override String name() { return "발포성 포도주"; }
        }
        public static class Champagne extends SparklingWine {
            @Override String name() { return "샴페인"; }
        }

        public static void main(final String[] args) {
            // [1] 실행 결과는?
            final Collection<?>[] collections = {
                new HashSet<>(),
                new ArrayList<>(),
                new HashMap<String, String>().values()
            };

            for (Collection<?> c : collections) {
                System.out.println(classify(c));
            }

            // - 결과: "그 외" 만 3번 출력된다.

            // [2] 실행 결과는??
            List<Wine> wineList = Arrays.asList(new Wine(), new SparklingWine(), new Champagne());

            for (Wine wine : wineList) {
                System.out.println(wine.name());
            }

            // - 결과: "포도주" "발포성 포도주" "샴페인" 이 출력된다.

            // * 재정의(Override)한 메서드는 동적으로 선택되고, -> 런타임
            // * 다중정의(Overload)한 메서드는 정적으로 선택된다. -> 컴파일 타임
        }
    }
}