package _ch17_observer;

public class DigitObserver implements Observer {

    @Override
    public void update(NumberGenerator generator) {
        System.out.println("DigitObserver:" + generator.getNumber());
        try {
            Thread.sleep(100L);
        }
        catch (InterruptedException e) {
            
        }
    }    
}
