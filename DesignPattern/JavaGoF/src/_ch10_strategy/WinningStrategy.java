package _ch10_strategy;

import java.util.Random;

public class WinningStrategy implements Strategy {
    
    private Random random;
    private boolean won = false;
    private Hand prevHand;
    
    public WinningStrategy(int seed) {
        this.random = new Random(seed);
    }

    public Hand nextHand() {
        if (!this.won) {
            this.prevHand = Hand.getHand(random.nextInt(3)); // 졌으면 무작위로 냄
        }
        return this.prevHand; // 이겼으면 똑같이 냄
    }

    public void study(boolean win) {
        this.won = win;
    }
}
