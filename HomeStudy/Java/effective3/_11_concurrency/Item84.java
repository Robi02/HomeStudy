package _11_concurrency;

public class Item84 {
    /**
     * [Item84] 프로그램의 도작을 스레드 스케줄러에 기대지 말라
     * 
     * [핵심]
     * 프로그램의 동작을 스레드 스케줄러에 기대지 말자.
     * 견고성과 이식성을 모두 해치는 행위다. 같은 이유로, Thread.yield와
     * 스레드 우선순위에 의존해서도 안 된다.
     * 이 기능들은 스레드 스케줄러에 제공하는 힌트일 뿐이다.
     * 스레드 우선순위는 이미 잘 동작하는 프로그램의 서비스 품질을 높이기 위해
     * 드물게 쓰일 수는 있지만, 간신히 동작하는 프로그램을 '고치는 용도'로 사용해서는 절대 안 된다.
     * 
     */

    public static class SlowCountDownLatch {
        
        private int count;

        public SlowCountDownLatch(int count) {
            if (count < 0)
                throw new IllegalArgumentException(count + " < 0"); 
            this.count = count;
        }

        public void await() {
            while (true) { // Busy waitting: 이런 코드 짜지 말자!
                synchronized (this) {
                    if (count == 0)
                        return;
                }
            }
        }

        public synchronized void countDown() {
            if (count != 0)
                count--;
        }
    }
}