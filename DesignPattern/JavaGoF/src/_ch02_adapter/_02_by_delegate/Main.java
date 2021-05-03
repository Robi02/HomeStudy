package _ch02_adapter._02_by_delegate;

public class Main {
    
    public static void main(String[] args) {
        Print p = new PrintBanner("Hello");
        p.printWeak();
        p.printStrong();
    }
}
