package _ch09_bridge;

public class Main {
    
    public static void main(String[] args) {
        Display d1 = new Display(new StringDisplayImpl("Hello, Korea."));
        Display d2 = new CountDisplay(new StringDisplayImpl("Hello, World."));
        CountDisplay d3 = new CountDisplay(new StringDisplayImpl("Hello, Universe."));

        d1.display();
        d2.display();
        d3.display();
        d3.multiDisplay(5);

        RandomCountDisplay d4 = new RandomCountDisplay(new StringDisplayImpl("Hello, Random."));
        d4.randomDisplay(10);
    }
}
