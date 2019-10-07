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

public class Q3 {

    public static class RoundQueue {
        List<Player> playerList;

        public RoundQueue(int size) {
            this.playerList = new ArrayList<Player>(size);
        }
        public void add(Player player) {
            this.playerList.add(player);
        }
        public Player get(int idx) {
            return this.playerList.get(idx);
        }
        public int getCircularIdx(int idx) {
            if (idx >= playerList.size()) {
                return idx % playerList.size();
            }
            else if (idx < 0) {
                return playerList.size() + idx;
            }
            return idx;
        }
        public Player[] getLeftRightPlayer(int idx) {
            Player[] result = new Player[2];
            result[0] = this.playerList.get(getCircularIdx(idx - 1));
            result[1] = this.playerList.get(getCircularIdx(idx + 1));
            return result;
        }
    }

    public static class Player {
        public int candy;
        public int chainCandy;
        public List<Player> followerList;
        public Player(int candy, int chainCandy) {
            this.candy = candy;
            this.chainCandy = chainCandy;
            this.followerList = new ArrayList<Player>();
        }
        public void addCandy() {
            this.candy += 1;
        }
        public void addCandyWithChain() {
            this.candy += 1;
            this.chainCandy += 1;            
            if (this.chainCandy == 1) {
                for (Player follower : followerList) {
                    follower.addCandyWithChain();
                }
            }
        }
        public void addFollower(Player follower) {
            this.followerList.add(follower);
        }
        public void prepareNextRound() {
            this.chainCandy = 0;
        }
    }

    public static void main(String[] args) { try {
        // input
        Scanner sc = new Scanner(System.in);
        int playerCnt = sc.nextInt();
        sc.nextLine();

        // 플레이어 세팅
        RoundQueue playerQueue = new RoundQueue(playerCnt);
        for (int i = 0; i < playerCnt; ++i) {
            playerQueue.add(new Player(0, 0));
        }

        // 카드로그 획득
        String[] logs = sc.nextLine().split(" ");
        sc.close();

        // 로그 분석 시작
        int playerIdx = 0;
        for (int i = 0; i < logs.length; ++i) {
            Player logPlayer = playerQueue.get(playerIdx);

            switch (logs[i]) {
                case "A":
                    logPlayer.addCandyWithChain();
                    break;
                case "J":
                    for (Player tgtPlayer : playerQueue.getLeftRightPlayer(playerIdx)) {
                        tgtPlayer.addCandyWithChain();
                    }
                    break;
                case "Q":
                    for (Player tgtPlayer : playerQueue.playerList) {
                        tgtPlayer.addCandy(); // no chain
                    }
                    break;
                case "K":
                    logPlayer.addFollower(playerQueue.get(Integer.valueOf(logs[++i])));
                    break;
            }

            // 플레이어 초기화
            for (Player player : playerQueue.playerList) {
                player.prepareNextRound();
            }

            ++playerIdx;
            playerIdx %= playerCnt;
        }

        // 출력
        StringBuilder ansSb = new StringBuilder();
        for (Player player : playerQueue.playerList) {
            ansSb.append(player.candy).append(' ');
        }

        ansSb.setLength(ansSb.length() - 1);
        System.out.println(ansSb.toString());
    }
    catch (Exception e) { e.printStackTrace(); }
    }
}