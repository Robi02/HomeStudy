// 2-10

package ch02;

public class FloatEx1 {

    public static void main(String[] args) {
        
        float f  = 9.12345678901234567890f;
        float f2 = 1.2345678901234567890f;
        double d = 9.12345678901234567890d;

        System.out.printf("     123456789012345678901234%n");
        System.out.printf("f  : %f%n", f); // 소수점 이하 6번째까지 출력 (default)
        System.out.printf("f  : %24.20f%n", f);
        System.out.printf("f2 : %24.20f%n", f2);
        System.out.printf("d  : %24.20f%n", d);

        /**
                 123456789012345678901234
            f  : 9.123457                   -> 6번째 자리가 7번째 자리에서 반올림됨
            f  :   9.12345695495605500000
            f2 :   1.23456788063049320000
            d  :   9.12345678901234600000
         */
    }
}
