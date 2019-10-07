/**
 * -문제 : 
 * 
 * 
 * -입력:
 * 
 * 
 * -출력:
 * 
 * 
 * -조건:
 * 
 * 
 * -예시:
 * in)
 * 
 * 
 * out)
 * 
 * 
 */

import java.util.*;

public class Q1 {
    public static void main(String[] args) {
        // input
        Scanner sc = new Scanner(System.in);
        int numOfCards = sc.nextInt();
        sc.nextLine();
        String[] cards = sc.nextLine().split(" ");
        sc.close();

        // 맵에 삽입
        Map<String, Integer> cardMap = new HashMap<String, Integer>();

        for (String card : cards) {
            cardMap.put(card, cardMap.getOrDefault(card, 0) + 1);
        }

        // 카드 짝 확인
        char result = 'Y';
        int errCnt = 0;
        int setCnt = Integer.MIN_VALUE;
        for (String key : cardMap.keySet()) {
            Integer mapCnt = cardMap.get(key);
            if (setCnt == Integer.MIN_VALUE) { // 최초
                setCnt = mapCnt;
            }
            else if (setCnt != mapCnt) {
                errCnt += Math.abs(setCnt - mapCnt);
            }
            
            if (errCnt > 1) {
                result = 'N';
                errCnt = 0;
                break;
            }
        }

        // out
        int cardTypeCnt = cardMap.size();
        System.out.println(result);
        System.out.println(numOfCards + errCnt);
        System.out.println(cardTypeCnt);
    }
}