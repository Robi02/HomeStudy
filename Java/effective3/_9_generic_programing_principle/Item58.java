package _9_generic_programing_principle;

import java.util.Iterator;
import java.util.List;

public class Item58 {
    /**
     * [Item58] 전통적인 for 문보다는 for-each 문을 사용하라
     * 
     * [핵심]
     * 전통적인 for 문과 비교했을 때 for-each 문은 명료하고, 유연하고, 버그를 예방해 준다.
     * 서능 저하도 없다. 가능한 모든 곳에서 for 문이 아닌 for-each 문을 사용하자.
     * 
     * [단, for-each문을 사용할 수 없는 경우도 있다!]
     * 1) 파괴적인 필터링: 컬렉션을 순회하면서 선택된 원소를 제거해야 하는 경우.
     * 2) 변형: 컬렉션을 순회하면서 그 원소의 값 일부 혹은 전체를 교체해야 하는 경우.
     * 3) 병렬 반복: 여러 컬렉션을 병렬로 순회해야 하는 경우.
     * 
     */

    // [1-1] 컬랙션 순회하기 - 더 나은 방법이 있다!
    public static void test1_1(List<String> list) {
        for (Iterator<String> i = list.iterator(); i.hasNext(); ) {
            String str = i.next();
            // ...
        }
    }

    // [1-2] 배열 순회하기 - 더 나은 방법이 있다!
    public static void test1_2(String[] array) {
        for (int i = 0; i < array.length; ++i) {
            String str = array[i];
            // ...
        }
    }

    // [1-3] 훌륭하다!
    public static void test1_3(List<String> list, String[] array) {
        for (String str : list) {
            // ...
        }

        for (String str : array) {
            // ...
        }
    }
}