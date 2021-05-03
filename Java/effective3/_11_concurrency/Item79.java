package _11_concurrency;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import _4_class_and_interface.Item18.ForwardingSet;

public class Item79 {
    /**
     * [Item79] 과도한 동기화는 피하라
     * 
     * [핵심]
     * 교착상태와 데이터 훼손을 피하려면 동기화 영역 안에서 외계인 메서드를 절대 호출하지 말자.
     * 일반화해 이야기하면, 동기화 영역 안에서의 작업은 최소한으로 줄이자.
     * 가변 클래스를 설계할 때는 스스로 동기화해야 할지 고민하자.
     * 멀티코어 세상인 지금은 과도한 동기화를 피하는 게 과거 어느 때보다 중요하다.
     * 합당한 이유가 있을 때만 내부에서 동기화하고, 동기화했는지 여부를 문서에 명확히 밝히자(Item82).
     * 
     * 1. 동기화 메서드나 동기화 블록 안에서는 제어를 절대로 클라이언트에 양도하면 안 된다.
     *    (재정의할 수 있는 메서드나 클라이언트가 넘겨준 함수 객체등을 호출하지 말라)
     * 
     * 2. 동기화 영역에서는 가능한 한 일을 적게 해야 한다.
     * 
     */


    @FunctionalInterface public static interface SetObserver<E> {
        public void added(ObservableSet<E> set, E element);
    }

    public static class ObservableSet<E> extends ForwardingSet<E> {
        public ObservableSet(Set<E> set) { super(set); }
        
        private final List<SetObserver<E>> observers = new ArrayList<>();

        public void addObserver(SetObserver<E> observer) {
            synchronized (observers) {
                observers.add(observer);
            }
        }

        public boolean removeObserver(SetObserver<E> observer) {
            synchronized (observers) {
                return observers.remove(observer);
            }
        }

        private void notifyElementAdded(E element) {
            synchronized (observers) {
                for (SetObserver<E> observer : observers)
                    observer.added(this, element); // 위험: 동기화 블록 안에서 외계인 메서드 호출!!!
            }

            // -> [3] 해결책: 외계인 메서드를 동기화 블록 바깥으로 옮긴다
            // List<SetObserver<E>> snapshot = null;
            // synchronized (observers) {
            //     snapshot = new ArrayList<>(observers);
            // }
            // for (SetObserver<E> observer : snapshot)
            //    observer.added(this, element);
        }

        @Override public boolean add(E element) {
            boolean added = super.add(element);
            if (added)
                notifyElementAdded(element);
            return added;
        }

        @Override public boolean addAll(Collection<? extends E> c) {
            boolean result = false;
            for (E element : c)
                result |= add(element); // notifyElementAdded 호출
            return result;
        }
    }

    public static void main(String[] args) {
        // [1] 정상적으로 0~99 까지 출력한다...
        ObservableSet<Integer> set = new ObservableSet<>(new HashSet<>());

        set.addObserver((s, e) -> System.out.println(e));

        for (int i = 0; i < 100; ++i) {
            set.add(i);
        }

        // [2] 하지만 이렇다면...?
        set = new ObservableSet<>(new HashSet<>());

        set.addObserver(new SetObserver<Integer>() {
            public void added(ObservableSet<Integer> s, Integer e) {
                System.out.println(e);
                if (e == 23) {
                    s.removeObserver(this);
                }
            }
        });

        for (int i = 0; i < 100; ++i) {
            set.add(i);
        }

        // 23까지 출력 후 종료할것 같았지만, ConcurrentModificationException이 발생한다!
    }
}