/**
 * - 문제 설명 : 네트워크
 * 네트워크란 컴퓨터 상호 간에 정보를 교환할 수 있도록 연결된 형태를 의미합니다.
 * 예를 들어, 컴퓨터 A와 컴퓨터 B가 직접적으로 연결되어있고,
 * 컴퓨터 B와 컴퓨터 C가 직접적으로 연결되어 있을 때 컴퓨터 A와 컴퓨터 C도 간접적으로
 * 연결되어 정보를 교환할 수 있습니다. 따라서 컴퓨터 A, B, C는 모두 같은 네트워크 상에 있다고 할 수 있습니다.
 * 
 * 컴퓨터의 개수 n, 연결에 대한 정보가 담긴 2차원 배열 computers가 매개변수로 주어질 때,
 * 네트워크의 개수를 return 하도록 solution 함수를 작성하시오.
 * 
 * - 제한사항
 * 컴퓨터의 개수 n은 1 이상 200 이하인 자연수입니다.
 * 각 컴퓨터는 0부터 n-1인 정수로 표현합니다.
 * i번 컴퓨터와 j번 컴퓨터가 연결되어 있으면 computers[i][j]를 1로 표현합니다.
 * computer[i][i]는 항상 1입니다.
 * 
*/

import java.util.HashSet;
import java.util.Set;

public class DFSnBFSLv3_002 { 미해결

    public int recursive(int[][] computers, int comIdx, int netIdx, int[] netGrpAry, int netGrpCnt) {
        if (comIdx == netIdx) {
            // 자기자신
            return 0;
        }

        boolean isFirstConn = false;

        if (netGrpAry[comIdx] == 0) {
            // 처음으로 연결된 컴퓨터
            netGrpAry[comIdx] = netGrpCnt;
            isFirstConn = true;
        }
        else if (netGrpAry[comIdx] == netGrpCnt) {
            // 이미 연결된 컴퓨터
            return netGrpCnt;
        }

        int[] connCom = computers[netIdx];

        for (int connComNetIdx = 0; connComNetIdx < connCom.length; ++connComNetIdx) {
            recursive(computers, netIdx, connComNetIdx, netGrpAry, netGrpCnt);
        }

        return (isFirstConn ? netGrpCnt + 1 : netGrpCnt);
    }

    public int solution(int n, int[][] computers) {
        int[] netGrpAry = new int[computers.length];
        int netGrpCnt = 1;

        for (int comIdx = 0; comIdx < computers.length; ++comIdx) {
            int[] computer = computers[comIdx];

            for (int netIdx = 0; netIdx < computer.length; ++netIdx) {
                netGrpCnt += recursive(computers, comIdx, netIdx, netGrpAry, netGrpCnt);
            }
        }

        return netGrpCnt;
    }

    public static void main(String[] args) {
        // ================================================================
        final DFSnBFSLv3_002 solution = new DFSnBFSLv3_002();
        final int n = 3;
        final int[][] computers = { {1, 1, 0}, {1, 1, 0}, {0, 0, 1} };
        //final int[][] computers = { {1, 1, 0}, {1, 1, 1}, {0, 1, 1} };
        // ================================================================
        final long bgnTime = System.currentTimeMillis();
        final int result = solution.solution(n, computers);
        final long elapsedTime = System.currentTimeMillis() - bgnTime;
        // ================================================================
        System.out.println("Result : " + result);
        System.out.println("ElapsedTime : " + elapsedTime + "ms");
        // ================================================================
    }
}