package _2_object_ctor_dtor;

public class Item03 {
    /**
     *  [Item03] private 생성자나 열거 타입으로 싱글턴임을 보증하라
     * 
     *  [싱글턴 단점]
     *  클래스를 싱글턴으로 만들면 이를 사용하는 클라이언트를 테스트하기가 어려워질 수 있다.
     *  
     *  [핵심]
     *  대부분 상황에서는 원소가 하나뿐인 열거 타입이 싱글턴을 만드는 가장 좋은 방법이다.
     *  (단, 만드려는 싱글턴이 Enum외의 클래스를 상속해야 한다면 불가능한 방식이다.)
     * 
     */

    // [1] public static final 필드 방식의 싱글턴
    // 1. private생성자는 기본적으로 Elvis1.INSTANCE를 초기화할 때 딱 한번만 호출된다.
    // 2. 단, 권한이 있는 클라이언트가 리플랙션API(AccessibleObject.setAccessible)을 사용하여 private생성자 호출이 가능하다.
    //    이를 막으려면 생성자를 수정하여 두번째 객체가 생성되려 할 때 예외를 던지면 된다.
    public static class Elvis1 {
        public static final Elvis1 INSTNNCE = new Elvis1();

        private Elvis1() {}
        public void leaveTheBuilding() {}

        private Object readResolve() {
            return Elvis1.INSTNNCE;
        }
    }

    // [2] 정적 팩터리 방식의 싱글턴
    // 1. 리플랙션API를 제외하고는 두 번째 객체가 생성되지 않는다.
    // 2. 마음이 바뀌면 API를 바꾸지 않고도 싱글턴이 아니게 변경할 수 있다. (장점)
    // 3. 원한다면 정적 팩터리를 제네릭 싱글턴 팩터리로 만들 수 있다. (장점)
    // 4. 정적 팩터리의 메서드 참조를 공급자로 사용할 수 있다. (Elvis3::getInstance를 Supplier<Elvis3>로 사용...) (장점)
    // 2~4의 장점이 필요하지 않다면 [1]번 방식의 싱글턴이 더 좋다.
    public static class Elvis2 {
        private static final Elvis2 INSTNACE = new Elvis2();

        private Elvis2() {}
        public static Elvis2 getInstance() { return Elvis2.INSTNACE; }
        public void leaveTheBuilding() {}

        // [1,2] 방식의 방식의 클래스를 직렬화(Serializable)하려 한다면 모든 인스턴스 필드를 일시적(transient)이라고 선언하고
        // readResolve 메서드를 제공해야 한다. 그렇지 않으면 중복된 가짜 Elvis가 생성될 것이다.
        // 싱글턴임을 보장해주는 readResolve 메서드
        private Object readResolve() {
            // 진짜 Elvis2를 반환하고, 가짜 Elvis는 가비지 컬렉터에게 맡긴다
            return Elvis2.INSTNACE;
        }
    }

    // [3] 열거 타입 방식의 싱글턴 - 바람직한 방법
    // 1. public필드 방식과 비슷하지만, 더 간결하고, 추가 노력 없이 직렬화할 수 있다.
    // 2. 심지어 아주 복잡한 직렬화 상황이나 리플렉션 공격에서도 제 2의 인스턴스가 생기는 일을 완벽히 막아낸다.
    // 단, 생성하려면 싱글턴이 Enum외의 클래스를 상속해야 한다면 사용할수 없는 방식이다.
    public static enum Elvis3 {
        INSTNNCE;

        public void leaveTheBuilding() {}
    }

     public static void main(String[] args) {
        Elvis1 e1 = Elvis1.INSTNNCE;
        Elvis2 e2 = Elvis2.getInstance();
        Elvis3 e3 = Elvis3.INSTNNCE;

        e1.leaveTheBuilding();
        e2.leaveTheBuilding();
        e3.leaveTheBuilding();
     }
}