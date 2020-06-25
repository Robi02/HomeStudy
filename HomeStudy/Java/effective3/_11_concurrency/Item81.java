package _11_concurrency;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

public class Item81 {
    /**
     * [Item81] wait와 notify보다는 동시성 유틸리티를 애용하라
     * 
     * [핵심]
     * wait과 notify를 직접 사용하는 것을 동시성 '어셈블리 언어'
     * 로 프로그래밍하는 것에 비유할 수 있다.
     * 반면 java.util.concurrent는 고수준 언어에 비유할 수 있다.
     * 코드를 새로 작성한다면 wait와 notify를 쓸 이유가 거의(어쩌면 전혀) 없다.
     * 이들을 사용하는 레거시 코드를 유지보수해야 한다면 wait는 항상 표준 관용구에
     * 따라 whlie문 안에서 호출하도록 하자. 일반적으로 notify보다는 notifyAll을 사용해야 한다.
     * 혹시라도 notify를 사용한다면 응답 불가 상태에 빠지지 않도록 각별히 주의하자.
     * 
     */

    // [1] 동시성 유틸리티를 사용한 동시 실행 시간을 재는 간단한 프레임워크
    public static long time(Executor executor, int concurrency, Runnable action) throws InterruptedException {
        CountDownLatch ready = new CountDownLatch(concurrency);
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch done  = new CountDownLatch(concurrency);

        for (int i = 0; i < concurrency; ++i) {
            executor.execute(() -> {
                // 타이머에게 준비를 마쳤음을 알림
                ready.countDown();
                try {
                    // 모든 작업자 스레드가 준비될 때 까지 대기
                    start.await();
                    action.run();
                }
                catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                finally {
                    // 타이머에게 작업을 마쳤음을 알림
                    done.countDown();
                }
            });
        }

        ready.await(); // 모든 작업자가 준비될 때 까지 대기
        long startNanos = System.nanoTime();
        start.countDown(); // 작업자들을 깨움
        done.await(); // 모든 작업자가 일이 끝나기를 대기
        return System.nanoTime() - startNanos;
    }

    public static void main(String[] args) {
        // [2] wait 메서드를 사용하는 표준 방식
        // synchronized (obj) {
        //    while (<조건이 충족되지 않았음>)
        //        obj.wait(); // 락을 걸고, 깨어나면 다시 잡는다
        //
        //    // 조건이 충족됐을 때의 동작을 수행
        //    ...
        //}
    }
}