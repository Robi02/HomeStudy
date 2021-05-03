
/**
 * 메시지(message) 처리 시간 최대 10개의 메시지를 처리하는 메시지 큐(message queue)가 있다. 메시지 큐에 메시지가
 * 쌓이면 이를 순차적으로 최대 10개의 컨슈머(consumer)가 처리한다. 메시지마다 처리에 걸리는 시간은 다를 수 있고 하나의 메시지
 * 처리에 걸리는 시간은 최대 100초이다. 모든 메시지가 0초에 도착하고 입력받는 순서대로 처리한다고 가정하였을 때, 전체 메시지를
 * 처리하는데 걸리는 시간을 계산하시오. 단, 메시지의 개수, 컨슈머의 개수 모두 1개 이상이고 메시지를 메시지 큐에 넣거나 빼는 작업에는
 * 아무런 오버헤드가 없다.
 * 
 * 예시 5개의 메시지, 2개의 컨슈머가 있고 각각의 메시지 처리시간이 각각 4초, 3초, 5초, 2초, 8초 걸린다고 했을 때, 아래 표와
 * 같이 총 14초가 소비된다.
 * 
 * 1 2 3 4 5 6 7 8 9 10 11 12 13 14 Consumer1 m1 m1 m1 m1 m4 m4 m5 m5 m5 m5 m5
 * m5 m5 m5 Consumer2 m2 m2 m2 m3 m3 m3 m3 m3 입력 형식 첫째 줄에 입력받을 메시지의 수와 컨슈머 개수가
 * 정수로 입력된다 첫째 줄의 메시지의 수만큼 처리 소요 시간이 입력된다 출력 형식 총 처리 시간을 출력한다
 * 
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Q1 {
    
    public int solution() {
        Scanner sc = new Scanner(System.in);
        int msgCnt = sc.nextInt();
        int conCnt = sc.nextInt();
        List<Integer> msgList = new ArrayList<Integer>();

        for (int i = 0; i < msgCnt; ++i) {
            msgList.add(sc.nextInt());
        }

        sc.close();

        //
        int[] consumer = new int[conCnt];
        int curTime = 0;

        while (!msgList.isEmpty()) {
            for (int con = 0; con < consumer.length; ++con) {
                if ((consumer[con])-- <= 0) {
                    if (!msgList.isEmpty()) {
                        consumer[con] = msgList.remove(0);
                    }
                }
            }

            ++curTime;
        }
        
        return curTime + msgCnt;
    }

    public static void main(String[] args) {
        // ================================================================
        final Q1 solution = new Q1();
        final String[] testCases = null;
        // ================================================================
        final long bgnTime = System.currentTimeMillis();
        final int result = solution.solution(); // #3 Set return type!
        System.out.println("Result : " + result); // #4 Set output type!
        final long elapsedTime = System.currentTimeMillis() - bgnTime;
        // ================================================================
        System.out.println("ElapsedTime : " + elapsedTime + "ms");
        // ================================================================
    }
}