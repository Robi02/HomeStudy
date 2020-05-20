import java.util.Arrays;
import java.util.EmptyStackException;

public class Item07 {
    /**
     *  [Item07] 다 쓴 객체 참조를 해제하라
     * 
     *  [핵심]
     *  메모리 누수는 겉으로 잘 드러나지 않아 시스템에 수년감 잠복하는 사례도 있다.
     *  이런 메모리 누수는 철저한 코드 리뷰나 힙 프로파일러 같은 디버깅 도구를 동원해야만
     *  발견되기도 한다. 그래서 이런 종류의 문제는 예방법을 익혀두는 것이 매우 중요하다.
     * 
     */

    public static class Stack {
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
            
            // return elements[--size]; <- Dangerous Code!

            // 얼핏 보기에는 큰 문제가 없어보이지만,
            // 이 코드를 개선하지 않으면 디스크 페이징이나 OutOfMemoryError를 일으킬 가능성이 존재한다.
            // 스택 사이즈를 줄였지만, elements의 참조가 사라진건 아니기 때문이다.
            // 해당 코드를 아래와 같이 수정해 주어야 GC가 수행될 것이다.

            Object result = elements[--size];
            elements[size] = null; // 참조 해제
            return result;

            // 그렇다고 무차별적으로 모든 객체 사용 후 null을 대입해 주는것도 좋지 않다.
            // null을 처리하는 일은 예외적인 경우여야 하며, 다 쓴 참조를 해제하는 가장 좋은 방법은
            // 그 참조를 담은 변수를 scope 밖으로 밀어내는 것이다.
        }

        private void ensureCapacity() {
            if (elements.length == size) {
                elements = Arrays.copyOf(elements, 2 * size + 1);
                
            }
        }
    }

    public static void main(String[] args) {
        
    }
}