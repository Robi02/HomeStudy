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

public class Q4 {

    public static class User {
        public int fuel;
        public int gold;
        public User (int fuel, int gold) {
            this.fuel = fuel;
            this.gold = gold;
        }
    }

    public static class Town {
        public char name;
        public Edge edge;
        public Town(char name, Edge edge) {
            this.name = name;
            this.edge = edge;
        }
    }

    public static class Edge {
        public char fromTownName;
        public char destTownName;
        public int distance;
        public Edge(char fromTownName, char destTownName, int distance) {
            this.fromTownName = fromTownName;
            this.destTownName = destTownName;
            this.distance = distance;
        }
    }

    public static void main(String[] args) {
        // input
        Scanner sc = new Scanner(System.in);
        int townCnt = sc.nextInt();
        int fuelAmt = sc.nextInt();
        int edgeCnt = sc.nextInt();
        sc.nextLine();

        List<Edge> edgeList = new ArrayList<Edge>();
        for (int i = 0; i < edgeCnt; ++i) {
            String[] edgeInfo = sc.nextLine().split(" ");
            edgeList.add(new Edge(edgeInfo[0].charAt(0), edgeInfo[1].charAt(0), Integer.valueOf(edgeInfo[2])));
        }
        sc.close();

        final char bgnTownName = 'A';
        final char destTownName = townCnt - '0';

        // 1. 중복없이 목적지에 도달할 수 있는 모든 방법을 찾아야 함 (깊이우선을 쓰자)



        // 2. 그 결과들중 가장 이익이 높은것을 출력하면 됨 (동일 결과가 있을 시 -1 출력)



        // logic
        System.out.println("Answer!");

        /*
        8 30
        7
        A B 3
        B C 5
        C F 2
        F G 2
        A D 5
        D E 10
        E H 30

        A D E H 700 15
        최대이익경로 최종보유금 최종연료

        마을마다 100원에 물건구매, 300원에 판매가능
        첫마을은 구매만가능
        마지막마을은 판매만가능
        무조건 사고팔고 해야함
        
        중복으로 못돌아다님
        첫마을제외 마을마다 10리터 연료줌
        목적지도착, 연료많이남음, 돈많이 순으로 우선순위
        여러 최적결과 존재 시 -1출력
        */
    }
}