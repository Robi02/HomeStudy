package _11_concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Item80 {
    /**
     * [Item80] 스레드보다는 실행자, 태스크, 스트림을 애용하라
     * 
     * [핵심]
     * 병렬 프로그래밍을 할 때, 스레드를 직접 생성하는 방식보다는
     * ExecutorService를 사용하는것이 더 좋다.
     * 단순한 단발성 액션이라면 [1]을, 스레드 풀이 필요하다면 [2]나 [3]을 고려하자.
     * 태스크에는 Runnable과 Callable이 있는데, Callable은 값을 반환하고 임의의 예외도 던질 수 있다.
     * Java7부터는 ForkJoinTask를 사용할 수 있는데, 작은 하위 태스크로 나누어서 CPU를 최대한 활용할 수 있다.
     * 병렬 프로그래밍에 대한 지식은 『자바 병렬 프로그래밍』(에이콘출판사, 2008)이라는 책을 참고하자.
     * 
     */

    public static void main(String[] args) {
        ExecutorService exec = Executors.newSingleThreadExecutor();
        exec.execute(() -> {
            System.out.println("Hello from thread #" + Thread.currentThread().getId() + "!");
            exec.shutdown();
        });

        ExecutorService exec2 = Executors.newFixedThreadPool(10);
        ThreadPoolExecutor exec3;
    }
}