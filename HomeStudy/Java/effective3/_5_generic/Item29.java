package _5_generic;

import java.util.Arrays;
import java.util.EmptyStackException;

public class Item29 {
    /**
     * [Item29] 이왕이면 제네릭 타입으로 만들라
     * 
     * [핵심]
     * 클라이언트에서 직접 형변화해야 하는 타입보다 제네릭 타입이 더 안전하고 쓰기 편하다.
     * 그러니 새로운 타입을 설계할 때는 형변환 없이도 사용할 수 있도록 하라.
     * 그렇게 하려면 제네릭 타입으로 만들어야 할 경우가 많다.
     * 기존 타입 중 제네릭이었어야 하는 게 있다면 제네릭 타입으로 변경하자. (Item26)
     * 기존 클라이언트에는 아무 영향을 주지 않으면서, 새로운 사용자를 훨신 편하게 해주는 길이다.
     * 
     */
    
    // [1] Item07의 스택 코드를 제네릭으로 개선
    public static class Stack<E> {
        private E[] elements; // <- Object[] elements;
        private int size;
        private static final int DEFAULT_INITIAL_CAPACITY = 16;

        public Stack() {
            // elements = new E[DEFAULT_INITIAL_CAPACITY];
            // 제네릭타입 배열을 생성할 수 없어서 오류가 발생한다...

            // [!] 비검사 형변환이 프로그램의 타입 안전성을 해치지 않을까? 다시 한번 확인해 보자.
            // 배열 elements는 private 필드에 저장되고, 사용자에게 반환되거나
            // 다른 메서드에 전달되는 일이 전혀 없다.
            // 유일하게 값을 추가할 수 있는 메서드인 push() 에서의 저장되는 원소 타입은 항상 E다.
            // 따라서 현 시점에서는 타입 안전하다.
            @SuppressWarnings("unchecked") E[] temp = (E[]) new Object[DEFAULT_INITIAL_CAPACITY];
            elements = temp;
        }

        public void push(E e) {
            ensureCapacity();
            elements[size++] = e;
        }

        public E pop() {
            if (size == 0) {
                throw new EmptyStackException();
            }

            E result = elements[--size];
            elements[size] = null;
            return result;
        }

        private void ensureCapacity() {
            if (elements.length == size) {
                elements = Arrays.copyOf(elements, 2 * size + 1);
            }
        }
    }
}