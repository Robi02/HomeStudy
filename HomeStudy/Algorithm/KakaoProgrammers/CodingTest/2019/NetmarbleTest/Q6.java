/**
문제 설명
바둑판에 가로, 세로, 대각선으로 n개의 돌이 연속해서 나열되어 있을 때를 ’n목’이라고 합니다. 단, 연속으로 나열된 돌이 n개보다 많다면 이는 n목으로 취급하지 않습니다.
아래 그림은 7 X 9 크기의 바둑판에 n = 4일때의 n목을 타원으로 표시한 것입니다. 돌이 있는 칸에는 1, 없는 칸에는 0이 적혀있습니다.
pic.png

가로 4목은 5개, 세로 4목은 1개, 대각선 4목은 4개이므로 4목은 10개입니다. 녹색 사각형으로 표시된 부분은 돌이 5개 연속으로 나열되어 있으므로, 4목으로 취급하지 않습니다.

바둑판의 세로크기 h, 가로크기 w, n목을 정의하는 숫자 n, 바둑판을 나타내는 배열 board가 주어졌을 때, n목의 개수를 return 하도록 solution 함수를 완성해 주세요.

제한사항
h, w, n은 4이상 1000이하인 자연수입니다.
board의 길이는 h입니다.
board의 원소는 길이가 w인 문자열입니다.
문자열은 0과 1로만 구성됩니다.
1은 돌이 놓인 칸을, 0은 돌이 없는 칸을 나타냅니다.
입출력 예
h	w	n	board	result
7	9	4	["111100000","000010011","111100011","111110011","111100011","111100010","111100000"]	10
5	5	5	["11111","11111","11111","11111","11111"]	12
입출력 예 설명
입출력 예 #1
앞서 설명한 예와 같습니다.

입출력 예 #2
가로로 5개, 세로로 5개, 대각선으로 2개씩 5목을 만들 수 있으므로 정답은 12입니다.

 */

import java.util.*;

public class Q6 {
    
    public int recursive(int[][] board, int x, int dx, int y, int dy, int combo, int n) {
        if (combo > n) {
            return n; // 무조건 만족하는 경우가 안생김
        }

        if (x < 0 || x >= board[0].length || y < 0 || y >= board.length) {
            return -1;
        }

        if (board[y][x] == 0) {
            return -1;
        }

        return recursive(board, x + dx, dx, y + dy, dy, combo + 1, n) + 1;
    }

    public int checkPt(int[][] board, int x, int y, int n) {
        int ansAtPtCnt = 0;
        int a = -1;
        int b = -1;

        if ((a = recursive(board, x, -1, y, 0,  0, n)) + (b = recursive(board, x, +1, y, 0,  0, n)) == n - 1) {
            System.out.println("x:" + x + ", y:" + y + "좌:" + a + ", 우:" + b);
            ansAtPtCnt += 1; // 좌우
        }

        if ((a = recursive(board, x, 0, y, -1,  0, n)) + (b = recursive(board, x, 0, y, +1,  0, n)) == n - 1) {
            System.out.println("x:" + x + ", y:" + y + "상:" + a + ", 하:" + b);
            ansAtPtCnt += 1; // 상하
        }

        if ((a = recursive(board, x, -1, y, -1,  0, n)) + (b = recursive(board, x, +1, y, +1,  0, n)) == n - 1) {
            System.out.println("x:" + x + ", y:" + y + "좌상:" + a + ", 우하:" + b);
            ansAtPtCnt += 1; // 좌상우하
        }

        if ((a = recursive(board, x, +1, y, -1,  0, n)) + (b = recursive(board, x, -1, y, -1,  0, n)) == n - 1) {
            System.out.println("x:" + x + ", y:" + y + "우상:" + a + ", 좌하:" + b);
            ansAtPtCnt += 1; // 우상좌하
        }

        return ansAtPtCnt;
    }

    public int solution(String s, int i) {
        /*int matrix[][] = {
            {0, 0, 1, 1, 1},
            {0, 0, 1, 1, 1},
            {0, 1, 1, 1, 1},
            {1, 1, 1, 0, 1},
            {1, 0, 1, 0, 1},
        };*/
        int matrix[][] = {
            {1,1,1,1,0,0,0,0,0},
            {0,0,0,0,1,0,0,1,1},
            {1,1,1,1,0,0,0,1,1},
            {1,1,1,1,1,0,0,1,1},
            {1,1,1,1,0,0,0,1,1},
            {1,1,1,1,0,0,0,1,0},
            {1,1,1,1,0,0,0,0,0}
        };
        int n = 4;

        int rtCnt = 0;
        for (int y = 0; y < matrix.length; ++y) {
            for (int x = 0; x < matrix[0].length; ++x) {
                if (matrix[y][x] == 1) {
                    rtCnt += checkPt(matrix, x, y, n);
                }
            }
        }
        return rtCnt / n;
    }

    public static void main(String[] args) {
        // ================================================================
        final Q6 solution = new Q6();
        // #1 Set input type!
        final String inputA = "abc";
        final int inputB = 123;
        // #2 Add extra input!
        // ================================================================
        final long bgnTime = System.currentTimeMillis();
        // #3 Set return type!
        final int result = solution.solution(inputA, inputB);
        // #4 Set output type!
        System.out.println("Result : " + result);
        final long elapsedTime = System.currentTimeMillis() - bgnTime;
        // ================================================================
        System.out.println("ElapsedTime : " + elapsedTime + "ms");
        // ================================================================
    }
}