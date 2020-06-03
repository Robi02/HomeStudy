import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ScheduledFuture;

public class Item31 {
    /**
     * [Item31] 한정적 와일드카드를 사용해 API 유연성을 높이라
     * 
     * [핵심]
     * 조금 복잡하더라도 와일드카드 타입을 적용하면 API가 훨신 유연해진다.
     * 그러니 널리 쓰일 라이브러리를 작성한다면 반드시 와일드카드 타입을 적절히 사용해줘야 한다.
     * PESC 공식을 기억하자. 생산자(producer)는 extends를, 소비자(consumer)는 super를 사용한다.
     * Comparable과 Comparator는 모두 소비자라는 사실도 잊지 말자.
     * 
     */
    
    public static void main(String[] args) {
        // [1] 한정적 와일드카드 사용의 장점
        Stack<Number> stk = new Stack<>();
        
        List<Integer> inList = new ArrayList<>();
        inList.add(1); inList.add(2); inList.add(3);
        stk.pushAll(inList);

        List<Number> outList = new ArrayList<>();
        stk.popAll(outList);

        // 유연성을 극대화하려면 원소의 생산자나 소비자용 입력 매개변수에
        // 와일드카드 타입 '<? extends(super) E>'을 사용하면 된다.
        // PECS(펙스) : producer-extends, consumer-super (생산자는 자식, 소비자는 부모)

        // [2] 한정적 와일드카드를 극으로 사용하는 경우
        List<ScheduledFuture<?>> scheduledFutures = new ArrayList<>();
        ScheduledFuture<?> result = max(scheduledFutures);

        // [수정전] public static <E Comparable<E>> E max(Collection<E> c)
        // [수정후] public static <E extends Comparable<? super E>> E max(Collection<? extends E> c)
        // (재귀적 타입 한정 + 한정적 와일드카드)

        // ScheduledFutre가 Comparable<ScheduledFutre>를 구현하지 않았기 때문에 
        // 수정 전의 max() 메서드(Item30)는 위 코드를 호출할 수 없었다.

        // 인터페이스 상속 구조를 잘 봐보자...
        // public interface Comparable<E>
        // public interface Delayed extends Comparable<Delayed>
        // public interface ScheduledFuture<V> extends Delayed, Future<V>

        // ...? 이해가 잘 안된다...
    }
    
    // [1]
    public static class Stack<E> {
        private E[] elements;
        private int size;
        private static final int DEFAULT_INITIAL_CAPACITY = 16;

        public Stack() {
            // 유일하게 값을 추가할 수 있는 메서드인 push() 에서의 저장되는 원소 타입은 항상 E다.
            // 따라서 현 시점에서는 타입 안전하다.
            @SuppressWarnings("unchecked") E[] temp = (E[]) new Object[DEFAULT_INITIAL_CAPACITY];
            elements = temp;
        }

        public void push(E e) {
            ensureCapacity();
            elements[size++] = e;
        }

        public void pushAll(Iterable<? extends E> src) { // '<? extends E>' : 한정적 와일드카드 타입
            for (E e : src)
                push(e);
        }

        public E pop() {
            if (size == 0) {
                throw new EmptyStackException();
            }

            E result = elements[--size];
            elements[size] = null;
            return result;
        }

        public void popAll(Collection<? super E> dst) { // '<? super E>' : 한정적 와일드카드 타입
            while (!isEmpty())
                dst.add(pop());
        }

        public boolean isEmpty() {
            return (size == 0);
        }

        private void ensureCapacity() {
            if (elements.length == size) {
                elements = Arrays.copyOf(elements, 2 * size + 1);
            }
        }
    }

    // [2] List<ScheduledFuture<?>>를 매개변수로 넣기 위해 구조 개선...
    public static <E extends Comparable<? super E>> E max(Collection<? extends E> c) {
        if (c.isEmpty())
            throw new IllegalArgumentException("컬렉션이 비어 있습니다.");

        E result = null;
        for (E e : c)
            if (result == null || e.compareTo(result) > 0)
                result = Objects.requireNonNull(e);
            
        return result;
    }

    // [3] 두 가지 버전의 swap() 메서드
    public static <E> void swapA(List<E> list, int i, int j) { // [3-1]
        list.set(i, list.set(j, list.get(i)));
    }

    public static void swapB(List<?> list, int i, int j) { // [3-2]
        // list.set(i, list.set(j, list.get(i))); // compile error
        swapHelper(list, i, j); // ok
    }

    // 위와 같은 경우, public API의 경우에는 [3-2]가 더 낫다.
    // 메서드 선언에 타입 매개변수가 한 번만 나오면 와일드카드로 대체를 권장한다.
    // 비한정적 타입 매개변수라면 비한정적 와일드카드로, 한정적 타입 매개변수면 한정적 와일드카드로 바꾼다.

    // [3-2]의 경우 작은 문제가 있는데, List<?>에는 null외에 어떤 값도 넣을 수 없는 점이다.
    // 이 경우, 와일드카드 타입의 실제 타입을 알려주는 메서드를 private helper 메서드로 따로 작성하는 방법이 있겠다.

    private static <E> void swapHelper(List<E> list, int i, int j) {
        list.set(i, list.set(j, list.get(i)));
    }
}