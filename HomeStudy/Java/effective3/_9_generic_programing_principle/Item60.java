package _9_generic_programing_principle;

import java.math.BigDecimal;

public class Item60 {
    /**
     * [Item60] 정확한 답이 필요하다면 float과 double은 피하라
     * 
     * [핵심]
     * 정확합 답이 필요한 계산에는 float이나 double을 피하라.
     * 소수점 추적은 시스템에 맡기고, 코딩 시의 불편함이나 성능 저하를 신경 쓰지 않겠다면
     * BigDecimal을 사용하라. BigDecimal이 제공하는 여덟 가지 반올림 모드를 이용하여
     * 반올림을 완벽히 제어 할 수 있다.
     * 법으로 정해진 반올림을 수행해야 하는 비즈니스 계산에서 아주 편리한 기능이다.
     * 반면, 성능이 중요하고 소수점을 직접 추적할 수 있고 숫자가 너무 크지 않다면 int나 long을 사용하라.
     * 숫자를 아홉 자리 십진수로 표현할 수 있다면 int를 사용하고, 열여덟 자리 십진수로 표현할 수 있다면 long을 사용하라.
     * 열여덟 자리를 넘어가면 BigDecimal을 사용해야 한다.
     * 
     * -> Integer.Max_VALUE = 2147483647 (열자리 수, 맨 앞자리가 2가 최대값이라 아홉자리 활용 가능)
     * -> Long.MAX_VALUE = 9223372036854775807 (열아홉 자리 수, 두번째 자리가 2라 열여덟 자리 활용 가능)
     * 
     */

    public static void main(String[] args) {
        {
            double funds = 1.00;
            int itemsBought = 0;
            for (double price = 0.10; funds >= price; price += 0.10) {
                funds -= price; // 0.90, 0.70, 0.40 0.00
                ++itemsBought; // 1, 2, 3, 4
            }
            System.out.println("Sol#1 double 사용.");
            System.out.println(itemsBought + "개 구입");
            System.out.println("잔돈(달러):" + funds);

            // 출력 결과가 4 / 0.00로 생각되는가?
            // 부동소수점 정밀도로 인해 3 / 0.399999999999999 가 출력되는 기가막힌 모습을 보인다.
            // 금융 계산에서 float과 double은 정말 치명적인 결과로 끝날 수 있음을 명심하라.
        }

        {
            final BigDecimal TEN_CENTS = new BigDecimal(".10");
            int itemsBought = 0;
            BigDecimal funds = new BigDecimal("1.00");
            for (BigDecimal price = TEN_CENTS; funds.compareTo(price) >= 0; price = price.add(TEN_CENTS)) {
                funds = funds.subtract(price);
                ++itemsBought;
            }
            System.out.println("Sol#2 BigDecimal 사용.");
            System.out.println(itemsBought + "개 구입");
            System.out.println("잔돈(달러):" + funds);

            // 이번에는 정상적으로 출력되지만...
            // 기본 타입보다 훨씬 쓰기 불편하고 느리다.
        }

        {
            int itemsBought = 0;
            int funds = 100;
            for (int price = 10; funds >= price; price += 10) {
                funds -= price;
                ++itemsBought;
            }
            System.out.println("Sol#3 *100배율 int 사용.");
            System.out.println(itemsBought + "개 구입");
            System.out.println("잔돈(달러):" + funds * 100);

            // 연산 배율을 바꿔서 처리한다.
            // 정확한 배율을 통일시키는 것이 중요하다!
            // 예전부터 내가 종종 사용하던 기법이지?
        }
    }
}