// 2-14. 실수형 간의 형변환

package ch02;

public class CastingEx3 {

    public static void main(String[] args) {
        
        float f = 9.1234567f;
        double d = 9.1234567;
        double d2 = (double)f;

        // 
        // [f]  -> 0|10000010   |00100011111100110101101
        // [d]  -> 0|10000000010|0010001111110011010110110111011100011111000110110100
        // [d2] -> 0|10000000010|0010001111110011010111000000000000000000000000000000

        System.out.printf("f  = %20.18f%n", f);
        System.out.printf("d  = %20.18f%n", d);
        System.out.printf("d2 = %20.18f%n", d2);
    }
}
