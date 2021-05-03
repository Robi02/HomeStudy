
/**
 * - 문제 설명 : H-Index
 * H-Index는 과학자의 생산성과 영향력을 나타내는 지표입니다.
 * 어느 과학자의 H-Index를 나타내는 값인 h를 구하려고 합니다.
 * 위키백과에 따르면, H-Index는 다음과 같이 구합니다.
 * 어떤 과학자가 발표한 논문 n편 중, h번 이상 인용된 논문이 h편 이상이고
 * 나머지 논문이 h번 이하 인용되었다면 h가 이 과학자의 H-Index입니다.
 * 어떤 과학자가 발표한 논문의 인용 횟수를 담은 배열 citations가 매개변수로 주어질 때,
 * 이 과학자의 H-Index를 return 하도록 solution 함수를 작성해주세요.
 * 
 * - 제한사항
 * 과학자가 발표한 논문의 수는 1편 이상 1,000편 이하입니다.
 * 논문별 인용 횟수는 0회 이상 10,000회 이하입니다.
 * 
 */

import java.util.Arrays;
import java.util.Random;

public class SortLv2_003 {
    
    public int solution(int[] citations) {
        Arrays.sort(citations);
        
        int hIndex = Integer.MIN_VALUE;
 
        for (int i = citations.length - 1; i > -1; --i) {
            // 어떤 과학자가 발표한 논문 n편 중, h번 이상 인용된 논문이 h편 이상
            // 나머지 논문이 h번 이하 인용되었다면 h가 이 과학자의 H-Index입니다.
            // (H-Index : i = 0 to citations.length, min(f(i), i) list's max value!)
            
            int hIndexCanditate = Math.min(citations[i], citations.length - i);

            if (hIndexCanditate > hIndex) {
                hIndex = hIndexCanditate;
            }
        }

        return hIndex;
    }

    public static void main(String[] args) {
        Random rand = new Random();
        // ================================================================
        final SortLv2_003 solution = new SortLv2_003();
        final int[][] citationsGrp = {{ 3, 0, 6, 1, 5 },    // result: 3
                                      { 22, 42 },           // result: 2
                                      { 10, 8, 5, 4, 3 }    // result: 4
        };
        /*int[] citations = new int [1000];
        for (int i = 0; i < citations.length; ++i) {
            citations[i] = rand.nextInt(10000);
        }*/
        // ================================================================
        final long bgnTime = System.currentTimeMillis();
        // ================================================================
        for (int[] citations : citationsGrp) {
            final int result = solution.solution(citations);
            System.out.println("Result : " + result);
        }
        // ================================================================
        final long elapsedTime = System.currentTimeMillis() - bgnTime;
        System.out.println("ElapsedTime : " + elapsedTime + "ms");
    }
}