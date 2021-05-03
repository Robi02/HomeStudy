package _5_generic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Item28 {
    /**
     * [Item28] 배열보다는 리스트를 사용하라
     * 
     * [핵심]
     * 배열과 제네릭에는 매우 다른 타입 규칙이 적용된다.
     * 배열은 공변이고 실체화되는 반면, 제네릭은 불공변이고 타입 정보가 소거된다.
     * 그 결과 배열은 런타임에는 타입 안전하지만 컴파일타임에는 그렇지 않다.
     * 제네릭은 그 반대다. 그래서 둘을 섞어 쓰기란 쉽지 않다.
     * 둘을 섞어 쓰다가 컴파일 오류나 경고를 만나면,
     * 가장 먼저 배열을 리스트로 대체하는 방법을 적용해 보자.
     * 
     */

    // [1] 배열과 제네릭의 차이
    // Sub가 Super의 하위 타입이라면 배열 Sub[]는 Super[]의 하위 타입이 된다.
    // (공변: 함께 변함)
    // List<Type1>은 List<Type2>의 하위 타입도 아니고 상위 타입도 아니다.

    /*
    {
        [Array]
        Object objectArray = new Long[1];
        objectArray[0] = "다른 타입을 넣으면?"; // 런타임 에러! (ArrayStoreException)
        // (배열은 런타임에 자신이 담기로 한 원소의 타입을 인지하고 확인함)

        vs.

        [List]
        List<Object> ol = new ArrayList<Long>();
        ol.add("다른 타입을 넣으면?"); // 컴파일 에러!
        // (제네릭은 타입 정보가 런타임에는 소거됨)
    }
    */

    // 배열과 제네릭은 잘 어우러지지 못한다...
    // 배열은 제네릭 타입, 매개변수화 타입, 타입 매개변수로 사용할 수 없다.

    /*
    {
        // 제네릭 배열 생성을 허용하지 않는 이유는? - 아래 코드는 컴파일 에러 발생
        List<String>[] stringLists = new List<String>[1];   // (1) 원래는 컴파일 에러지만 생성된다고 가정해보자
        List<Integer> intList = List.of(42);                // (2) List<Integer> 생성
        Object[] objects = stringLists;                     // (3) objects에 List<String>[] 을 참조하겠지... (배열은 공변이니 가능)
        objects[0] = intList;                               // (4) objects[0]에 List<Integer>을 할당... List<String>인데?!
        String s = stringLists[0].get(0);                   // (5) Auto type casting -> ClassCastException!
    }
    */

    // [2] Chooser - 아주 위험한 코드! 제네릭을 시급히 적용해야 한다!
    public static class Chooser {
        private final Object[] choiceArray;

        public Chooser(Collection choices) {
            choiceArray = choices.toArray();
        }

        public Object choose() {
            // choose() 메서드 호출 시마다 반환된 Object를
            // 원하는 타입으로 형변환 해야 한다...
            Random rnd = ThreadLocalRandom.current();
            return choiceArray[rnd.nextInt(choiceArray.length)];
        }
    }

    // 개선안1: Object타입 대신, 제네릭으로 만든다
    //          (런타임에 오동작할 확률이 0%는 아니다 - 사람은 항상 실수를 한다)
    public static class ChooserEx<T> {
        private final T[] choiceArray;

        public ChooserEx(Collection<T> choices) {
            // 제네릭 T[]로 타입 안정성이 보장되는 코드입니다.
            // 사용에 주의해 주시기 바랍니다. (이렇게 주석을 써 놓는게 좋다...)
            @SuppressWarnings("unchecked") T[] temp = (T[]) choices.toArray();
            choiceArray = temp;
        }

        public T choose() {
            Random rnd = ThreadLocalRandom.current();
            return choiceArray[rnd.nextInt(choiceArray.length)];
        }
    }
    
    // 개선안2: 제네릭으로 만들고 배열에서 리스트 기반으로 바꾼다
    //          (약간의 성능 하락을 감수하고 컴파일러단에서 제어한다)
    public static class ChooserEx2<T> {
        private final List<T> choiceList;

        public ChooserEx2(Collection<T> choices) {
            choiceList = new ArrayList<>(choices);
        }

        public T choose() {
            Random rnd = ThreadLocalRandom.current();
            return choiceList.get(rnd.nextInt(choiceList.size()));
        }
    }
}