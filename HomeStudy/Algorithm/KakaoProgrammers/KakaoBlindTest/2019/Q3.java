/**
 * - 문제 설명
 * 고고학자인 튜브는 고대 유적지에서 보물과 유적이 가득할 것으로 추정되는 비밀의 문을 발견하였습니다. 그런데 문을 열려고 살펴보니 특이한 형태의 자물쇠로 잠겨 있었고 문 앞에는 특이한 형태의 열쇠와 함께 자물쇠를 푸는 방법에 대해 다음과 같이 설명해 주는 종이가 발견되었습니다.
 * 잠겨있는 자물쇠는 격자 한 칸의 크기가 1 x 1인 N x N 크기의 정사각 격자 형태이고 특이한 모양의 열쇠는 M x M 크기인 정사각 격자 형태로 되어 있습니다.
 * 자물쇠에는 홈이 파여 있고 열쇠 또한 홈과 돌기 부분이 있습니다. 열쇠는 회전과 이동이 가능하며 열쇠의 돌기 부분을 자물쇠의 홈 부분에 딱 맞게 채우면 자물쇠가 열리게 되는 구조입니다. 자물쇠 영역을 벗어난 부분에 있는 열쇠의 홈과 돌기는 자물쇠를 여는 데 영향을 주지 않지만, 자물쇠 영역 내에서는 열쇠의 돌기 부분과 자물쇠의 홈 부분이 정확히 일치해야 하며 열쇠의 돌기와 자물쇠의 돌기가 만나서는 안됩니다. 또한 자물쇠의 모든 홈을 채워 비어있는 곳이 없어야 자물쇠를 열 수 있습니다.
 * 열쇠를 나타내는 2차원 배열 key와 자물쇠를 나타내는 2차원 배열 lock이 매개변수로 주어질 때, 열쇠로 자물쇠를 열수 있으면 true를, 열 수 없으면 false를 return 하도록 solution 함수를 완성해주세요.
 * 
 * - 제한사항
 * key는 M x M(3 ≤ M ≤ 20, M은 자연수)크기 2차원 배열입니다.
 * lock은 N x N(3 ≤ N ≤ 20, N은 자연수)크기 2차원 배열입니다.
 * M은 항상 N 이하입니다.
 * key와 lock의 원소는 0 또는 1로 이루어져 있습니다.
 * 0은 홈 부분, 1은 돌기 부분을 나타냅니다.
 *
 * - 입출력 예
 * key	lock	result
 * [[0, 0, 0], [1, 0, 0], [0, 1, 1]]	[[1, 1, 1], [1, 1, 0], [1, 0, 1]]	true
 */

import java.util.Arrays;

public class Q3 {
    
    // 1. 키 배열을 시계방향으로 90도씩 회전시키는 함수
    // (메모리를 2배 먹는방법이라... 시간되면 swap방식으로 변경)
    public int[][] rotateKey(int[][] key) {
        int[][] rtAry = new int[key.length][key.length];

        for (int row = 0; row < key.length; ++row) {
            int[] keyRow = key[row];
            int rtCol = key.length - 1 - row;
            
            for (int col = 0; col < keyRow.length; ++col) {
                int rtRow = col;
                rtAry[rtRow][rtCol] = key[row][col];
            }
        }

        return rtAry;
    }

    // 2. 키를 자물쇠 x,y에 놓았을 때 열림을 체크하는 함수
    public boolean isOpen(int x, int y, int[][] key, int[][] lock) {
        // 키 넣음
        for (int kRow = 0; kRow < key.length; ++kRow) {
            int[] kRowAry = key[kRow];

            for (int kCol = 0; kCol < kRowAry.length; ++kCol) {
                if (kRowAry[kCol] == 0) {
                    continue; // 키에서 돌기 없는 위치
                }

                int lRow = kRow + y;
                int lCol = kCol + x;
                
                if (lRow < 0 || lCol < 0 || lRow >= lock.length || lCol >= lock.length) {
                    continue; // 허공에 돌기
                }

                if (lock[lRow][lCol] == 1) {
                    return false; // 돌기끼리 충돌해서 키를 못꼽음
                }
                else {
                    lock[lRow][lCol] = 1;
                }
            }
        }

        // 열림 검사 (열린거 개수를 매개변수로 넘겨주고, 위에 '= 1'
        // 부분에서 조건문 추가(잠긴곳인지) 카운팅하여 연산 단축가능)
        for (int row = 0; row < lock.length; ++row) {
            int[] lockRow = lock[row];

            for (int col = 0; col < lockRow.length; ++col) {
                if (lockRow[col] == 0) {
                    return false;
                }
            }
        }

        return true;
    }

    // 행렬 복사 함수
    public int[][] copyMat(int[][] mat) {
        int[][] rtMat = new int[mat.length][mat.length];

        for (int y = 0; y < mat.length; ++y) {
            for (int x = 0; x < mat.length; ++x) {
                rtMat[y][x] = mat[y][x];
            }
        }

        return rtMat;
    }

    // 행렬 출력 함수
    public void printMatrix(int[][] mat1, int[][] mat2) {
        System.out.println("key\t\tlock");
        for (int i = 0; i < mat1.length; ++i) {
            System.out.println(Arrays.toString(mat1[i]) + "\t" +  Arrays.toString(mat2[i]));
        }
    }

    public boolean solution(int[][] key, int[][] lock) {
        for (int i = 0; i < 4; ++i) {
            for (int y = -key.length + 1; y < lock.length; ++y) {
                for (int x = -key.length + 1; x < lock.length; ++x) {
                    if (isOpen(x, y, key, copyMat(lock))) {
                        return true;
                    }
                }
            }

            if (i < 4) key = rotateKey(key);
        }

        return false;
    }

    public static void main(String[] args) {
        // ================================================================
        final Q3 solution = new Q3();
        final int[][] testCasesKey = new int[][] { // #1 Set input type!
            { 0, 0, 0 }, {1, 0, 0 }, { 0, 1, 1 }
        };
        final int[][] testCasesLock = new int[][] {
            { 1, 1, 1 }, { 1, 1, 0 }, { 1, 0, 1 }
        };
        // #2 Add extra input!
        // ================================================================
        final long bgnTime = System.currentTimeMillis();
        for (int i = 0; i < 1; ++i) {
            final boolean result = solution.solution(testCasesKey, testCasesLock); // #3 Set return type!
            System.out.println("Result : " + result); // #4 Set output type!
        }
        final long elapsedTime = System.currentTimeMillis() - bgnTime;
        // ================================================================
        System.out.println("ElapsedTime : " + elapsedTime + "ms");
        // ================================================================
    }
}