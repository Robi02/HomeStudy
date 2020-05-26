import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Item18 {
    /**
     * [Item18] 상속보다는 컴포지션을 사용하라
     * 
     * [핵심]
     * 상속은 강력하지만 캡슐화를 해친다는 문제가 있다. 상속은 상위 클래스와 하위 클래스가 순수한
     * is-a 관계일 때만 써야 한다. is-a 관계일 때도 안심할 수만은 없는 게, 하위 클래스의 패키지가
     * 상위 클래스와 다르고, 상위 클래스가 확장을 고려해 설계되지 않았다면 여전히 문제가 될 수 있다.
     * 상속의 취약점을 피하려면 상속 대신 컴포지션과 전달을 사용하자.
     * 특히 래퍼 클래스로 구현할 적당한 인터페이스가 있다면 더욱 그렇다.
     * 래퍼 클래스는 하위 클래스보다 견고하고 강력하다.
     * 
     * [상속의 단점]
     * 캡슐화를 깨트린다.
     * 내부 구현을 불필요하게 노출한다.
     * 상위 클래스 API의 결함까지도 승계한다.
     * 
     * [래퍼 클래스의 단점]
     * 래퍼 클래스 자기 자신의 참조를 다른 객체에 넘겨서 다음 콜백때 사용하도록 할 때
     * 내부 객체는 자신을 감싸고 있는 래퍼의 존재를 몰라서 자신의 참조를 넘기고,
     * 콜백 때 래퍼가 아닌 내부 객체를 호출하게 된다. (SELF 문제)
     * 
     */
    
    // [1] 계측 HashSet - 잘못된 상속의 예
    public static class InstrumentedHashSet<E> extends HashSet<E> {
        // 추가된 원소의 수
        private int addCount = 0;

        public InstrumentedHashSet() {}

        public InstrumentedHashSet(int initCap, float loadFactor) {
            super(initCap, loadFactor);
        }

        @Override public boolean add(E e) {
            ++addCount;
            return super.add(e);
        }

        @Override public boolean addAll(Collection<? extends E> c) {
            addCount += c.size();
            return super.addAll(c);
        }

        public int getAddCount() {
            return addCount;
        }
    }

    public static void main(String[] args) {
        InstrumentedHashSet<String> set = new InstrumentedHashSet<>();
        set.addAll(Arrays.asList("1", "2", "3"));
        System.out.println("[1] : " + set.getAddCount()); // [1] 반환값이 과연 3일까?

        // 정답은 6 을 반환한다.
        // HashSet의 addAll 메서드가 add를 사용해 구현되기 때문...
        // HashSet 문서에는 이런 내용이 적혀있지 않다.

        InstrumentedSetEx<String> setEx = new InstrumentedSetEx<>(new HashSet<>(32)); // [2] HashSet 말고 TreeSet으로도 구현이 가능해졌다.
        setEx.addAll(Arrays.asList("1", "2", "3"));
        System.out.println("[2] : " + setEx.getAddCount());

        // [2] ForwardingSet<E> 클래스를 통해 훨씬 유연해지고 문제점이 해결되었다.
    }

    // [2] 개선된 컴포지션 Set의 전달 클래스(ForwardingClass)
    public static class ForwardingSet<E> implements Set<E> {
        private final Set<E> s;
        public ForwardingSet(Set<E> s) { this.s = s; }

        public void clear() { s.clear(); }
        public boolean contains(Object o) { return s.contains(o); }
        public boolean isEmpty() { return s.isEmpty(); }
        public int size() { return s.size(); }
        public Iterator<E> iterator() { return s.iterator(); }
        public boolean add(E e) { return s.add(e); }
        public boolean remove(Object o) { return s.remove(o); }
        public boolean containsAll(Collection<?> c) { return s.containsAll(c); }
        public boolean addAll(Collection<? extends E> c) { return s.addAll(c); }
        public boolean removeAll(Collection<?> c) { return s.removeAll(c); }
        public boolean retainAll(Collection<?> c) { return s.retainAll(c); }
        public Object[] toArray() { return s.toArray(); }
        public <T> T[] toArray(T[] a) { return s.toArray(a); }

        @Override public boolean equals(Object o) { return s.equals(o); }
        @Override public int hashCode() { return s.hashCode(); }
        @Override public String toString() { return s.toString(); }

    }

    // [2] 데코레이터 패턴 : 래퍼 클래스 : 개선된 컴포지션 Set
    public static class InstrumentedSetEx<E> extends ForwardingSet<E> {
        private int addCount = 0;

        public InstrumentedSetEx(Set<E> s) {
            super(s);
        }

        @Override public boolean add(E e) {
            ++addCount;
            return super.add(e);
        }

        @Override public boolean addAll(Collection<? extends E> c) {
            addCount += c.size();
            return super.addAll(c);
        }

        public int getAddCount() {
            return addCount;
        }
    }
}