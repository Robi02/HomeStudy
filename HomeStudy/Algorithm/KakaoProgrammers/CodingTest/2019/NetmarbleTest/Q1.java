/**
문제 설명
과녁에 화살을 10발 쏘았을 때 얻는 점수의 합을 구하려 합니다. 양궁 과녁은 아래 그림과 같이 5개의 동심원으로 구성되며, 점수 영역은 6개입니다.

yanggung_1.jpg

위 그림에서 a ~ e는 각 점수 영역의 간격을 나타냅니다.

플레이어는 총 10발의 화살을 쏘며, 화살이 꽂힐 때마다 화살이 꽂힌 영역의 점수를 얻습니다. 이때 얻게 되는 총점을 구하려 합니다.

길이 a, b, c, d, e가 순서대로 담긴 배열 target과 화살이 과녁에 꽂힌 좌표를 담은 배열 positions가 매개변수로 주어질 때, 몇 점을 얻었는지 return 하도록 solution 함수를 완성해주세요.

제한사항
target의 길이는 5입니다.
target의 원소는 길이 a, b, c, d, e가 순서대로 담겨 있습니다.
target의 원소는 1 이상 1,000 이하인 자연수입니다.
positions의 길이는 10입니다.
positions의 원소는 [x, y]이며, 이는 화살이 [x, y] 위치에 꽂혔다는 의미입니다.
x와 y는 -10,000 이상 10,000 이하인 정수입니다.
화살이 점수 영역의 경계에 꽂혔을 경우 경계를 포함하는 두 영역의 점수 중 더 높은 점수를 얻게 됩니다.
과녁 정중앙의 좌표는 [0, 0]이며, 가로 방향은 x축, 세로 방향은 y축입니다.
입출력 예
target	positions	return
[2, 2, 2, 2, 2]	[[0, 0], [0, 1], [1, 1], [-3, 5], [7,5], [10, 0], [-15, 22], [-6, -5], [3, 3], [5, -5]]	54
[2, 3, 4, 3, 2]	[[0, 0], [0, 1], [1, 1], [-3, 5], [7,5], [10, 0], [-15, 22], [-6, -5], [3, 3], [5, -5]]	66
입출력 예 설명
입출력 예 #1

화살을 쏘면 [10, 10, 10, 6, 2, 2, 0, 4, 6, 4]점을 받아서 54점을 얻습니다.

입출력 예 #2

화살을 쏘면 [10, 10, 10, 6, 6, 4, 0, 6, 8, 6]점을 받아서 66점을 얻습니다.

 */

import java.util.*;

public class Q1 {
    
    int[] SCORES = { 10, 8, 6, 4, 2, 0 };

    public double distanceFromCenter(int[] position) {
        return Math.sqrt(Math.pow(position[0], 2) + Math.pow(position[1], 2));
    }

    public int getScore(int[] target, int[] position) {
        int pointRadius = 0;
        double dtFromCenter = distanceFromCenter(position);

        for (int i = 0; i < target.length; ++i) {
            pointRadius += target[i];

            if (dtFromCenter <= pointRadius) {
                return SCORES[i];
            }
        }

        return 0;
    }

    public int solution(int[] target, int[][] positions) {
        int rtScrore = 0;

        for (int i = 0; i < positions.length; ++i) {
            rtScrore += getScore(target, positions[i]);
        }

        return rtScrore;
    }

    public static void main(String[] args) {
        // ================================================================
        final Q1 solution = new Q1();
        // #1 Set input type!
        final int[] target = { 2, 3, 4, 3, 2 };
        final int[][] positions = { {0,0},{0,1},{1,1},{-3,5},{7,5},{10,0},{-15,22},{-6,-5},{3,3},{5,-5} };
        // #2 Add extra input!
        // ================================================================
        final long bgnTime = System.currentTimeMillis();
        // #3 Set return type!
        final int result = solution.solution(target, positions);
        // #4 Set output type!
        System.out.println("Result : " + result);
        final long elapsedTime = System.currentTimeMillis() - bgnTime;
        // ================================================================
        System.out.println("ElapsedTime : " + elapsedTime + "ms");
        // ================================================================
    }
}