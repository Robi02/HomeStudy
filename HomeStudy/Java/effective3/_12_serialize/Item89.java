package _12_serialize;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Arrays;

public class Item89 {
    /**
     * [Item89] 인스턴스 수를 통제해야 한다면 readResolve보다는 열거 타입을 사용하라
     * 
     * [핵심]
     * 불변식을 지키기 위해 인스턴스를 통제해야 한다면 가능한 한 열거 타입을 사용하자.
     * 여의치 않은 상황에서 직렬화와 인스턴스 통제가 모두 필요하다면 readResolve메서드를
     * 작성해 넣어야 하고 ,그 클래스에서 모든 참조 타입 인스턴스 필드를 transient로 선언해야 한다.
     * 
     */

    public static class Elvis implements Serializable {
        public static final Elvis INSTANCE = new Elvis();
        private Elvis() {}

        private String[] favoriteSongs = {
            "Hound Dog", "Heartbreak Hotel"
        };

        public void printFavorites() {
            System.out.println(Arrays.toString(favoriteSongs));
        }

        private Object readResolve() {
            return INSTANCE;
        }

        private static final long serialVersionUID = 0;
    }

    public static class ElvisStealer implements Serializable {
        static Elvis impresonator;
        private Elvis payload;

        private Object readResolve() {
            // resolve 되기 전의 Elvis 인스턴스의 참조를 저장
            impresonator = payload;

            // favoriteSongs 필드에 맞는 타입의 객체 반환
            return new String[] { "A Fool Such as I" };
        }
        
        private static final long serialVersionUID = 0;
    }

    public static class ElvisImpresonator {
        // 진짜 Elvis 인스턴스로는 만들어질 수 없는 바이트 스트림!
        private static final byte[] serializedForm = {
            (byte)0xac, (byte)0xed, 0x00, 0x05, 0x73, 0x72, 0x00, 0x05,
            0x45, 0x6c, 0x76, 0x69, 0x73, (byte)0x84, (byte)0xe6,
            (byte)0x93, 0x33, (byte)0xc3, (byte)0xf4, (byte)0x8b,
            0x32, 0x02, 0x00, 0x01, 0x4c, 0x00, 0x0d, 0x66, 0x61, 0x76,
            0x6f, 0x72, 0x69, 0x74, 0x65, 0x53, 0x6f, 0x6e, 0x67, 0x73,
            0x74, 0x00, 0x12, 0x4c, 0x6a, 0x61, 0x76, 0x61, 0x2f, 0x6c,
            0x61, 0x6e, 0x67, 0x2f, 0x4f, 0x62, 0x6a, 0x65, 0x63, 0x74,
            0x3b, 0x78, 0x70, 0x73, 0x72, 0x00, 0x0c, 0x45, 0x6c, 0x76,
            0x69, 0x73, 0x53, 0x74, 0x65, 0x61, 0x6c, 0x65, 0x72, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x02, 0x00, 0x01,
            0x4c, 0x00, 0x07, 0x70, 0x61, 0x79, 0x6c, 0x6f, 0x61, 0x64,
            0x74, 0x00, 0x07, 0x4c, 0x45, 0x6c, 0x76, 0x69, 0x73, 0x3b,
            0x78, 0x70, 0x71, 0x00, 0x7e, 0x00, 0x02
        };
    }

    static Object deserialize(byte[] sf) {
        try {
            return new ObjectInputStream(new ByteArrayInputStream(sf)).readObject();
        }
        catch (IOException | ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static void main(String[] args) {
        // ElivsStrealer.impresonator를 초기화한 다음,
        // 진짜 Elvis(즉, Elvis.INSTANCE)를 반환한다.
        Elvis elvis = (Elvis) deserialize(ElvisImpresonator.serializedForm);
        Elvis impresonator = ElvisStealer.impresonator;

        elvis.printFavorites();
        impresonator.printFavorites();

        // Elivs를 따로 클래스파일로 작성하기 전까지는 테스트 할 수 없다...
    }

    public enum SafeElvis {
        INSTANCE; // 열거를 사용하면, 선언된 상수 외에 다른 객체가 없음을 자바가 보장해 준다.

        private String[] favoriteSongs = {
            "Hound Dog", "Heartbreak Hotel"
        };

        public void printFavorites() {
            System.out.println(Arrays.toString(favoriteSongs));
        }
    }
}