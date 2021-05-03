package _12_serialize;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Set;

public class Item85 {
    /**
     * [Item85] 자바 직렬화의 대안을 찾으라
     * 
     * [핵심]
     * 직렬화는 위험하니 피해야 한다. 시스템을 밑바닥부터 설계한다면 JSON이나 프로토콜 버퍼
     * 같은 대안을 사용하자. 신뢰할 수 없는 데이터는 역직렬화 하지 말자.
     * 꼭 해야 한다면 객체 역직렬화 필터링을 사용하되, 이마저도 모든 공겨을 막아줄 수는 없음을
     * 기억하자. 클래스가 직렬화를 지원하도록 만들지 말고, 꼭 그렇게 만들어야 한다면
     * 정말 신경 써서 작성해야 한다.
     * 
     */

    // [1] 역직렬화 폭탄 - 영원히 계속되는 코드
    public static byte[] bomb() {
        Set<Object> root = new HashSet<>();
        Set<Object> s1 = root;
        Set<Object> s2 = new HashSet<>();
        for (int i = 0; i < 100; ++i) {
            Set<Object> t1 = new HashSet<>();
            Set<Object> t2 = new HashSet<>();
            t1.add("foo");
            s1.add(t1);
            s1.add(t2);
            s2.add(t1);
            s2.add(t2);
        }
        
        return serialize(root);
        // 이 객체 그래프는 201개의 HashSet 인스턴스로 구성되는데, 각각은 3개 이하의 객체 참조를 갖는다.
        // 스트림의 전체 크기는 5,744바이트지만 역직렬화는 영원히 계속될 것이다
        // HasetSet 인스턴스의 역직렬화는 원소들의 hashCode()를 2^100번 넘게 호출해야 하기 때문이다.
        // 그리고, 무언가 잘못되었다는 신호조차 주지 않고 무한히 연산한다.
    }

    public static byte[] serialize(Object obj) {
        byte[] rtAry = null;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                oos.writeObject(obj);
                rtAry = baos.toByteArray();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return rtAry;
    }
}