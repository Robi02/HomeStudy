/**
문제 설명
지뢰 찾기 게임 판이 주어졌을 때 지뢰가 있는 칸, 지뢰가 없는 칸, 지뢰가 있는지 없는지 알 수 없는 칸을 알아보려 합니다. 판의 크기는 2 x N이며, 지뢰 찾기 게임의 규칙은 다음과 같습니다.

클릭한 칸에 지뢰가 있으면 폭탄이 터집니다.
클릭한 칸에 지뢰가 없다면 해당 칸에는 상, 하, 좌, 우 및 대각선 방향에 있는 지뢰 수가 나타납니다.
예를 들어 2 x 6 크기의 게임 보드에서 (1, 2), (1, 6), (2, 1), (2, 2), (2, 3), (2, 5) 총 6칸을 클릭한 상태가 다음과 같은 경우

mine_501.png

지뢰 위치를 다음과 같이 예상할 수 있습니다.

예상 1:

mine_502.png

예상 2:

mine_503.png

✷ : 지뢰

위 그림에 따라 지뢰가 반드시 있는 칸, 지뢰가 반드시 없는 칸, 지뢰가 있는지 없는지 알 수 없는 칸을 표시하면 다음과 같습니다.

mine_504.png

🚩: 반드시 지뢰가 있음
⛑ : 반드시 지뢰가 없음
❓ : 알 수 없음

게임판에서 일부 칸을 클릭한 상태가 담긴 배열 board가 매개변수로 주어질 때, 각 칸에 지뢰가 반드시 있는지, 없는지, 또는 알 수 없는지를 문자열 배열 형태로 return 하도록 solution 함수를 완성해주세요.

제한사항
board의 길이는 2입니다.
board의 원소는 게임판의 행을 나타내는 문자열입니다.
문자열의 길이는 3 이상 20 이하입니다.
문자열은 숫자와 '.'으로만 이루어져 있습니다.
클릭한 칸에는 인접한 칸에 있는 지뢰 개수를 나타내는 숫자가 적혀있습니다.
클릭하지 않은 칸에는 '.'가 적혀있습니다.
클릭하지 않은 칸 중 숫자가 적힌 칸들과 인접한 칸은 15개 이하입니다.
가능한 지뢰 배치가 반드시 하나 이상 존재하는 경우만 입력으로 주어집니다.
답을 return 할 때에는 '.'이었던 곳에 'M', 'S', '?'를 적절히 채워서 return 해주세요.
반드시 지뢰가 있는 칸은 ‘M’으로 표시합니다.
반드시 지뢰가 없는 칸은 ‘S’로 표시합니다.
지뢰가 있는지 없는지 알 수 없는 칸은 ‘?’로 표시합니다.
일반적 지뢰 찾기 게임과는 달리 근처에 지뢰가 없는 칸을 클릭해도 인접한 칸이 연쇄적으로 열리지 않는다고 가정합니다.
입출력 예
board	result
[".1...2", "111.3."]	["M1S?M2", "111?3M"]
[".3....", "1..20."]	["?3MSSS", "1?M20S"]
입출력 예 설명
입출력 예 #1

문제 예시와 같습니다.

입출력 예 #2

지뢰 위치를 다음과 같이 두 가지로 예상할 수 있습니다.

mine_201.png

mine_202.png
 */

import java.util.*;

public class Q7 {
    
    public int solution(String s, int i) {
        return 0;
    }

    public static void main(String[] args) {
        // ================================================================
        final Q7 solution = new Q7();
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