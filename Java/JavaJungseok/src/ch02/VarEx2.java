// 2-2

package ch02;

public class VarEx2 {

    public static void main(String[] args) {

        int x = 10;
        int y = 20;
        int temp = 0;

        System.out.println("x:" + x + ", y: " + y);

        temp = x;
        x = y;
        y = temp;

        System.out.println("x:" + x + ", y: " + y);

        x ^= y;
        y ^= x;
        x ^= y;

        System.out.println("x:" + x + ", y: " + y);

        x += y;
        y = x - y;
        x -= y;

        System.out.println("x:" + x + ", y: " + y);

        float d = 0x1p1f;
    }
}