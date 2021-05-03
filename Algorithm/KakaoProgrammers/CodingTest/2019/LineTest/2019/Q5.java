
/**
 * 문제 설명 코니와 문의 술래잡기 가로, 세로가 각각 n과 m인 모눈종이 공간에서 코니와 문이 술래잡기를 하고 있다. 이때, 코니가
 * (x,y)위치로 도망 간 뒤, 문이 코니를 가장 빨리 잡을 수 있는 경우의 수는 몇 가지인가?
 * 
 * 다음은 코니와 문의 술래잡기 규칙이다.
 * 
 * 코니는 도망간 뒤 이동하지 않는다 문은 (0,0) 지점에서 게임을 시작한다 문은 가로, 세로로만 이동이 가능하다 한 칸을 이동할 때 1초의
 * 시간이 필요하다 코니가 모눈종이 공간 밖으로 도망 간 경우 문은 코니를 잡을 수 없다 모눈종이 공간의 n, m 크기는 1부터 24 사이의
 * 자연수이다 (0 < n,m < 25) 예시 아래는 가로x세로 9x9 모눈종이 공간이다. 코니가 (2,1) 위치로 도망을 갔다. 문이
 * (0,0) 위치에서 출발하여 코니를 가장 빨리 잡을 수 있는 경우의 수는 3가지이며 3초의 시간이 걸린다.
 * 
 * 6b613880-d88e-11e9-8485-f3c00a5d126f.png
 * 
 * 입력 형식 첫 줄에는 술래잡기할 모눈종이 공간의 가로 세로를 입력한다 두 번째 줄에는 코니가 도망간 위치의 가로 세로 좌표를 입력한다 출력
 * 형식 표준 출력으로 결과를 출력한다 문이 코니를 잡을 수 있을때, 첫 줄에는 문이 코니를 잡기까지 필요한 최소 시간을 출력한다 문이 코니를
 * 잡을 수 있을때, 둘째 줄에는 문이 코니를 최소 시간으로 잡는 경우의 수를 출력한다 문이 코니를 잡을 수 없거나 게임이 시작될 수 없는
 * 경우, fail 을 출력한다 입출력 예제 입력
 * 
 * 9 9 2 1 출력
 * 
 * 3 3
 * 
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Q5 {

    // 두 점사이의 길이
    public static double dtBt2Pt(int x1, int y1, int x2, int y2) {
        int dx = x1 - x2;
        int dy = y1 - y2;
        return Math.sqrt(dx * dx + dy * dy);
    }
    
    // 깊이우선 탐색 사용
    // 너비우선 탐색으로 접근하는게 일반적으로 보이지만, 중간에 장애물도 없고
    // 끊긴 경로등도 없어서 매 이동시 코니와 계속 가까워지게 되는듯 하다...
    // 이동 후 두 점사이의 거리가 가까워졌는지를 재귀 탈출조건으로 구현
    public static void dfs(List<Integer> ansList, int mx, int my,
                           int cx, int cy, int tx, int ty,
                           int depth, double lastTgtDist) {
        if (cx < 0 || cx > mx || cy < 0 || cy > my) {
            return;
        }

        // 목적지 도달 체크
        if (cx == tx && cy == ty) {
            ansList.add(depth);
            return;
        }

        // 한 번의 이동으로 목표와의 거리가 가까워지지 않았으면 해당 경로 버림
        double td = dtBt2Pt(cx, cy, tx, ty);

        if (lastTgtDist < td) {
            return;
        }

        dfs(ansList, mx, my, cx - 1, cy, tx, ty, depth + 1, td); // 좌
        dfs(ansList, mx, my, cx + 1, cy, tx, ty, depth + 1, td); // 우
        dfs(ansList, mx, my, cx, cy - 1, tx, ty, depth + 1, td); // 상
        dfs(ansList, mx, my, cx, cy + 1, tx, ty, depth + 1, td); // 하
    }

    public static void main(String[] args) {
        final long bgnTime = System.currentTimeMillis();
        // ================================================================

        Scanner sc = new Scanner(System.in);
        final int sx = sc.nextInt(); // 보드 크기 X
        final int sy = sc.nextInt(); // 보드 크기 Y
        final int tx = sc.nextInt(); // 코니(타겟) X 
        final int ty = sc.nextInt(); // 코니(타겟) Y

        //
        List<Integer> ansList = new ArrayList<Integer>();

        if (tx <= sx && ty <= sy) { // 보드를 벗어난 코니는 dfs통과 안시킴 -> fail
            dfs(ansList, sx, sy, 0, 0, tx, ty, 0, dtBt2Pt(0, 0, tx, ty));
        }

        if (!ansList.isEmpty()) {
            System.out.println(ansList.get(0));
            System.out.println(ansList.size());
        }
        else {
            System.out.println("fail");
        }

        // ================================================================
        final long elapsedTime = System.currentTimeMillis() - bgnTime;
        System.out.println("ElapsedTime : " + elapsedTime + "ms");
    }
}