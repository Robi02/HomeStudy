/**
 * -문제 : 전광판 광고
 * 이름이 움직이는 전광판 만들기.
 * 
 * -입력:
 * 1. 첫 번재 줄은 행렬의 크기 N과 회전수 W가 입력됨.
 * (1 <= N <= 100)
 * (-1000000000 <= W <= 1000000000)
 * 2. 두 번재 줄부터는 행렬의 원소값이 입력됨.
 * 3. 각 줄은 개행문자('\n')으로 구분됨.
 * 
 * -출력:
 * 테두리부터 중심 사이의 정사각형들을 회전 +- 에 따라 W만큼 이동한 결과 출력.
 * (한 줄의 끝은, 개행문자로 끝나야 함.)
 * 
 * -조건:
 * 1. 회전수의 부호는 가장 바깥방향의 회전방향을 결정. (양수일때 시계방향, 음수일때 반시계방향)
 * 2. 인접한 두 테두리의 방향은 서로 반대.
 * 3. 회전수의 절대값만큼 원소들이 이동.
 * 
 * -예시:
 * in)
 * 2 333
 * a b
 * c d
 * 
 * out)
 * c a
 * d b
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Practice1 {
    public static void main(String[] args) {
        // input
        Scanner sc = new Scanner(System.in);
        final int matSz = sc.nextInt();
        final int rotateCnt = sc.nextInt();
        sc.nextLine(); // discard '\n'
        
        // cycle list (matrix)
        List<List<String>> cycleLists = new ArrayList<List<String>>();
        int cycleCnt = (matSz + 1) / 2;

        for (int i = 0; i < cycleCnt; ++i) {
            cycleLists.add(new LinkedList<String>());
        }

        // push input into matrix
        String[][] matrix = new String[matSz][];
        for (int i = 0; i < matrix.length; ++i) {
            matrix[i] = new String[matSz];
            String[] names = sc.nextLine().split(" ");

            for (int j = 0; j < matSz; ++j) {
                matrix[i][j] = names[j];
            }
        }
        sc.close();
        
        // push matrix into cycle lists
        int cycleIdx = 0;
        char direction = 'R';
        int reaminCnt = matSz * matSz;
        int x = 0, y = 0;
        
        while (reaminCnt > 0) {
            // matrix to cycle list
            if (isBoundInMatrix(matrix, x, y) && !isNullMat(matrix, x, y)) {
                cycleLists.get(cycleIdx).add(matrix[y][x]);
                matrix[y][x] = null;
                --reaminCnt;
            }
            
            // move pivot
            switch (direction) {
                case 'R': // Right
                    if (!isBoundInMatrix(matrix, x + 1, y) || isNullMat(matrix, x + 1, y)) {
                        direction = 'D';
                        ++y;
                        continue;
                    }
                    ++x;
                break;
                case 'D': // Down
                    if (!isBoundInMatrix(matrix, x, y + 1) || isNullMat(matrix, x, y + 1)) {
                        direction = 'L';
                        --x;
                        continue;
                    }
                    ++y;
                break; 
                case 'L': // Left
                    if (!isBoundInMatrix(matrix, x - 1, y) || isNullMat(matrix, x - 1, y)) {
                        direction = 'U';
                        --y;
                        continue;
                    }
                    --x;
                break;
                case 'U': // Up
                    if (!isBoundInMatrix(matrix, x, y - 1) || isNullMat(matrix, x, y - 1)) {
                        direction = 'R';
                        ++cycleIdx;
                        ++x;
                        continue;
                    }
                    --y;
                break;
            }
        }

        // rotate cycle list
        boolean rotRight = rotateCnt > 0 ? true : false;
        for (List<String> cycleList : cycleLists) {
            int cycleRotCnt = Math.abs(rotateCnt) % cycleList.size();
            for (int i = 0 ; i < cycleRotCnt; ++i) {
                if (rotRight) {
                    cycleList.add(0, cycleList.remove(cycleList.size() - 1)); // tail to head
                }
                else {
                    cycleList.add(cycleList.size() - 1, cycleList.remove(0)); // head to tail
                }
            }

            rotRight = !rotRight;
        }

        // cycle list to matrix
        List<String> elemList = new ArrayList<String>(matSz * matSz);
        for (List<String> cycleList : cycleLists) {
            elemList.addAll(cycleList);
        }

        direction = 'R';
        x = 0; y = 0;
        
        while (elemList.size() > 0) {
            // matrix to cycle list
            if (isBoundInMatrix(matrix, x, y) && isNullMat(matrix, x, y)) {
                matrix[y][x] = elemList.remove(0);
                --reaminCnt;
            }
            
            // move pivot
            switch (direction) {
                case 'R': // Right
                    if (!isBoundInMatrix(matrix, x + 1, y) || !isNullMat(matrix, x + 1, y)) {
                        direction = 'D';
                        ++y;
                        continue;
                    }
                    ++x;
                break;
                case 'D': // Down
                    if (!isBoundInMatrix(matrix, x, y + 1) || !isNullMat(matrix, x, y + 1)) {
                        direction = 'L';
                        --x;
                        continue;
                    }
                    ++y;
                break; 
                case 'L': // Left
                    if (!isBoundInMatrix(matrix, x - 1, y) || !isNullMat(matrix, x - 1, y)) {
                        direction = 'U';
                        --y;
                        continue;
                    }
                    --x;
                break;
                case 'U': // Up
                    if (!isBoundInMatrix(matrix, x, y - 1) || !isNullMat(matrix, x, y - 1)) {
                        direction = 'R';
                        ++cycleIdx;
                        ++x;
                        continue;
                    }
                    --y;
                break;
            }
        }

        // print matrix
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < matrix.length; ++i) {
            for (int j = 0; j < matrix.length; ++j) {
                sb.append(matrix[i][j]).append(' ');
            }
            sb.append('\n');
        }
        System.out.print(sb.toString());
    }

    public static boolean isNullMat(String[][] matrix, int x, int y) {
        if (matrix[y][x] == null) {
            return true;
        }

        return false;
    }

    public static boolean isBoundInMatrix(String[][] matrix, int x, int y) {
        if (x < 0 || x >= matrix.length || y < 0 || y >= matrix.length) {
            return false;
        }

        return true;
    }
}