package _ch18_memento;

import _ch18_memento.game.Gamer;
import _ch18_memento.game.Memento;

public class Main {
    
    public static void main(String[] args) {
        Gamer gamer = new Gamer(100);
        Memento memento = gamer.createMemento();

        for (int i = 0; i < 100; ++i) {
            System.out.println("==== " + i);
            System.out.println("현상:" + gamer);

            gamer.bet();

            System.out.println("소지금은" + gamer.getMoney() + "원이 되었습니다.");

            if (gamer.getMoney() > memento.getMoney()) {
                System.out.println(" (많이 증가했으므로 현재 상태를 저장하자)");
                memento = gamer.createMemento();
            }
            else if (gamer.getMoney() < memento.getMoney() / 2) {
                System.out.println(" (많이 감소했으므로 이전 상태로 복원하자)");
                gamer.restoreMemento(memento);
            }

            try {
                Thread.sleep(100L);
            }
            catch (InterruptedException e) {

            }
            System.out.println();
        }
    }
}
