package _ch10_strategy;

public class Hand {
    
    public static final int HANDVALUE_MUK = 0;
    public static final int HANDVALUE_ZZI = 1;
    public static final int HANDVALUE_PAA = 2;

    public static final Hand[] hand = {
        new Hand(HANDVALUE_MUK),
        new Hand(HANDVALUE_ZZI),
        new Hand(HANDVALUE_PAA)
    };

    private static final String[] name = {
        "묵", "찌", "빠"
    };

    private int handvalue;

    public Hand(int handvalue) {
        this.handvalue = handvalue;
    }

    public static Hand getHand(int handvalue) {
        return hand[handvalue];
    }

    public boolean isStrongerThan(Hand h) {
        return fight(h) == 1;
    }

    public boolean isWeakerThan(Hand h) {
        return fight(h) == -1;
    }

    private int fight(Hand h) {
        if (this.handvalue == h.handvalue) {
            return 0; // this와 h가 비김
        }
        else if ((this.handvalue + 1) % 3 == h.handvalue) {
            return 1; // this가 이김
        }
        else {
            return -1; // this가 짐
        }
    }

    @Override
    public String toString() {
        return name[this.handvalue];
    }
}
