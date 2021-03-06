/**
문제 설명
졸업 프로젝트를 위해 팀을 구성하려 합니다. 한 팀은 3명으로 구성되며, 모든 참가자는 먼저 같이 팀을 이루고 싶은 사람을 한 명씩 지명합니다. 이때, A가 B를, B가 C를, C가 A를 선택한 경우 A, B, C 세 사람이 한 팀이 됩니다. 예를 들어 각 참가자가 다음과 같이 선택하면 (1, 2, 3) 번 참가자가 한 팀을 이룹니다.

team_1001.png

각 참가자가 지목한 사람의 번호를 순서대로 담은 배열 vote가 solution 함수의 매개변수로 주어질 때, 총 몇 팀이 만들어지는지 return 하도록 solution 함수를 완성해주세요.

제한 사항
vote의 길이는 2 이상 1,000 이하입니다.
vote의 길이가 전체 참가자 수입니다.
vote의 원소는 1 이상 vote의 길이 이하인 자연수입니다.
모든 참가자는 1부터 (vote의 길이)까지 번호가 하나씩 붙어 있습니다.
vote의 첫 번째 원소는 1번 참가자가 선택한 사람의 번호, 두 번째 원소는 2번 참가자가 선택한 사람의 번호, ... 순으로 들어있습니다.
자기 자신을 선택한 사람은 없습니다.
만들어지는 팀이 없는 경우 0을 return 하세요.
입출력 예
vote	result
[2,3,1,3,4]	1
[5,4,2,3,1]	1
[2,3,4,1,6,7,5]	1
입출력 예 설명
입출력 예 #1
앞서 설명한 예와 같습니다

입출력 예 #2

3team_102.png

2번 참여자가 4번 참여자를, 4번 참여자가 3번 참여자를, 3번 참여자가 2번 참여자를 지목했으므로 2, 3, 4번 참여자가 한 팀을 이룹니다. 5번 참가자와 1번 참가자는 서로를 지목했지만, 한 팀은 3명으로 구성해야 하므로 둘은 팀을 이루지 못합니다.

입출력 예 #3

3team_103.png

5번, 6번, 7번 참가자가 팀을 이룹니다. 1번, 2번, 3번, 4번 참가자는 서로를 지목했으나 한 팀은 3명으로 구성해야 하므로 사람이 많아 넷은 팀을 이루지 못합니다.

 */

import java.util.*;

public class Q3 {
    
    public int solution(String s, int i) {
        return 0;
    }

    public static void main(String[] args) {
        // ================================================================
        final Q3 solution = new Q3();
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