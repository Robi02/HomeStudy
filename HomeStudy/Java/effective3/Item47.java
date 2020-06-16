import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Item47 {
    /**
     * [Item47] 반환 타입으로는 스트림보다 컬렉션이 낫다
     * 
     * [핵심]
     * 원소 시퀀스를 반환하는 메서드를 작성할 때는, 이를 스트림으로 처리하기를 원하는 사용자와
     * 반복으로 처리하길 원하는 사용자가 모두 있을 수 있음을 떠올리고, 양족을 다 만족시키려 노력하자.
     * 컬렉션을 반환할 수 있다면 그렇게 하라. 반환 전부터 이미 원소들을 컬렉션에 담아 관리하고 있거나
     * 컬렉션을 하나 더 만들어도 될 정도로 원소 개수가 적다면 ArrayList같은 표준 컬렉션에 담아 반환하라.
     * 그렇지 않으면 앞서의 멱집합 예처럼 전용 컬렉션을 구현할지 고민하라.
     * 컬렉션을 반환하는 게 불가능하면 스트림과 Iterable중 더 자연스러운 것을 반환하라. 만약 나중에
     * Stream 인터페이스가 Itereable을 지원하도록 자바가 수정된다면, 그때는 안심하고 스트림을
     * 반환하면 될 것이다. (스트림 처리와 반복 모두에 사용 가능해지니)
     * 
     * 
     * 
     */

    // 멱집합을 구현한 전용 클래스
    public static class PowerSet {

        public static final <E> Collection<Set<E>> of(Set<E> s) {
            List<E> src = new ArrayList<>(s);
            if (src.size() > 30) {
                throw new IllegalArgumentException("집합에 원소가 너무 많습니다! (최대 30개): " + s);
            }

            return new AbstractList<Set<E>>() {
                @Override public int size() {
                    // 멱집합의 크기는 2를 원래 집합의 원소 수만큼 거듭제곱한 것과 같다.
                    return 1 << src.size();
                }

                @Override public boolean contains(Object o) {
                    return o instanceof Set && src.containsAll((Set) o);
                }

                @Override public Set<E> get(int index) {
                    Set<E> result = new HashSet<>();
                    for (int i = 0; index != 0; ++i, index >>= 1) {
                        // Index:0 0000 0000 0000 0000 -> Nothing
                        // Index:1 0000 0000 0000 0001 -> src.get(0)
                        // Index:2 0000 0000 0000 0010 -> src.get(1)
                        // Index:3 0000 0000 0000 0011 -> src.get(0), src.get(1)
                        // ...
                        if ((index & 1) == 1) {
                            result.add(src.get(i));
                        }
                    }
                    return result;
                }
            };
        }
    }

    public static void main(String[] args) {
        Set<String> s = new HashSet<>();
        s.add("A");
        s.add("B");
        s.add("C");
        Collection<Set<String>> ps = PowerSet.of(s);
        System.out.println(ps.toString());
        System.out.println(ps.size());
    }
}