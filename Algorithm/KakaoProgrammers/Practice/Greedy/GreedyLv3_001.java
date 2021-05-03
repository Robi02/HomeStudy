
/**
 * - 문제 : 체육복
 * 
 * 점심시간에 도둑이 들어, 일부 학생이 체육복을 도난당했습니다.
 * 다행히 여벌 체육복이 있는 학생이 이들에게 체육복을 빌려주려 합니다.
 * 학생들의 번호는 체격 순으로 매겨져 있어, 바로 앞번호의 학생이나 바로
 * 뒷번호의 학생에게만 체육복을 빌려줄 수 있습니다.
 * 예를 들어, 4번 학생은 3번 학생이나 5번 학생에게만 체육복을 빌려줄 수 있습니다.
 * 체육복이 없으면 수업을 들을 수 없기 때문에 체육복을 적절히 빌려 최대한 많은 학생이 체육수업을 들어야 합니다.
 * 전체 학생의 수 n, 체육복을 도난당한 학생들의 번호가 담긴 배열 lost,
 * 여벌의 체육복을 가져온 학생들의 번호가 담긴 배열 reserve가 매개변수로 주어질 때,
 * 체육수업을 들을 수 있는 학생의 최댓값을 return 하도록 solution 함수를 작성해주세요.
 * 
 * - 제한사항
 * 전체 학생의 수는 2명 이상 30명 이하입니다.
 * 체육복을 도난당한 학생의 수는 1명 이상 n명 이하이고 중복되는 번호는 없습니다.
 * 여벌의 체육복을 가져온 학생의 수는 1명 이상 n명 이하이고 중복되는 번호는 없습니다.
 * 여벌 체육복이 있는 학생만 다른 학생에게 체육복을 빌려줄 수 있습니다.
 * 여벌 체육복을 가져온 학생이 체육복을 도난당했을 수 있습니다.
 * 이때 이 학생은 체육복을 하나만 도난당했다고 가정하며,
 * 남은 체육복이 하나이기에 다른 학생에게는 체육복을 빌려줄 수 없습니다.
 *
 * - 입출력 예
 * n	lost	reserve  	return
 * 5	[2, 4]	[1, 3, 5]	5
 * 5	[2, 4]	[3]     	4
 * 3	[3]	    [1]     	2
 * 
 */

import java.util.*;

public class GreedyLv3_001 {

    public int solution(int n, int[] lost, int[] reserve) {
        // 초기화 배열 생성
        boolean[] tAry = new boolean[n];
        Arrays.fill(tAry, true);

        // 잃어버린 학생들은 false
        for (int i = 0; i < lost.length; ++i) {
            tAry[lost[i] - 1] = false;
        }

        // 여벌 체육복이 있지만, 본인이 잃어버린 상태면
        // 여벌 체육복을 제거(-1), 본인을 true
        for (int i = 0; i < reserve.length; ++i) {
            int idx = reserve[i] - 1; 
            if (tAry[idx] == false) {
                reserve[i] = -1;
                tAry[idx] = true;
            }
        }

        // 여벌 체육복을 최대한 나눠줄 수 있게 분배
        for (int i = 0; i < reserve.length; ++i) {
            if (reserve[i] != -1) { // 여벌 체육복이 있음
                int idx = reserve[i] - 1;
                int prevIdx = Math.min(Math.max(0, idx - 1), tAry.length - 1); // 0 <= idx < tAry.length
                int nextIdx = Math.min(Math.max(0, idx + 1), tAry.length - 1);

                if (tAry[prevIdx] == false) { // 앞 학생에게 빌려줌
                    tAry[prevIdx] = true;
                }
                else { // 뒤 학생에게 빌려줌
                    tAry[nextIdx] = true;
                }
            }
        }

        // 결과 카운팅 후 반환
        int answer = 0;
        for (int i = 0; i < tAry.length; ++i) {
            if (tAry[i] == true) ++answer;
        }
        return answer;
    }

    public static void main(String[] args) {
        // ================================================================
        final GreedyLv3_001 solution = new GreedyLv3_001();
        final int n = 5;
        final int[] lost = { 2, 4 };
        final int[] reserve = { 1, 3, 5 }; // result: 5
        // ================================================================
        final long bgnTime = System.currentTimeMillis();
        final int result = solution.solution(n, lost, reserve);
        final long elapsedTime = System.currentTimeMillis() - bgnTime;
        // ================================================================
        System.out.println("Result : " + result);
        System.out.println("ElapsedTime : " + elapsedTime + "ms");
        // ================================================================
    }
}