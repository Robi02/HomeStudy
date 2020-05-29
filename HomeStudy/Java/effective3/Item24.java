import java.util.AbstractSet;
import java.util.Iterator;

public class Item24 {
    /**
     * [Item24] 멤버 클래스는 되도록 static으로 만들라
     * 
     * [핵심]
     * 중첩 클래스에는 네 가지가 있으며, 각각의 쓰임이 다르다.
     * 메서드 밖에서도 사용해야 하거나 메서드 안에 정의하기엔 너무 길다면 멤버 클래스로 만든다.
     * 멤버 클래스의 인스턴스 각각이 바깥 인스턴스를 참조한다면 비정적(non-static)으로,
     * 그렇지 않으면 정적(static)으로 만들자. 중첩 클래스가 한 메서드 안에서만 쓰이면서
     * 그 인스턴스를 생성하는 지점이 단 한 곳이고 해당 타입으로 쓰기에 적합한 클래스나 인터페이스가
     * 이미 있다면 익명(anonymous) 클래스로 만들고, 그렇지 않으면 지역(local) 클래스로 만들자.
     * 
     * [중첩 클래스의 종류]
     * 1) 정적 멤버 클래스
     * 2) (비정적) 멤버 클래스
     * 3) 익명 클래스
     * 4) 지역 클래스
     * 
     * 
     */

    private int outerClassInt;
    private static int outerClassStaticInt;

    // 1) 정적 멤버 클래스
    private static class StaticInnerClass {
        public void accessTest() {
            // 바깥 클래스의 private static 멤버에 접근할 수 있다.
            Item24.outerClassStaticInt = 0;
        }
    }

    // 2) 비정적 멤버 클래스 예시
    private class InnerClass {
        public void accessTest() {
            // 바깥 클래스의 private 멤버에 접근할 수 있다.
            outerClassInt = 0;
        }

        // 개념상 중첩 클래스의 인스턴스가 바깥 인스턴스와 독립적으로
        // 존재할 수 있다면 정적 멤버 클래스로 만들어야 한다.

        // InnerClass가 인스턴스화 될 때 Item24와 InnerClass와의 관계가 확립되고
        // 더 이상 변경할 수 없다. 일반적으로 바깥 클래스의 인스턴스 메서드에서
        // InnerClass(비정적 멤버 클래스)의 생성자를 호출할 때 자동으로 만들어지는게
        // 보통이지만, 드물게는 직접 호출해 수동으로 만들기도 한다.
        // 이 정보는 "비정적 멤버 클래스의 인스턴스 안"에 만들어져서 메모리 공간과 생성시간을 차지한다.

        // 따라서, 멤버 클래스에서 바깥 인스턴스에 접긓라 일이 없다면 무조건 static을 붙여서 정적 멤버 클래스로 만들자!
    }

    // 2) 비정적 멤버 클래스 - 일반적으로 어댑터를 정의할 때 자주 쓰인다
    public class MySet<E> extends AbstractSet<E> {
        @Override public Iterator<E> iterator() {
            return new MyIterator();
        }

        private class MyIterator implements Iterator<E> {
            // priavte 정적 멤버 클래스는 바깥 클래스가 표현하는 객체의 한 부분(구성요소)를 나타낼 때 쓴다.

            @Override
            public boolean hasNext() {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public E next() {
                // TODO Auto-generated method stub
                return null;
            }
            
        }

        @Override
        public int size() {
            // TODO Auto-generated method stub
            return 0;
        }
    }

    private static final int privateStaticFinalInt = 0;
    private final int privateFinalInt = 0;
    private int privateInt = 0;

    public static void main(String[] args) {
        // 3) 익명 클래스 (주로 정적 팩터리 메서드를 구현할 때 사용할 수 있다. (Item20: intArrayAsList 참고))
        // 제약: 선언 지점에서만 인스턴스 생성이 가능하다.
        //       instnaceof 검사나 클래스의 일므이 필요한 작업을 수행할 수 없다.
        //       여러 인터페이스 구현이 불가능하고, 인터페이스 구현과 동시에 다른 클래스 상속이 불가하다.
        //       익명 클래스가 상위 타입에서 상속한 멤버 외에는 호출할 수 없다.
        //       표현식 중간에 등장하므로 가독성이 떨어질 수 있다.
        //
        // -> 이러한 제약들로 인해 람다가 지원된 이후로 자리를 물려주게 되었다.
        //
        Item24 i24 = new Item24() {
            @Override
            public String toString() {
                System.out.println(privateStaticFinalInt);  // 접근 가능
                //System.out.println(privateFinalInt);      // 접근 불가
                //System.out.println(privateInt);           // 접근 불가

                return super.toString();
            }
        };

        // 4) 지역 클래스
        class LocalClass {
            private int privateInt2;
            // private static int privateStaticInt; // 정적 멤버를 가질 수 없다

            public LocalClass() {
                this.privateInt2 = privateStaticFinalInt; // 접근 가능
                // this.privateInt2 = privateFinalInt;    // 접근 불가
                // this.privateInt2 = privateInt;         // 접근 불가
            }
        }
    }
}