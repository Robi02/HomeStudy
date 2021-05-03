package _2_object_ctor_dtor;

public class Item04 {
    /**
     *  [Item04] 인스턴스화를 막으려거든 private 생성자를 사용하라
     * 
     *  [핵심]
     *  Util클래스처럼 static 메서드로만 이루어진 클래스가 존재할 수 있다.
     *  이 경우, 해당 클래스를 인스턴스화 하지 못하도록 private 생성자를 하나 만들어 주는게 좋다.
     *  그렇지 않는다면 사용자가 컴파일러에 의해 자동 생성된 public 생성자를 호출하여
     *  올바르지 않은 접근을 할 확률이 매우 높아지기 때문이다.
     *  abstract 클래스로 선언하면 안될까? 물론 안된다. 해당 클래스를 상속해버리면 그만이기 때문이다.
     *  오히려 사용자가 '이 유틸 클래스는 상속해서 사용해야 하는구나!'와 같은 오해를 불러일으킬 수 있다.
     *  따라서, 클래스의 상속과 인스턴스화를 막으려면 꼭 private 생성자를 하나 선언해 주자.
     * 
     */

    public static class Util {
        private Util() {
            throw new AssertionError();
        }

        public static int add(int a, int b) {
            return a + b;
        }

        // ...
    }

    public static void main(String[] args) {
        int sum = Util.add(1, 2);
        
        // Item04.Util util = new Item04.Util();
        // ↑Inner클래스이기에 호출이 가능한 듯 하다.
        // 다른 파일의 클래스라면 컴파일 에러가 발생했을 것이다.
        // 주석을 풀고 실행하면 AssertionError예외가 던져진다.
    }
}