/**
 * - 문제 설명 : 주식가격
 * 초 단위로 기록된 주식가격이 담긴 배열 prices가 매개변수로 주어질 때, 가격이 떨어지지 않은 기간은 몇 초인지를 return 하도록 solution 함수를 완성하세요.
 * 
 * - 제한사항
 * prices의 각 가격은 1 이상 10,000 이하인 자연수입니다.
 * prices의 길이는 2 이상 100,000 이하입니다.
 * 
 * - 입출력 예
 * prices	return
 * [1, 2, 3, 2, 3]
 */

import java.util.Arrays;

public class SAQLv2_006 {

    public int[] solution(int[] prices) {
        int[] rtAry = new int[prices.length];

        for (int i = 0; i < prices.length - 1; ++i) {
            int timeCnt = 0;
            
            for (int j = i + 1; j < prices.length; ++j) {
                ++timeCnt;

                if (prices[i] > prices[j]) {
                    break;
                }
            }

            rtAry[i] = timeCnt;
        }

        return rtAry;
    }

    public static void main(String[] args) {
        // ================================================================
        final SAQLv2_006 solution = new SAQLv2_006();
        final int[] prices = { 1, 2, 3, 2, 3 }; // [4, 3, 1, 1, 0]
        // ================================================================
        final long bgnTime = System.currentTimeMillis();
        final int[] results = solution.solution(prices);
        final long elapsedTime = System.currentTimeMillis() - bgnTime;
        // ================================================================
        System.out.println("Result : " + Arrays.toString(results));
        System.out.println("ElapsedTime : " + elapsedTime + "ms");
        // ================================================================
    }
}