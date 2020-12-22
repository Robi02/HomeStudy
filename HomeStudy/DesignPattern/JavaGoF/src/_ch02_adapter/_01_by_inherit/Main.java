package _ch02_adapter._01_by_inherit;

public class Main {
    
    public static void main(String[] args) {
        Print p = new PrintBanner("Hello");
        p.printWeak();
        p.printStrong();
    }
}
