
/**
 * 문제 설명 Number Printing 0 ~ 9의 숫자를 크기와 정렬 방식을 달리하여 인쇄하는 프로그램을 만들고자 한다.
 * 
 * 입력 형식 첫 번째 줄에는 입력 받을 데이터의 갯수와 정렬 방식을 나타내는 값이 공백으로 구분되어 적혀 있다 입력 받을 데이터 개수는 1
 * <= N <= 100 사이이다 정렬 방식을 나타내는 값은 TOP, BOTTOM 또는 MIDDLE 중 하나이다 TOP은 위 정렬,
 * BOTTOM은 아래 정렬, 그리고 MIDDLE은 중앙 정렬을 의미 한다 두 번째 줄부터 N개의 줄은 크기 숫자들 형식으로 입력된다 크기는
 * 출력할 숫자의 가로 크기를 의미하며 3 이상의 홀수이다 숫자들은 출력 해야 할 숫자열을 의미한다 출력 형식 숫자를 표시하는 부분은 #
 * 문자, 여백을 표시하는 부분은 . 문자를 이용하도록 한다 숫자의 가로 크기는 입력으로 받은 값이다 숫자는 반드시 . 문자를 포함한 크기여야
 * 한다 아래 예시의 숫자 1 또는 6을 보면, 해당 숫자는 . 문자 포함하여 가로 크기가 3이다 숫자의 세로 크기는 입력 받은 가로 크기의
 * 값 중 제일 큰 값을 이용하여 계산한다 제일 큰 값이 n이라 할 때, 2n-1 이다 (제일 큰 가로 크기가 5일 경우 세로 크기는 9)
 * 출력할 숫자 사이에는 한 칸의 공백을 둔다 출력할 숫자는 가로 크기가 3인 경우 아래와 같은 모양이 되도록 한다 ### ..# ###
 * ### #.# ### #.. ### ### ### #.# ..# ..# ..# #.# #.. #.. ..# #.# #.# #.# ..#
 * ### ### ### ### ### ..# ### ### #.# ..# #.. ..# ..# ..# #.# ..# #.# ..# ###
 * ..# ### ### ..# ### ### ..# ### ..# 입출력 예제 입력
 * 
 * 4 TOP 5 123 3 45 5 7890 3 6 출력
 * 
 * ....# ##### ##### #.# ### ##### ##### ##### ##### #.. ....# ....# ....# #.#
 * #.. ....# #...# #...# #...# #.. ....# ....# ....# ### ### ....# #...# #...#
 * #...# ### ....# ....# ....# ..# ..# ....# #...# #...# #...# #.# ....# #####
 * ##### ..# ### ....# ##### ##### #...# ### ....# #.... ....# ... ... ....#
 * #...# ....# #...# ... ....# #.... ....# ... ... ....# #...# ....# #...# ...
 * ....# #.... ....# ... ... ....# #...# ....# #...# ... ....# ##### ##### ...
 * ... ....# ##### ....# ##### ...
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Q6 {
    
    public static class DigitSeg {
        public int size;
        public int num;

        public DigitSeg(int size, int num) {
            this.size = size;
            this.num = num;
        }
    }

    public static void main(String[] args) {
        final long bgnTime = System.currentTimeMillis();
        // ================================================================

        Scanner sc = new Scanner(System.in);
        String[] inputCntAndAlign = sc.nextLine().split(" ");
        final int inputCnt = Integer.valueOf(inputCntAndAlign[0]);
        // 0: TOP, 1: MIDDLE, 2: BOTTOM
        final int alignMode = inputCntAndAlign[1].equals("TOP") ? 0 : inputCntAndAlign[1].equals("MIDDLE") ? 1 : 0;
        List<DigitSeg> inputList = new ArrayList<DigitSeg>();
        int maxSize = Integer.MIN_VALUE;
        
        for (int i = 0; i < inputCnt; ++i) {
            String[] inputStr = sc.nextLine().split(" ");
            int size = Integer.valueOf(inputStr[0]);

            // 연속된 숫자는 쪼개서 관리 (ex: 5 1234 -> 5 1, 5 2, 5 3, 5 4)
            for (int j = 0; j < inputStr[1].length(); ++j) {
                char digit = inputStr[1].charAt(j);
                int num = Integer.valueOf(digit);
                inputList.add(new DigitSeg(size, num));
            }

            if (size > maxSize) {
                maxSize = size;
            }
        }

        sc.close();

        // 디지털세그먼트 인터프리터 시작
        // 1. 일단 그리기 버퍼부터 생성
        int bufHeight = 2 * maxSize - 1;  // 높이: 2n-1 (n:가장 큰 문자크기)
        char[][] buffer = new char[bufHeight][];
        
        for (int i = 0; i < buffer.length; ++i) {
            // 마지막열은 숫자 구분 띄어쓰기가 없음
            buffer[i] = new char[4 * inputList.size() - 1];
            Arrays.fill(buffer[i], '.');
        }

        // 2. 명령어대로 숫자를 버퍼에 그림
        int xIdx = 0;
        for (int i = 0; i < inputList.size(); ++i) {
            final DigitSeg digit = inputList.get(i);
            final int size = digit.size;
            int yIdx = alignMode == 0 ? 0 : alignMode == 1 ? (bufHeight - (2 * size - 1) - (size / 2)) : bufHeight - (2 * size - 1);

            switch (digit.num) {
                case 0:
                    
                break;

                case 1:
                break;

                case 2:
                break;

                case 3:
                break;

                case 4:
                break;

                case 5:
                break;

                case 6:
                break;

                case 7:
                break;

                case 8:
                break;

                case 9:
                break;
            }
            
            xIdx += size + 1;
        }

        // 3. 버퍼의 내용을 출력
        for (int i = 0; i < buffer.length; ++i) {
            System.out.println(new String(buffer[i]));
        }

        // ================================================================
        final long elapsedTime = System.currentTimeMillis() - bgnTime;
        System.out.println("ElapsedTime : " + elapsedTime + "ms");
    }
}