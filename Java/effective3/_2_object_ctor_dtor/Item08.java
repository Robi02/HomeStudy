package _2_object_ctor_dtor;

public class Item08 {
    /**
     * [Item08] finalizer와 cleaner 사용을 피하라
     * 
     * [핵심]
     * cleaner(자바 8까지는 finalizer)는 안전망 역할이나
     * 중요하지 않은 네이티브 자원 회수용으로만 사용하자.
     * 물론 이런 경우라도 불확실성과 성능 저하에 주의해야 한다.
     * 
     * 1) f&c(finalizer and cleaner)는 심각한 성능 문제를 동반한다.
     *  -> AutoCloseable 객체는 12ns, finalizer는 550ms, cleaner는 500ms 정도로 느리다.
     * 
     * 2) finalizer를 사용한 클래스는 finalizer 공격에 노출되어 심각한 보안 문제를 야기할 수 있다.
     *  -> 생성자나 직렬화 과정에서 예외가 발생하면, 이 생성되다 만 객체에서 악의적인 하위 클래스의
     *     finalizer가 수행될 수 있게 된다. 심지어 이 finalizer는 정적 필드에 자신의 참조를 할당하여
     *     GC되지 않도록 해버릴 수도 있다! 이를 막으려면, final class가 아닌 클래스를 만들었다면
     *     finalizer를 final 메서드로 선언, 빈 메서드로 둔다. ( final protected void finalize(){ } )
     *     이러면 하위 클래스에서의 재선언을 막아버릴 수 있다.
     * 
     * 3) AutoCloseable을 구현하여 사용하는게 가장 현명한 방법이다.
     * 
     * [예외]
     * 1) 자원의 소유자가 close를 호출하지 않는 것에 대비한 안전망 정도로는 사용할 수 있다.
     *    (Ex. FileInputStream, FileOutputStream, ThreadPoolExecutor)
     * 
     * 2) 네이티브 피어(일반 자바 객체가 네이티브 메서드를 통해 기능을 위임한 네이티브 객체)와\
     *    연결된 자원을 회수할 때 사용할 수 있다. 단, 성능 저하를 감당할 수 있어야 한다.
     * 
     */

     public static class Room implements AutoCloseable {
        /*
        private static final Cleaner cleaner = Cleaner.create(); <- Java 1.9

        // 청소가 필요한 자원. 절대 Room을 참조해서는 안 된다!
        private static class State implements Runnable {
            int numJunkPiles;

            State(int numJunkPiles) {
                this.numJunkPiles = numJunkPiles;
            }

            @Override public void run() {
                System.out.println("방 청소");
                numJunkPiles = 0;
            }
        }

        // 방의 상태. cleanable과 공유한다.
        private final State state;

        // cleanable 객체. 수거 대상이 되면 방을 청소한다.
        private final Cleaner.Cleanable cleanable;

        public Room(int numJunkPiles) {
            state = new State(numJunkPiles);
            cleanable = cleaner.register(this, state);
        }
        */

        @Override public void close() {
            // cleanable.clean();
        }
     }
}