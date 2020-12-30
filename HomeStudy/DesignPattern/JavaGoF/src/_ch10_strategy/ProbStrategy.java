package _ch10_strategy;

import java.util.Random;

public class ProbStrategy implements Strategy {
    
    private Random random;
    private int prevHandValue = 0;
    private int currentHandValue = 0;
    private int[][] history = {
        { 1, 1, 1 },
        { 1, 1, 1 },
        { 1, 1, 1 }
    };

    public ProbStrategy(int seed) {
        this.random = new Random(seed);
    }

    public Hand nextHand() {
        int bet = random.nextInt(getSum(this.currentHandValue));
        int handvalue = 0;

        if (bet < this.history[this.currentHandValue][0]) {
            handvalue = 0;
        }
        else if (bet < this.history[this.currentHandValue][0] + this.history[this.currentHandValue][1]) {
            handvalue = 1;
        }
        else {
            handvalue = 2;
        }

        this.prevHandValue = this.currentHandValue;
        this.currentHandValue = handvalue;
        return Hand.getHand(handvalue);
    }

    private int getSum(int hv) {
        int sum = 0;
        for (int i = 0; i < 3; ++i) {
            sum += this.history[hv][i];
        }
        return sum;
    }

    public void study(boolean win) {
        if (win) {
            // 이긴 경우: 이전에 낸 손과 이번에 '낸' 손의 승리로 기록
            this.history[this.prevHandValue][this.currentHandValue]++;
        }
        else {
            // 진 경우: 이전에 낸 손과 현재 이번에 '안낸' 손의 승리로 기록
            this.history[this.prevHandValue][(currentHandValue + 1) % 3]++;
            this.history[this.prevHandValue][(currentHandValue + 2) % 3]++;
        }
    }
}
