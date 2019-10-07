/**
 * -문제 : 
 * 
 * 
 * -입력:
 * 
 * 
 * -출력:
 * 
 * 
 * -조건:
 * 
 * 
 * -예시:
 * in)
 * 
 * 
 * out)
 * 
 * 
 */

import java.util.*;

public class Q2 {
    public static void main(String[] args) {
        Map<String, Integer> cntMap = new HashMap<String, Integer>();
        List<String> fqList = new LinkedList<String>();
        StringBuilder ansSb = new StringBuilder();
        List<String> maxKeyList = new ArrayList<String>();

        // input
        Scanner sc = new Scanner(System.in);
        int cmdCnt = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < cmdCnt; ++i) {
            // read
            String cmd = sc.nextLine();
            
            // translate
            if (cmd.startsWith("enqueue")) {
                // 인큐 수행
                // 개수 카운팅
                String[] eqCmd = cmd.split(" ");
                String elem = eqCmd[1];
                cntMap.put(elem, cntMap.getOrDefault(elem, 0) + 1);
                // 큐에 삽입
                fqList.add(elem);
            }
            else {
                // 디큐 수행
                // 맵에서 가장 개수가 많은원소의 키들을 리스팅
                maxKeyList.clear();
                int maxVal = Integer.MIN_VALUE;
                for (String key : cntMap.keySet()) {
                    int val = cntMap.get(key);
                    if (maxVal < val) {
                        maxKeyList.clear();
                        maxKeyList.add(key);
                        maxVal = val;
                    }
                    else if (maxVal == val) {
                        maxKeyList.add(key);
                        maxVal = val;
                    }
                }

                // 그 중, 큐에서 가장 먼저 발견되는 원소를 제거
                boolean removed = false;
                for (int j = 0; j < fqList.size(); ++j) {
                    for (String key : maxKeyList) {
                        if (key.equals(fqList.get(j))) {
                            // 카운팅 감소
                            cntMap.put(key, cntMap.getOrDefault(key, 0) - 1);
                            // 큐에서 제거
                            ansSb.append(fqList.remove(j)).append(' ');
                            removed = true;
                            break;
                        }
                    }

                    if (removed) break;
                }
            }
        }

        sc.close();
        ansSb.setLength(ansSb.length() - 1);
        System.out.println(ansSb.toString());
    }
}