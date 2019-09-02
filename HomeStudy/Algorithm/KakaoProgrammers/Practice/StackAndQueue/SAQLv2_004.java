import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.stream.Collectors;

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
not solved
public class SAQLv2_004 {

    public static class Truck {
        public int weight;
        public int position;

        public Truck(int weight, int position) {
            this.weight = weight;
            this.position = position;
        }
    }

    public int solution(int bridge_length, int weight, int[] truck_weights) {
        int rtTotalTime = 0;

        // 1. 트럭 무게를 오름차순으로 정렬
        Arrays.sort(truck_weights);

        // 2. 트럭을 가장 가벼운 + 무거운 무게의 트럭을 다리의 한계치까지 보내서 평균적인 균형을 맞춰 보냄
        Deque<Truck> remainingTruckQueue = new ArrayDeque<Truck>(truck_weights.length);
        
        for (int i = 0; i < truck_weights.length; ++i) {
            remainingTruckQueue.push(new Truck(truck_weights[i], -1));
        }
        List<Truck> truckOnBridgeList = new LinkedList<Truck>();
        int totalWeightOnBridge = 0;

        while (remainingTruckQueue.size() > 0 || truckOnBridgeList.size() > 0) {
            // add
            if (remainingTruckQueue.size() > 0 && totalWeightOnBridge < weight) {
                Truck newTruck = remainingTruckQueue.removeFirst();
                int weightAfterAdd = newTruck.weight + totalWeightOnBridge;

                if (weightAfterAdd <= weight) {
                    truckOnBridgeList.add(newTruck);
                    totalWeightOnBridge = weightAfterAdd;
                }
            }

            // update truck
            if (truckOnBridgeList.size() > 0) {
                for (Truck truck : truckOnBridgeList) {
                    ++truck.position;
                }

                Truck firstTruck = truckOnBridgeList.get(0);
            }
        }

        rtTotalTime += (truckOnBridgeList.size() * (bridge_length + 1));
        
        return rtTotalTime;
    }

    public static void main(String[] args) {
        // ================================================================
        final SAQLv2_004 solution = new SAQLv2_004();
        final int bridge_length = 100;
        final int weight = 100;
        final int[] truck_weights = { 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10 }; // reuslt: 110
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