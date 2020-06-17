package _5_generic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class Item26 {
    /**
     * [Item26] 로(raw) 타입은 사용하지 말라
     * 
     * [핵심]
     * 로(raw) 타입을 사용하면 런타임에 예외가 일어날 수 있으니 사용하면 안 된다. (ClassCastException)
     * 로 타입은 제네릭이 도입되기 이전(Java1.4) 코드와의 호환성을 위해 제공될 뿐이다.
     * 빠르게 훑어보자면, Set<Object>는 어떤 타입의 객체도 저장할 수 있는 매개변수화 타입이고,
     * Set<?>는 모종의 타입 객체만 저장할 수 있는 와일드카드 타입이다.
     * 그리고 이들의 로 타입인 Set은 제네릭 타입 시스템에 속하지 않는다.
     * Set<Object>와 Set<?>는 안전하지만, 로 타입인 Set은 안전하지 않다.
     * 
     */
    
    // [1] 컬렉션의 raw 타입 - 따라하지 말 것!
    // private final Collection stamps = ...;
    // ->
    // private final Collection<Stamp> stamps = ...;
    // 위처럼 매개변수화된 컬렉션 타입을 사용해서 타입 안정성을 확보하자.

    // [2] 반복자의 raw 타입 - 따라하지 말 것!
    /*for (Iterator i = stamps.iterator(); i.hasNext(); ) {
        Stamp stamp = (Stamp) i.next(); // ClassCastException!
        stamp.cancle();
    }*/

    public static void main(String[] args) {
        // [3] raw 타입은 런타임에 예외가 발생할 확률을 매우 높인다.
        List<String> strings = new ArrayList<>();
        unsafeAdd(strings, Integer.valueOf(42));
        String s = strings.get(0); // 컴파일러가 자동으로 형변환 코드를 넣어준다
        // 바로 윗 줄에서... Integer -> String (ClassCastException 발생!)
    }

    public static void unsafeAdd(List list, Object o) {
        list.add(o);
    }

    // [3] 비한정적 와일드카드(<?>) 타입을 사용하라 - 타입 안전하며 유연하다.
    static int numElementsInCommon(Set<?> s1, Set<?> s2) {
        // s1.add("error"); // 와일드카드 타입은 null외에 어떤 원소도 넣을 수 없다.
        s2.add(null);       // 따라서, raw타입보다 안전하다.
        s2.contains("ok");
        s2.size();
        return 0;
    }

    // [4] 클래스 리터럴에는 raw 타입만 허용된다 (배열과 기본 타입도 허용)
    static void classLiteral() {
        /*
        Class c1 = List.class;          // OK
        Class c2 = String[].class;      // OK
        Class c3 = int.class;           // OK
        Class c4 = List<?>.class;       // NO
        Class c5 = List<String>.class;  // NO
        */
    }

    // [5] 런타임에는 제네릭 타입 정보가 지워지므로 instanceof 연산자는
    //     비한정적 와일드카드 타입 이외 매개변수화 타입에 적용할 수 없다.
    static void instaceOf(Object o) {
        if (o instanceof Set) { // 로 타입
            Set<?> set = (Set<?>) o; // 와일드카드 타입
            // 검사된 형 변환이므로 컴파일 경고가 뜨지 않음
        }

        // if (o instanceof Set<String>) {} // 에러

        if (o instanceof Set<?>) {
            Set<String> set1 = (Set<String>) o; // 경고: 실제로도 위험한 코드
            Set<?> set2 = (Set<?>) o; // 통과
        }
    }
}