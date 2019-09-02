import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

/**
 * - 문제 : 가장 큰 수 0 또는 양의 정수가 주어졌을 때, 정수를 이어 붙여 만들 수 있는 가장 큰 수를 알아내 주세요. 예를 들어,
 * 주어진 정수가 [6, 10, 2]라면 [6102, 6210, 1062, 1026, 2610, 2106]를 만들 수 있고, 이중 가장 큰
 * 수는 6210입니다. 0 또는 양의 정수가 담긴 배열 numbers가 매개변수로 주어질 때, 순서를 재배치하여 만들 수 있는 가장 큰 수를
 * 문자열로 바꾸어 return 하도록 solution 함수를 작성해주세요.
 * 
 * - 제한 사항 numbers의 길이는 1 이상 100,000 이하입니다. numbers의 원소는 0 이상 1,000 이하입니다. 정답이
 * 너무 클 수 있으니 문자열로 바꾸어 return 합니다.
 * 
 */

public class SortLv2_002 {

    public static String solution(int[] numbers) {
        String[] numStrAry = new String[numbers.length];
        
        // change int ary to string ary
        for (int i = 0; i < numStrAry.length; ++i) {
            numStrAry[i] = String.valueOf(numbers[i]);
        }

        System.out.println("Arrays : " + Arrays.toString(numStrAry));

        // sort by custom rule
        Arrays.sort(numStrAry, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int o1Len = o1.length();
                int o2Len = o2.length();
                int maxLen = Math.max(o1Len, o2Len);

                for (int i = 0; i < maxLen; ++i) {
                    char c1 = o1.charAt(Math.min(i, o1Len - 1));
                    char c2 = o2.charAt(Math.min(i, o2Len - 1));

                    if (i > o1Len && c1 == '0') c1 = o1.charAt(0); // ??
                    if (i > o2Len && c2 == '0') c2 = o2.charAt(0); // 40, 403 케이스... 규칙은?

                    if (c1 == c2) {
                        continue;
                    }

                    return (c1 > c2 ? -1 : 1);
                }

                return 0;
            }
        });

        System.out.println("Sroted : " + Arrays.toString(numStrAry));

        // append string
        StringBuilder rtSb = new StringBuilder();
        for (int i = 0; i < numStrAry.length; ++i) {
            rtSb.append(numStrAry[i]);
        }

        // makes to integer form (00001 -> 1, 0000 -> 0)
        String rtStr = rtSb.toString();
        int rtStrLen = rtStr.length();
        int bgnIdx = 0;
        for (int i = 0; i < rtStrLen - 1; ++i) {
            if (rtStr.charAt(i) == '0') ++bgnIdx;
            else break;
        }

        return rtStr.substring(Math.min(bgnIdx, rtStrLen), rtStrLen);
    }

    public static void main(String[] args) {
        /*final Random rand = new Random();
        final int aryLen = 100;
        int[] array = new int[aryLen];
        for (int i = 0; i < aryLen; ++i) {
            array[i] = rand.nextInt(1001);
        }*/

        int[] array = new int[] { 1, 10, 20, 0 };
            // [40,403 ]
            // [40,405]
            // [40,404]
            // [12,121]
            // [2,22,223]++
            // [70,0,0,0]
            // [0,0,0,0]
            // [0,0,0,1000]
            // [12,1213]};

        final long bgnTime = System.currentTimeMillis();
        final String resultStr = solution(array);
        final long elapsedTime = System.currentTimeMillis() - bgnTime;

        System.out.println("Result : " + resultStr);
        System.out.println("ElapsedTime : " + elapsedTime + "ms");
    }
}