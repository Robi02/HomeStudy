package _4_class_and_interface;

import java.util.AbstractList;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Item20 {
    /**
     * [Item20] 추상 클래스보다는 인터페이스를 우선하라
     * 
     * [핵심]
     * 일반적으로 다중 구현용 타입으로는 인터페이스가 가장 적합히다.
     * 복잡한 인터페이스라면 구현하는 수고를 덜어주는 골격 구현을 함께 제공하는 방법을 꼭 고려해보자.
     * 골격 구현은 '가능한 한' 인터페이스의 디폴트 ㅍ메서드로 제공하여 그 인터페이스를 구현한
     * 모든 곳에서 활용하도록 하는 것이 좋다. '가능한 한'이라고 한 이유는, 인터페이스에 걸려 있는
     * 구현상의 제약 때문에 골격 구현을 추상 클래스로 제공하는 경우가 더 흔하기 때문이다.
     * 
     */

    // 골격 구현을 사용해 완성한 구체 클래스 (AbstractList<> 골격 구현)
    static List<Integer> intArrayAsList(int[] a) {
        Objects.requireNonNull(a);

        // 다이아몬드 연산자(<>)를 공백으로 사용하는건 Java9 부터 가능하다...
        // 지금 환경은 Java1.8 이므로, <Integer>로 적었다.
        return new AbstractList<Integer>() {
            @Override public Integer get(int i) {
                return a[i]; // 오토박싱
            }

            @Override public Integer set(int i, Integer val) {
                int oldVal = a[i];
                a[i] = val;     // 오토언박싱
                return oldVal;  // 오토박싱
            }

            @Override public int size() {
                return a.length;
            }
        };
    }

    // 골격 구현 클래스 예시
    // Map.Entry<K,V> 인터페이스에서 디폴트 메서드로 골격 구현을 제공하면 되지 않는가?
    // -> toString(), equals(), hashCode()같은 Object 메서드들은 인터페이스에서 재정의할 수 없다!
    public static abstract class AbstractMapEntry<K,V> implements Map.Entry<K,V> {
        // 변경 가능한 엔트리는 이 메서드를 반드시 재정의해야 한다.
        @Override public V setValue(V value) {
            throw new UnsupportedOperationException();
        }

        // Map.Entry.equals의 일반 규약을 구현한다.
        @Override public boolean equals(Object o) {
            if (o == this) {
                return true;
            }

            if (!(o instanceof Map.Entry)) {
                return false;
            }

            Map.Entry<?,?> e = (Map.Entry) o;

            return Objects.equals(e.getKey(), getKey()) &&
                   Objects.equals(e.getValue(), getValue());
        }

        // Map.Entry.hashCode의 일반 규약을 구현한다.
        @Override public int hashCode() {
            return Objects.hashCode(getKey()) ^ Objects.hashCode(getValue());
        }

        @Override public String toString() {
            return getKey() + "=" + getValue();
        }
    }
}