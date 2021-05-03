
/**
 * - 문제 설명 : 타겟 넘버 n개의 음이 아닌 정수가 있습니다. 이 수를 적절히 더하거나 빼서 타겟 넘버를 만들려고 합니다. 예를 들어
 * [1, 1, 1, 1, 1]로 숫자 3을 만들려면 다음 다섯 방법을 쓸 수 있습니다.
 * 
 * -1+1+1+1+1 = 3 +1-1+1+1+1 = 3 +1+1-1+1+1 = 3 +1+1+1-1+1 = 3 +1+1+1+1-1 = 3
 * 
 * 사용할 수 있는 숫자가 담긴 배열 numbers, 타겟 넘버 target이 매개변수로 주어질 때 숫자를 적절히 더하고 빼서 타겟 넘버를
 * 만드는 방법의 수를 return 하도록 solution 함수를 작성해주세요.
 * 
 * - 제한사항 주어지는 숫자의 개수는 2개 이상 20개 이하입니다. 각 숫자는 1 이상 50 이하인 자연수입니다. 타겟 넘버는 1 이상
 * 1000 이하인 자연수입니다.
 * 
 */

public class DFSnBFSLv2_001 {

    public int recursive(int[] numbers, int depth, int sum, int target, boolean isPlus) {
        int curNum = numbers[depth];

        if (isPlus) {
            sum += curNum;
        }
        else {
            sum -= curNum;
        }

        if (depth + 1 != numbers.length) {
            return recursive(numbers, depth + 1, sum, target, true) + recursive(numbers, depth + 1, sum, target, false);
        }
        else {
            if (sum == target) {
                return 1;
            }
            
            return 0;
        }
    }

    public int solution(int[] numbers, int target) {
        return recursive(numbers, 0, 0, target, true) + recursive(numbers, 0, 0, target, false);
    }

    public static void main(String[] args) {
        // ================================================================
        final DFSnBFSLv2_001 solution = new DFSnBFSLv2_001();
        final int[] numbers = { 1, 1, 1, 1, 1 };
        final int target = 3;
        // ================================================================
        final long bgnTime = System.currentTimeMillis();
        final int result = solution.solution(numbers, target);
        final long elapsedTime = System.currentTimeMillis() - bgnTime;
        // ================================================================
        System.out.println("Result : " + result);
        System.out.println("ElapsedTime : " + elapsedTime + "ms");
        // ================================================================
    }
}