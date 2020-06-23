package _11_concurrency;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Item78 {
    /**
     * [Item78] 공유 중인 가변 데이터는 동기화해 사용해라
     * 
     * [핵심]
     * 여러 스레드가 가변 데이터를 공유한다면 그 데이터를 읽고 쓰는 동작은 반드시 동기화 해야 한다.
     * 동기화하지 않으면 한 스레드가 수행한 변경을 다른 스레드가 보지 못할 수도 있다.
     * 공유되는 가변 데이터를 동기화하는 데 실패하면 응답 불가 상태에 빠지거나
     * 안전 실패로 이어질 수 있다. 이는 디버깅 난이도가 가장 높은 문제에 속한다.
     * 간헐적이거나 특정 타이밍에만 발생할 수도 있고, VM에 따라 현상이 달라지기도 한다.
     * 배타적 실행은 필요 없고 스레드끼리의 통신만 필요하다면 volatile 한정자만으로 동기화할 수 있다.
     * 다만 올바로 사용하기가 까다롭다.
     * 
     */

    public static class StopThread {
        private static boolean stopRequested;
        
        // [3] volatile로 선언하면 동기화를 생략할 수 있다
        //     volatile로 선언된 변수는 항상 가장 최근에 기록된 값을 읽게 됨을 보장한다
        //  -> 즉, 읽기에 대해서만 보장하므로 (++i) 같은 '읽고 쓰기' 혹은 '쓰기'가
        //     연관된 행동에 대해서는 보장하지 못한다. 따라서, 사용에 주의가 필요하다.
        // private static volatile boolean stopRequested2;

        // [4] java.util.concurrent.atomic을 사용할 수도 있다
        //     (성능도 동기화 버전버다 우수하다!)
        // private static AtomicBoolean stopRequested3 = new AtomicBoolean(false);

        public static void wrong() throws InterruptedException {
            // [1] 잘못된 코드 - 이 프로그램은 무한히 실행된다
            Thread bkThread = new Thread(() -> {
                int i = 0;
                // 이 코드를 JVM에 따라 아래와 같이 최적화 해버릴 수도 있다
                while (!stopRequested)
                    ++i;
                // OpenJDK VM이 실제로 적용하는 끌어올리기(hoisting)라는 최적화 기법
                // if (!stopRequested)
                //     while (true)
                //         ++i;
            });
            bkThread.start();

            TimeUnit.SECONDS.sleep(1L);
            stopRequested = true;
        }

        // [2] 적절히 동기화해 스래드를 정상 종료하는 코드
        //  -> 읽기와 쓰기 모두 동기화되지 않으면 동작이 보장되지 않는다
        public static synchronized void requestStop() {
            stopRequested = true;
        }

        public static synchronized boolean stopRequested() {
            return stopRequested;
        }

        public static void ok() throws InterruptedException {
            Thread bkThread = new Thread(() -> {
                int i = 0;
                while (!stopRequested())
                    ++i;
            });
            bkThread.start();

            TimeUnit.SECONDS.sleep(1L);
            requestStop();
        }


        public static void main(String[] args) throws InterruptedException {
            wrong(); // [Ctrl + C]로 강제종료하기 전까지 계속 수행된다...
            ok();
        }
    }
}