import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Objects;

public class Item13 {
    /**
     * [Item13] clone 재정의는 주의해서 진행해라
     * 
     * [핵심]
     * Cloneable이 몰고 온 모든 문제를 되짚어봤을 때, 새로운 인텊에시를 만들 때는 절대
     * Cloneable을 확장해서는 안 되며, 새로운 클래스도 이를 구현해서는 안 된다.
     * final 클래스라면 Cloneable을 구현해도 위험이 크지 않지만, 성능 최적화 관점에서 검토한 후
     * 별다른 문제가 없을 때만 드물게 허용해야 한다(Item.67). 기본 원칙은 '복제 기능은 생성자와
     * 팩터리를 이용하는게 최고' 라는 것이다. 단, 배열만은 clone메서드 방식이 가장 깔끔한,
     * 이 규칙의 합당한 예외라 할 수 있다.
     * 
     */

    public static class Stack implements Cloneable {
        private Object[] elements;
        private int size;
        private static final int DEFAULT_INITIAL_CAPACITY = 16;

        public Stack() {
            elements = new Object[DEFAULT_INITIAL_CAPACITY];
        }

        public void push(Object e) {
            ensureCapacity();
            elements[size++] = e;
        }

        public Object pop() {
            if (size == 0) {
                throw new EmptyStackException();
            }
            
            Object result = elements[--size];
            elements[size] = null;
            return result;
        }

        private void ensureCapacity() {
            if (elements.length == size) {
                elements = Arrays.copyOf(elements, 2 * size + 1);
            }
        }

        // Cloneable 인터페이스 구현체
        @Override public Stack clone() {
            try {
                Stack result = (Stack) super.clone(); // Object의 모든 필드를 카피, 아래는 반환 객체의 필드를 카피
                result.elements = elements.clone(); // 배열의 모든 원소까지 복제하여 새로운 배열 반환
                return result;
            }
            catch (CloneNotSupportedException e) {
                throw new AssertionError(); // 도달할 경우가 발생하지 않음
            }
        }

        // 생성자를 이용한 방법 (Stack의 인터페이스를 매개변수로 받을 수도 있어서 더 권장된다)
        public Stack(Stack stk) {
            Objects.requireNonNull(stk);
            this.elements = stk.elements.clone();
            this.size = stk.size;
        }
    }
}