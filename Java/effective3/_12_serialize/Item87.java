package _12_serialize;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Item87 {
    /**
     * [Item87] 커스텀 직렬화 형태를 고려해보라
     * 
     * [핵심]
     * 클래스를 직렬화하기로 했다면(Item86) 어떤 직렬화 형탤르 사용할지 심사숙고하기 바란다.
     * 자바의 기본 직렬화 형태는 객체를 직렬화한 결과가 해당 객체의 논리적 표현에
     * 부합할 때만 사용하고, 그렇지 않으면 객체를 적절히 설명하는 커스텀 직렬화 형태를 고안하라.
     * 직렬화 형태도 공개 메서드(Item51)를 설계할 대에 준하는 시간을 들여 설계해야 한다.
     * 한번 공개된 메서드는 향후 릴리스에서 제거할 수 없듯이, 직렬화 형태에 포함된 필드도 마음대로
     * 제거할 수 없다. 직렬화 호환성을 유지하기 위해 영원히 지원해야 하는 것이다.
     * 잘못된 직렬화 형태를 선택하면 해당 클래스의 복잡성과 성능에 영구히 부정적인 영향을 남긴다.
     * 
     * [팁]
     * 1. 먼저 고민해보고 괜찮다고 판단될 때만 기본 직렬화 형태를 사용하라.
     * 2. 객체의 물리적 표현과 논리적 내용이 같다면 기본 직렬화 형태라도 무방하다.
     * 3. 기본 직렬화 형태가 적합하다고 결정됐더라도 불변식 보장과
     *    보안을 위해 readObject()를 제공해야 할 때가 많다.
     * 4. 해당 객체의 논리적 상태와 무관한 필드라고 확신할 때만 transient 한정자를 생략하라.
     * 5. 객체의 전체 상태를 읽는 메서드에 적용해야 하는 동기화 메커니즘을 직렬화에도 적용해야 한다.
     *    (모든 메서드가 synchronized로 선언하여 스레드 안전하게 설계된 객체라면, writeObject를 호출하는
     *     메서드 또한 syncrhornized로 선언해야 한다.)
     * 6. 어떤 직렬화 형태를 택하든 직렬화 가능 클래스 모두에 직렬 버전 UID를 명시적으로 부여하자.
     *    또한, 구버전으로 직렬된 인스턴스와의 호환성을 끊고싶은 경우가 아니면 UID를 수정하지 말라.
     * 
     * [객체의 물리-논리적 표현차가 클 때 기본 직렬화를 사용하면 생기는 문제점]
     * 1. 공개 API가 현재 내부 표현 방식에 영구히 묶인다
     * 2. 너무 많은 공간을 차지할 수 있다.
     * 3. 시간이 너무 많이 걸릴 수 있다.
     * 4. 스택 오버플로를 일으킬 수 있다.
     * 
     */


    public static final class StringList implements Serializable {
        private transient int size = 0;
        private transient Entry head = null;
        // transient(일시적) 한정자는 해당 인스턴스 필드가
        // 기본 직렬화 형태에 포함되지 않는다는 표시다.

        // 이제는 직렬화되지 않음
        public static class Entry {
            String data;
            Entry next;
            Entry previous;
        }

        // 지정한 문자열을 이 리스트에 추가
        public final void add(String s) {
            // ...
        }

        /**
         * 이 {@code StringList} 인스턴스를 직렬화한다.
         * 
         * @serialData 이 리스크의 크기(포함된 문자열의 개수)를 기록한 후
         * ({@code int}), 이어서 모든 원소를(각각은 {@code String})
         * 순서대로 기록한다.
         * 
         */
        private void writeObject(ObjectOutputStream s) throws IOException {
            s.defaultWriteObject(); // 모든 필드가 transient면 호출할 필요가 없지만, 추후 transient가 아닌 인스턴스가 추가될 경우를 대비한다.
            s.writeInt(size);

            // 모든 원소를 순차적으로 기록
            for (Entry e = head; e != null; e = e.next)
                s.writeObject(e.data);
        }

        private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
            s.defaultReadObject(); // 모든 필드가 transient면 호출할 필요가 없지만, 추후 transient가 아닌 인스턴스가 추가될 경우를 대비한다.
            int numElements = s.readInt();

            // 모든 원소를 읽어 리스트에 삽입
            for (int i = 0; i < numElements; ++i)
                add((String) s.readObject());
        }

        // 나머지 코드 생략...
    }
}