/**
 * - 문제 설명 : 숫자 야구
 * 숫자 야구 게임이란 2명이 서로가 생각한 숫자를 맞추는 게임입니다.
 * 각자 서로 다른 1~9까지 3자리 임의의 숫자를 정한 뒤 서로에게 3자리의 숫자를 불러서 결과를 확인합니다.
 * 그리고 그 결과를 토대로 상대가 정한 숫자를 예상한 뒤 맞힙니다.
 * 
 * > 숫자는 맞지만, 위치가 틀렸을 때는 볼
 * > 숫자와 위치가 모두 맞을 때는 스트라이크
 * > 숫자와 위치가 모두 틀렸을 때는 아웃
 *
 * 예를 들어, 아래의 경우가 있으면
 * 
 * A : 123
 * B : 1스트라이크 1볼.
 * A : 356
 * B : 1스트라이크 0볼.
 * A : 327
 * B : 2스트라이크 0볼.
 * A : 489
 * B : 0스트라이크 1볼.
 *
 * 이때 가능한 답은 324와 328 두 가지입니다. 
 * 질문한 세 자리의 수, 스트라이크의 수, 볼의 수를 담은 2차원 배열 baseball이 매개변수로 주어질 때,
 * 가능한 답의 개수를 return 하도록 solution 함수를 작성해주세요.
 * 
 * - 제한사항
 * 질문의 수는 1 이상 100 이하의 자연수입니다.
 * baseball의 각 행은 [세 자리의 수, 스트라이크의 수, 볼의 수] 를 담고 있습니다.
 *
 * - 입출력 예
 * baseball                                                 return
 * [[123, 1, 1], [356, 1, 0], [327, 2, 0], [489, 0, 1]]     2
 * 
 */

import java.util.Arrays;

public class BFLv2_003 {

    public boolean checkResult(int testNum, int testCase) {
        나중에 풀어보자...
    }

    public int solution(int[][] baseball) {
        for (int testNum = 123; testNum < 988; ++testNum) {
            int first  = testNum / 100;
            int second = testNum % 100 / 10;
            int third  = testNum % 10;

            if (first == second || first == third || second == third) continue; // out of rule

            for (int j = 0; j < baseball[j].length; ++j) {
                int[] testCase = baseball[j];
                int ball = testCase[1];
                int strike = testCase[2];

                testNum
            }

            int 
        }
    }

    public static void main(String[] args) {
        // ================================================================
        final BFLv2_003 solution = new BFLv2_003();
        final int[][] baseball = { {123,1,1}, {356,1,0}, {327,2,0}, {489,0,1} }; // result: 2
        // ================================================================
        final long bgnTime = System.currentTimeMillis();
        final int result = solution.solution(baseball);
        final long elapsedTime = System.currentTimeMillis() - bgnTime;
        // ================================================================
        System.out.println("Result : " + result);
        System.out.println("ElapsedTime : " + elapsedTime + "ms");
        // ================================================================
    }
}