
/**
 * - 문제 설명
 * 주어진 항공권을 모두 이용하여 여행경로를 짜려고 합니다. 항상 ICN 공항에서 출발합니다.
 * 
 * 항공권 정보가 담긴 2차원 배열 tickets가 매개변수로 주어질 때, 방문하는 공항 경로를 배열에 담아 return 하도록 solution 함수를 작성해주세요.
 * 
 * - 제한사항
 * 모든 공항은 알파벳 대문자 3글자로 이루어집니다.
 * 주어진 공항 수는 3개 이상 10,000개 이하입니다.
 * tickets의 각 행 [a, b]는 a 공항에서 b 공항으로 가는 항공권이 있다는 의미입니다.
 * 주어진 항공권은 모두 사용해야 합니다.
 * 만일 가능한 경로가 2개 이상일 경우 알파벳 순서가 앞서는 경로를 return 합니다.
 * 모든 도시를 방문할 수 없는 경우는 주어지지 않습니다.
 * 
 * - 입출력 예
 * tickets	return
 * [[ICN, JFK], [HND, IAD], [JFK, HND]]	[ICN, JFK, HND, IAD]
 * [[ICN, SFO], [ICN, ATL], [SFO, ATL], [ATL, ICN], [ATL,SFO]]	[ICN, ATL, ICN, SFO, ATL, SFO]
*/

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class DFSnBFSLv3_004 {

    public String[] solution(String[][] tickets) {
        Arrays.sort(tickets, new Comparator<String[]>() {
            @Override
            public int compare(String[] o1, String[] o2) {
                boolean isICN1 = o1[0].equals("ICN");
                boolean isICN2 = o2[0].equals("ICN");

                if (isICN1) {
                    if (isICN2) {
                        return o1[1].compareTo(o2[1]);
                    } else {
                        return 1;
                    }
                } else if (isICN2) {
                    return 1;
                } else {
                    if (o1[0].equals(o2[0])) {
                        return o1[1].compareTo(o2[1]);
                    }

                    return o1[0].compareTo(o2[0]);
                }
            }
        });

        List<String> answerList = new ArrayList<String>();

        for (int i = 0; i < tickets.length; ++i) {
            if (answerList.isEmpty()) {
                String[] firstTicket = tickets[i];
                if (firstTicket[0].equals("ICN")) {
                    recursive(1, firstTicket, tickets, answerList);
                }
                else {
                    break;
                }
            }
        }

        return answerList.toArray(new String[0]);
    }

    public void recursive(int depth, String[] lastTicket, String[][] tickets, List<String> answerList) {
        if (depth == tickets.length - 1) {
            boolean pathComplete = true;
            for (int i = 0; i < tickets.length; ++i) {
                if (tickets[i] != null) {
                    pathComplete = false;
                    break;
                }
            }
            if (pathComplete) {
                answerList.add(lastTicket[1]);
            }
            return;
        }

        for (int i = 0; i < tickets.length; ++i) {
            String[] ticket = tickets[i];
            if (ticket == null) continue;
            else if (lastTicket[1].equals(ticket[0])) {
                tickets[i] = null;
                recursive(depth + 1, ticket, tickets, answerList);
                tickets[i] = ticket;
            }
        }
    }
    
    public static void main(String[] args) {
        // ================================================================
        final DFSnBFSLv3_004 solution = new DFSnBFSLv3_004();
        final String[][] tickets = { {"ICN","JFK"}, {"HND","IAD"}, {"JFK","HND"} };
        // final String[][] tickets = { {"ICN","SFO"}, {"ICN","ATL"}, {"SFO","ATL"}, {"ATL","ICN"}, {"ATL","SFO"} };
        // ================================================================
        final long bgnTime = System.currentTimeMillis();
        final String[] result = solution.solution(tickets);
        final long elapsedTime = System.currentTimeMillis() - bgnTime;
        // ================================================================
        System.out.println("Result : " + Arrays.toString(result));
        System.out.println("ElapsedTime : " + elapsedTime + "ms");
        // ================================================================
    }
}