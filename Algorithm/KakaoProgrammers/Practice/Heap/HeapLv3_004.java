
/**
 * - 문제 설명 : 이중우선순위큐
 * 이중 우선순위 큐는 다음 연산을 할 수 있는 자료구조를 말합니다.
 * 
 * 명령어	수신 탑(높이)
 * I 숫자	큐에 주어진 숫자를 삽입합니다.
 * D 1	큐에서 최댓값을 삭제합니다.
 * D -1	큐에서 최솟값을 삭제합니다.
 * 이중 우선순위 큐가 할 연산 operations가 매개변수로 주어질 때, 모든 연산을 처리한 후 큐가 비어있으면 [0,0] 비어있지 않으면 [최댓값, 최솟값]을 return 하도록 solution 함수를 구현해주세요.
 * 
 * - 제한사항
 * operations는 길이가 1 이상 1,000,000 이하인 문자열 배열입니다.
 * operations의 원소는 큐가 수행할 연산을 나타냅니다.
 * 원소는 “명령어 데이터” 형식으로 주어집니다.- 최댓값/최솟값을 삭제하는 연산에서 최댓값/최솟값이 둘 이상인 경우, 하나만 삭제합니다.
 * 빈 큐에 데이터를 삭제하라는 연산이 주어질 경우, 해당 연산은 무시합니다.
 * 
 * - 입출력 예
 * operations	return
 * [I 16,D 1]	[0,0]
 * [I 7,I 5,I -5,D -1]	[7,5]
*/

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

public class HeapLv3_004 {

    public int[] translate(String operation) {
        String[] opSplit = operation.split(" ");
        String opcode = opSplit[0];
        String oprand = opSplit[1];
        int[] rt = new int[2];        

        if (opcode.equals("I")) {
            rt[0] = 0;
            rt[1] = Integer.valueOf(oprand);
        }
        else {
            if (oprand.equals("1")) {
                rt[0] = 1;
            }
            else {
                rt[0] = -1;
            }
        }

        return rt;
    }
    
    public int[] solution(String[] operations) {
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
        PriorityQueue<Integer> pqInv = new PriorityQueue<Integer>(Collections.reverseOrder());

        for (int i = 0; i < operations.length; ++i) {
            int[] code = translate(operations[i]);

            if (code[0] == 0) { // Insert
                pq.add(code[1]);
                pqInv.add(code[1]);
            }
            else if (code[0] == 1) { // Delete - max
                if (!pq.isEmpty()) {
                    pq.remove(pqInv.remove());
                }
            }
            else { // Delete - min
                if (!pqInv.isEmpty()) {
                    pqInv.remove(pq.remove());
                }
            }
        }

        int[] rt = new int[2];
        rt[0] = pqInv.isEmpty() ? 0 : pqInv.peek();
        rt[1] = pq.isEmpty() ? 0 : pq.peek();
        return rt;
    }

    public static void main(String[] args) {
        // ================================================================
        final HeapLv3_004 solution = new HeapLv3_004();
        final String[] operations = {"I 16", "I -5643", "D -1", "D 1", "D 1", "I 123", "D -1"}; // [0, 0]
        // ================================================================
        final long bgnTime = System.currentTimeMillis();
        final int[] result = solution.solution(operations);
        final long elapsedTime = System.currentTimeMillis() - bgnTime;
        // ================================================================
        System.out.println("Result : " + Arrays.toString(result));
        System.out.println("ElapsedTime : " + elapsedTime + "ms");
        // ================================================================
    }
}