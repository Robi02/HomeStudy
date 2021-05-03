package _ch09_bridge;

import java.util.Random;

public class RandomCountDisplay extends CountDisplay {

    public RandomCountDisplay(DisplayImpl impl) {
        super(impl);
    }
    
    public void randomDisplay(int times) {
        Random random = new Random();
        multiDisplay(random.nextInt(times));
    }
}
