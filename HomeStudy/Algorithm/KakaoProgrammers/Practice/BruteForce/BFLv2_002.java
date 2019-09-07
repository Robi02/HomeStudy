
/**
 * - 문제 설명 : 소수 찾기
 * 한자리 숫자가 적힌 종이 조각이 흩어져있습니다.
 * 흩어진 종이 조각을 붙여 소수를 몇 개 만들 수 있는지 알아내려 합니다.
 * 각 종이 조각에 적힌 숫자가 적힌 문자열 numbers가 주어졌을 때,
 * 종이 조각으로 만들 수 있는 소수가 몇 개인지 return 하도록 solution 함수를 완성해주세요.
 * 
 * - 제한사항
 * numbers는 길이 1 이상 7 이하인 문자열입니다. numbers는 0~9까지 숫자만으로 이루어져 있습니다.
 * 013은 0, 1, 3 숫자가 적힌 종이 조각이 흩어져있다는 의미입니다.
 * 
 * - 입출력 예
 * numbers  return
 * 17       3
 * 011      2
 * 
 */

import java.util.HashSet;
import java.util.Set;

public class BFLv2_002 {

    public int pow(int base, int exp) {
        int rtVal = 1;

        for (int i = 0; i < exp; ++i) {
            rtVal *= base;
        }

        return rtVal;
    }

    public boolean isPrime(int number) {
        if (number < 2) {
            return false;
        }
        else if (number == 2) {
            return true;
        }

        int checkLimit = (int)Math.sqrt(number) + 1;

        for (int i = 2; i < checkLimit; ++i) {
            if (number % i == 0) {
                return false;
            }
        }

        return true;
    }

    // permutation (순열)
    public void recursive(int[] numberAry, int depth, int compositedNum, Set<Integer> primeSet) {
        if (depth == numberAry.length) {
            return;
        }

        for (int i = depth; i < numberAry.length; ++i) {
            int lastCompsitedNum = compositedNum;
            
            lastCompsitedNum *= 10;
            lastCompsitedNum += numberAry[i];

            if (isPrime(lastCompsitedNum)) {
                primeSet.add(lastCompsitedNum);
            }

            int temp = numberAry[depth];
            numberAry[depth] = numberAry[i];
            numberAry[i] = temp;

            recursive(numberAry, depth + 1, lastCompsitedNum, primeSet);

            temp = numberAry[depth];
            numberAry[depth] = numberAry[i];
            numberAry[i] = temp;
        }
    }

    public int solution(String numbers) {
        final int numberLen = numbers.length();
        final char[] strNumberAry = numbers.toCharArray();
        int[] numberAry = new int[numberLen];
        
        // string to int[]
        for (int i = 0; i < numberLen; ++i) {
            numberAry[i] = (int)(strNumberAry[i] - '0');
        }

        Set<Integer> primeSet = new HashSet<Integer>();

        recursive(numberAry, 0, 0, primeSet);
        System.out.println(primeSet.toString());

        return primeSet.size();
    }

    public static void main(String[] args) {
        // ================================================================
        final BFLv2_002 solution = new BFLv2_002();
        final String numbers = "011";
        // ================================================================
        final long bgnTime = System.currentTimeMillis();
        final int result = solution.solution(numbers);
        final long elapsedTime = System.currentTimeMillis() - bgnTime;
        // ================================================================
        System.out.println("Result : " + result);
        System.out.println("ElapsedTime : " + elapsedTime + "ms");
        // ================================================================
    }
}