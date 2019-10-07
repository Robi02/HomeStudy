import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;

/**
 * - 문제 설명 트럭 여러 대가 강을 가로지르는 일 차선 다리를 정해진 순으로 건너려 합니다. 모든 트럭이 다리를 건너려면 최소 몇 초가
 * 걸리는지 알아내야 합니다. 트럭은 1초에 1만큼 움직이며, 다리 길이는 bridge_length이고 다리는 무게 weight까지 견딥니다.
 * ※ 트럭이 다리에 완전히 오르지 않은 경우, 이 트럭의 무게는 고려하지 않습니다. solution 함수의 매개변수로 다리 길이
 * bridgelength, 다리가 견딜 수 있는 무게 weight, 트럭별 무게 truckweights가 주어집니다. 이때 모든 트럭이
 * 다리를 건너려면 최소 몇 초가 걸리는지 return 하도록 solution 함수를 완성하세요.
 * 
 * - 제한 조건 bridge_length는 1 이상 10,000 이하입니다. weight는 1 이상 10,000 이하입니다.
 * truck_weights의 길이는 1 이상 10,000 이하입니다. 모든 트럭의 무게는 1 이상 weight 이하입니다.
 *
 */

public class SAQLv2_004 {

    public static class Truck {
        public int pos;
        public int weight;
        public void moveTruck() {
            this.pos += 1;
        }
        public Truck(int pos, int weight) {
            this.pos = pos;
            this.weight = weight;
        }
    }

    public int solution(int bridge_length, int weight, int[] truck_weights) {
        // 트럭을 오름차순 무게로 정렬하여 덱에 넣음
        Arrays.sort(truck_weights);
        Deque<Integer> dq = new ArrayDeque<Integer>();

        for (int i = 0; i < truck_weights.length; ++i) {
            dq.addLast(truck_weights[i]);
        }

        // 다리에 적재 시작
        int totalTime = 0;
        List<Truck> bridgeList = new ArrayList<Truck>();
        int totalWeight = 0;
        boolean isHeadTurn = true;

        while (!dq.isEmpty() || !bridgeList.isEmpty()) {
            if (!dq.isEmpty()) {
                Integer newTruckWeigth = isHeadTurn ? dq.peekFirst() : dq.peekLast();
                int afterWeight = totalWeight + newTruckWeigth;

                if (afterWeight <= weight) { // 다리에 아직 적재 가능한 경우
                    // 트럭 충돌 확인
                    boolean loadAble = true;
                    if (!bridgeList.isEmpty()) {
                        if (bridgeList.get(0).pos == 0) {
                            loadAble = false;
                        }
                    }
                    // 새 트럭 적재
                    if (loadAble) {
                        Truck newTruck = new Truck(0, (isHeadTurn ? dq.removeFirst() : dq.removeLast()));
                        bridgeList.add(newTruck);
                        totalWeight = afterWeight;
                    }
                }

                isHeadTurn = !isHeadTurn;
            }

            if (!bridgeList.isEmpty()) {
                // 다리위의 트럭 이동
                for (int i = 0; i < bridgeList.size(); ++i) {
                    Truck updateTruck = bridgeList.get(i);
                    updateTruck.moveTruck();
                }

                // 다리를 벗어나는 트럭
                if (bridgeList.get(0).pos > bridge_length) {
                    totalWeight -= bridgeList.remove(0).weight;
                }
            }

            ++totalTime;
        }

        return totalTime;
    }

    public static void main(String[] args) {
        // ================================================================
        final SAQLv2_004 solution = new SAQLv2_004();
        final int bridge_length = 2;
        final int weight = 10;
        final int[] truck_weights = { 7, 4, 5, 6 }; // reuslt: 8
        //final int[] truck_weights = { 10, 10, 10, 10, 10, 10, 10, 10, 10, 10 }; // reuslt: 110
        // ================================================================
        final long bgnTime = System.currentTimeMillis();
        final int result = solution.solution(bridge_length, weight, truck_weights);
        final long elapsedTime = System.currentTimeMillis() - bgnTime;
        // ================================================================
        System.out.println("Result : " + result);
        System.out.println("ElapsedTime : " + elapsedTime + "ms");
        // ================================================================
    }
}