import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * - 문제 설명 : 더 맵게
 * 매운 것을 좋아하는 Leo는 모든 음식의 스코빌 지수를 K 이상으로 만들고 싶습니다.
 * 모든 음식의 스코빌 지수를 K 이상으로 만들기 위해 Leo는 스코빌 지수가 가장 낮은
 * 두 개의 음식을 아래와 같이 특별한 방법으로 섞어 새로운 음식을 만듭니다.
 * 섞은 음식의 스코빌 지수 = 가장 맵지 않은 음식의 스코빌 지수 + (두 번째로 맵지 않은 음식의 스코빌 지수 * 2)
 * Leo는 모든 음식의 스코빌 지수가 K 이상이 될 때까지 반복하여 섞습니다.
 * Leo가 가진 음식의 스코빌 지수를 담은 배열 scoville과 원하는 스코빌 지수 K가 주어질 때,
 * 모든 음식의 스코빌 지수를 K 이상으로 만들기 위해 섞어야 하는 최소 횟수를 return 하도록 solution 함수를 작성해주세요.
 * 
 * - 제한 사항
 * scoville의 길이는 1 이상 1,000,000 이하입니다.
 * K는 0 이상 1,000,000,000 이하입니다.
 * scoville의 원소는 각각 0 이상 1,000,000 이하입니다.
 * 모든 음식의 스코빌 지수를 K 이상으로 만들 수 없는 경우에는 -1을 return 합니다.
 * 
 */

public class HeapLv2_001 {
    
    public int solution(int[] scoville, int K) {
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>();

        for (int i = 0; i < scoville.length; ++i) {
            pq.add(scoville[i]);
        }

        // System.out.println(Arrays.toString(pq.toArray()));

        int rtCnt = 0;
        int mixedScoville = 0;
        boolean jobFinished = false;
        
        while (true) {
            if (pq.size() < 2) {
                if (pq.size() == 1 && pq.remove() >= K) {
                    return 0;
                }
                
                return -1;
            }

            jobFinished = true;

            for (Integer sc : pq) {
                if (sc < K) {
                    jobFinished = false;
                    break;
                }
            }

            if (jobFinished) {
                break;
            }

            mixedScoville = pq.remove() + (pq.remove() * 2);
            pq.add(mixedScoville);
            ++rtCnt;
            // System.out.println(Arrays.toString(pq.toArray()));
        }

        return rtCnt;
    }

    public static void main(String[] args) {
        // ================================================================
        final HeapLv2_001 solution = new HeapLv2_001();
        final int[] scoville = new int[] { 3, 3, 3, 3, 3, 3 };
        final int K = 3;
        // ================================================================
        final long bgnTime = System.currentTimeMillis();
        final int result = solution.solution(scoville, K);
        final long elapsedTime = System.currentTimeMillis() - bgnTime;
        // ================================================================
        System.out.println("Result : " + result);
        System.out.println("ElapsedTime : " + elapsedTime + "ms");
        // ================================================================
    }
}