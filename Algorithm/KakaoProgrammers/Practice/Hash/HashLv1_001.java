import java.util.Map;
import java.util.HashMap;

/**
 * - 문제: 완주하지못한 선수
 *  참여자중 완주하지 못한 선수가 1명. 해당 선수의 이름을 찾아라.
 * 
 * - 제한사항
 *  마라톤 경기에 참여한 선수의 수는 1명 이상 100,000명 이하입니다.
 *  completion의 길이는 participant의 길이보다 1 작습니다.
 *  참가자의 이름은 1개 이상 20개 이하의 알파벳 소문자로 이루어져 있습니다.
 *  참가자 중에는 동명이인이 있을 수 있습니다.
 */

public class HashLv1_001 {

    public static String solution(String[] participant, String[] completion) {
        Map<String, Integer> participantMap = new java.util.HashMap<String, Integer>();

        // 참가자 명단 생성
        for (String name : participant) {
            Integer counter = participantMap.get(name);
            participantMap.put(name, counter != null ? counter + 1 : 1);
        }

        // 완주자 계산
        for (String name : completion) {
            Integer counter = participantMap.get(name);

            if (counter == null) {
                System.out.println("오류 -> 미등록 참가자 발견! (이름: " + name + ")");
                continue;
            }
            else if (counter == 1) {
                participantMap.remove(name);
            }
            else {
                participantMap.put(name, counter - 1);
            }
        }

        return participantMap.keySet().iterator().next();
    }

    public static void main(String[] args) {
        System.out.println(solution(new String[] {"leo", "kiki", "eden"}, new String[] {"eden", "kiki"}));
    }
}