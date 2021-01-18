package _ch20_flyweight;

public class Main {
    
    public static void main(String[] args) {
        if (args.length == 0) {
            args = new String[] { "1234567890-123" };
        }

        BigString bs = new BigString(args[0]);
        bs.print();
    }
}
