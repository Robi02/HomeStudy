/**
문제 설명
화장실
오프라인 필기 테스트의 시험 감독을 맡게 된 코니는 혹시 부정행위가 일어나지는 않을까 시험장을 구석구석 살펴보았다. 지원자들을 매의 눈으로 주시하던 코니는 놓친 부분을 발견했다. 바로 화장실이었다! 지원자들이 화장실에 같이 가서 답을 공유하지는 않을까? 걱정이 많은 코니는 한 가지 사실을 깨닫고 마음을 놓을 수 있었다. LINE의 화장실은 모든 지원자들을 서로 다른 화장실로 보낼 수 있을 만큼 넉넉하다는 것이었다.
지원자의 수와 지원자들이 화장실에 간 시간과 화장실에서 돌아온 시간의 목록이 주어졌을 때, 모든 지원자들이 서로 다른 화장실에 들어갈 수 있는 화장실의 최소 개수를 구하여라.

입력 형식
첫 번째 줄에는 지원자의 수 N이 들어온다
N은 1000 이하의 양의 정수이다
두 번째 줄부터 N+1번째 줄까지 각 지원자가 화장실에 간 시간과 화장실에서 돌아온 시간이 주어진다
화장실에 간 시간과 화장실에서 돌아온 시간은 모두 150 이하의 음이 아닌 정수이며, 화장실에서 돌아온 시간은 화장실에서 간 시간보다 항상 크다
화장실에 가지 않는 지원자는 없다
출력 형식
모든 지원자들이 서로 다른 화장실에 들어갈 수 있는 화장실 수의 최솟값을 출력한다
입출력 예제
입력

3
0 10
10 15
20 30
출력

1
입력

2
5 15
0 10
출력

2
 */

import java.util.Scanner;

public class Q3 {

    public static class User {
        public int in;
        public int out;

        public User(int in, int out) {
            this.in = in;
            this.out = out;
        }

        @Override
        public String toString() {
            return "[" + this.in + ", " + this.out + "]";
        }

        public boolean hasConflict(User otherUser) {
            if (this.in < otherUser.out && this.out > otherUser.in) return true;
            return false;
        }
    }

    public static void main(String[] args) {
        final long bgnTime = System.currentTimeMillis();
        // ================================================================

        Scanner sc = new Scanner(System.in);
        int userCnt = sc.nextInt();
        User[] userAry = new User[userCnt];

        for (int i = 0; i < userCnt; ++i) {
            userAry[i] = new User(sc.nextInt(), sc.nextInt());
        }

        sc.close();

        //
        int maxToiletCnt = Integer.MIN_VALUE;

        for (int i = 0; i < userCnt; ++i) {
            User user = userAry[i];
            int toiletCnt = 1;

            for (int j = 0; j < userCnt; ++j) {
                if (i == j) continue;

                // 겹치는지 체크
                User otherUser = userAry[j];
                if (user.hasConflict(otherUser)) {
                    ++toiletCnt;
                }
            }

            if (toiletCnt > maxToiletCnt) {
                maxToiletCnt = toiletCnt;
            }
        }

        System.out.println(maxToiletCnt);

        // ================================================================
        final long elapsedTime = System.currentTimeMillis() - bgnTime;
        System.out.println("ElapsedTime : " + elapsedTime + "ms");
    }
}