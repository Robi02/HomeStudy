
/**
 * - 문제 설명 : 모의고사
 * 수포자는 수학을 포기한 사람의 준말입니다.
 * 수포자 삼인방은 모의고사에 수학 문제를 전부 찍으려 합니다.
 * 수포자는 1번 문제부터 마지막 문제까지 다음과 같이 찍습니다.
 * 
 * 1번 수포자가 찍는 방식: 1, 2, 3, 4, 5, 1, 2, 3, 4, 5, ...
 * 2번 수포자가 찍는 방식: 2, 1, 2, 3, 2, 4, 2, 5, 2, 1, 2, 3, 2, 4, 2, 5, ...
 * 3번 수포자가 찍는 방식: 3, 3, 1, 1, 2, 2, 4, 4, 5, 5, 3, 3, 1, 1, 2, 2, 4, 4, 5, 5, ...
 * 
 * 1번 문제부터 마지막 문제까지의 정답이 순서대로 들은 배열 answers가 주어졌을 때,
 * 가장 많은 문제를 맞힌 사람이 누구인지 배열에 담아 return 하도록 solution 함수를 작성해주세요.
 * 
 * - 제한 조건
 * 시험은 최대 10,000 문제로 구성되어있습니다.
 * 문제의 정답은 1, 2, 3, 4, 5중 하나입니다.
 * 가장 높은 점수를 받은 사람이 여럿일 경우, return하는 값을 오름차순 정렬해주세요.
 * 
 * - 입출력 예
 * answers      return
 * [1,2,3,4,5]  [1]
 * [1,3,2,4,2]  [1,2,3]
 * 
 */

import java.util.Arrays;

public class BFLv1_001 {

    public int[] solution(int[] answers) {
        final int[][] patterns = {
            { 1, 2, 3, 4, 5 },
            { 2, 1, 2, 3, 2, 4, 2, 5 },
            { 3, 3, 1, 1, 2, 2, 4, 4, 5, 5 }
        };

        int[] scores = new int[patterns.length];
        int highstScore = Integer.MIN_VALUE;

        for (int student = 0; student < patterns.length; ++student) {
            final int[] pattern = patterns[student];
            int score = 0;
            
            for (int question = 0; question < answers.length; ++question) {
                if (answers[question] == pattern[question % pattern.length]) {
                    score += 1;
                }
            }

            if (score > highstScore) {
                highstScore = score;
            }

            scores[student] = score;
        }

        int[] rtAry = new int[patterns.length];
        int rtAryCnt = 0;
        
        for (int i = 0; i < scores.length; ++i) {
            if (scores[i] == highstScore) {
                rtAry[rtAryCnt++] = (i + 1);
            }
        }

        return Arrays.copyOfRange(rtAry, 0, rtAryCnt);
    }

    public static void main(String[] args) {
        // ================================================================
        final BFLv1_001 solution = new BFLv1_001();
        final int[] answers = { 1, 3, 2, 4, 2 };
        // ================================================================
        final long bgnTime = System.currentTimeMillis();
        final int[] result = solution.solution(answers);
        final long elapsedTime = System.currentTimeMillis() - bgnTime;
        // ================================================================
        System.out.println("Result : " + Arrays.toString(result));
        System.out.println("ElapsedTime : " + elapsedTime + "ms");
        // ================================================================
    }
}