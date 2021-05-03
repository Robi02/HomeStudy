import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * - 문제 설명 : 기능 개발 프로그래머스 팀에서는 기능 개선 작업을 수행 중입니다. 각 기능은 진도가 100%일 때 서비스에 반영할 수
 * 있습니다. 또, 각 기능의 개발속도는 모두 다르기 때문에 뒤에 있는 기능이 앞에 있는 기능보다 먼저 개발될 수 있고, 이때 뒤에 있는
 * 기능은 앞에 있는 기능이 배포될 때 함께 배포됩니다. 먼저 배포되어야 하는 순서대로 작업의 진도가 적힌 정수 배열 progresses와 각
 * 작업의 개발 속도가 적힌 정수 배열 speeds가 주어질 때 각 배포마다 몇 개의 기능이 배포되는지를return 하도록 solution
 * 함수를 완성하세요.
 * 
 * - 제한 사항 작업의 개수(progresses, speeds배열의 길이)는 100개 이하입니다. 작업 진도는 100 미만의 자연수입니다.
 * 작업 속도는 100 이하의 자연수입니다. 배포는 하루에 한 번만 할 수 있으며, 하루의 끝에 이루어진다고 가정합니다. 예를 들어 진도율이
 * 95%인 작업의 개발 속도가 하루에 4%라면 배포는 2일 뒤에 이루어집니다.
 * 
 */

public class SAQLv2_005 {

    public int[] solution(int[] progresses, int[] speeds) {
        List<Integer> fetchAtOnceList = new ArrayList<Integer>();
        int unfetchedIdx = 0;
        
        while (unfetchedIdx < progresses.length) {
            for (int i = unfetchedIdx; i < progresses.length; ++i) {
                int progress = progresses[i];

                if (progress < 100) {
                    progresses[i] += speeds[i];
                }
            }
            
            int fetchAtOnceCnt = 0;
            
            for (int j = unfetchedIdx; j < progresses.length; ++j) {
                int progress = progresses[j];
                
                if (progress > 99) {
                    ++fetchAtOnceCnt;
                    ++unfetchedIdx;
                }
                else {
                    break;
                }
            }
            
            if (fetchAtOnceCnt > 0) {
                fetchAtOnceList.add(fetchAtOnceCnt);
            }
        }
        
        int[] rtAry = new int[fetchAtOnceList.size()];
        
        for (int i = 0; i < rtAry.length; ++i) {
            rtAry[i] = fetchAtOnceList.get(i);
        }
        
        return rtAry;
    }

    public static void main(String[] args) {
        // ================================================================
        final SAQLv2_005 solution = new SAQLv2_005();
        final int[] progresses = { 93, 50, 55 };
        final int[] speeds = { 1, 30, 5 };
        // ================================================================
        final long bgnTime = System.currentTimeMillis();
        final int[] results = solution.solution(progresses, speeds);
        final long elapsedTime = System.currentTimeMillis() - bgnTime;
        // ================================================================
        System.out.println("Result : " + Arrays.toString(results));
        System.out.println("ElapsedTime : " + elapsedTime + "ms");
        // ================================================================
    }
}