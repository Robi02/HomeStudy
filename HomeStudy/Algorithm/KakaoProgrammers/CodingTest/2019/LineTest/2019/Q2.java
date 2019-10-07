/**
    문제 설명
    k번째 순열 찾기
    예를 들어 숫자 1, 2, 3으로 만들 수 있는 순열 중 오름차순으로 정렬 했을 때 3번째 순열은 213이다.

    조합된 순열	순서(오름차순)
    123	1
    132	2
    213	3
    231	4
    312	5
    321	6
    제한 사항
    순열을 조합할 때 주어진 숫자를 모두 한번씩 사용해야 한다
    입력 형식
    첫 번째 행에 공백(space)을 구분자로 숫자가 주어진다
    각 숫자는 한 자리 숫자로 주어진다 (0과 같거나 크고, 10보다 작은 숫자)
    같은 숫자가 중복되어 나타나지 않는다
    두 번째 행에 찾으려는 수열의 순서(k)가 주어진다
    k는 조합된 순열의 개수보다 크거나 작지 않다
    출력 형식
    조합된 순열을 오름차순으로 정렬 했을 때 k번째 순열
    맨 앞자리가 0인 경우는 0을 그대로 유지한다
    입출력 예제
    입력

    1 0 2
    5
    출력

    201
 * 
 * 
 * 
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Q2 {
    
    public static void permutaion(Integer[] elems, int idx, PriorityQueue<String> permPq) {
        if (idx == elems.length) {
            String perm = "";

            for (int i = 0; i < elems.length; ++i) {
                perm += elems[i];
            }

            permPq.add(perm);
            return;
        }

        for (int i = idx; i < elems.length; ++i) {
            int temp = elems[i];
            elems[i] = elems[idx];
            elems[idx] = temp;

            permutaion(elems, idx + 1, permPq);

            temp = elems[i];
            elems[i] = elems[idx];
            elems[idx] = temp;
        }
    }

    public static void main(String[] args) {
        final long bgnTime = System.currentTimeMillis();
        // ================================================================
        List<Integer> numList = new ArrayList<Integer>();
        Scanner sc = new Scanner(System.in);

        for (String num : sc.nextLine().split(" ")) {
            numList.add(Integer.valueOf(num));
        }

        Collections.sort(numList);

        int targetP = sc.nextInt();
        sc.close();

        // 일단은 시간이 촉박하므로, 전체 순열을 구하는 방식으로 접근...
        PriorityQueue<String> permPq = new PriorityQueue<String>();
        permutaion(numList.toArray(new Integer[0]), 0, permPq);

        while (!permPq.isEmpty()) {
            String perm = permPq.remove();
            if (--targetP == 0) {
                System.out.println(perm);
                break;
            }
        }
        // ================================================================
        final long elapsedTime = System.currentTimeMillis() - bgnTime;
        System.out.println("ElapsedTime : " + elapsedTime + "ms");
    }
}