import java.util.Map;
import java.util.HashMap;

/**
 * - 문제: 전화번호 목록
 *  전화번호부 번호중 한 번호가 다른 번호의 접두어로 포함되는지 확인하여 포함하면 false, 아니면 true반환.
 *  전화번호가 다음과 같을 경우, 구조대 전화번호는 영석이의 전화번호의 접두사입니다.
 *      구조대 : 119
 *      박준영 : 97 674 223
 *      지영석 : 11 9552 4421
 * 
 * - 제한사항
 *  phone_book의 길이는 1 이상 1,000,000 이하입니다.
 *  각 전화번호의 길이는 1 이상 20 이하입니다.
 */

public class HashLv2_002 {

    public static boolean solution(String[] phone_book) {
        final int forLen = phone_book.length;

        for (int i = 0; i < forLen; ++i) {
            for (int j = 0; j < forLen; ++j) {
                if (i == j) continue;
                else if (phone_book[i].indexOf(phone_book[j]) == 0) {
                    return false;
                }
            }
        }

        return true;
    }

    public static void main(String[] args) {
        System.out.println(solution(new String[] {"119", "97674223", "1195524421"}));
    }
}