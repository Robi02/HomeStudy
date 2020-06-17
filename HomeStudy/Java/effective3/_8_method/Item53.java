package _8_method;

public class Item53 {
    /**
     * [Item53] 가변인수는 신중히 사용하라
     * 
     * [핵심]
     * 인수 개수가 일정하지 않은 메서드를 정의해야 한다면 가변인수가 반드시 필요하다.
     * 메서드를 정의할 때 필수 매개변수는 가변인수 앞에 두고,
     * 가변인수를 사용할 때는 성능 문제까지 고려하자.
     * 
     */

    // [1-1] 인수가 1개 이상이어야 하는 가변인수 메서드 - 잘못 구현한 예
    public static int min(int... args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("인수가 1개 이상 필요합니다.");
        }
        int min = args[0];
        for (int i = 1; i < args.length; ++i) {
            if (args[i] < min) {
                min = args[i];
            }
        }
        return min;
        
        // 인수가 1개면 터진다!
        // min의 초기값이 Integer.MAX_VALUE가 아닌 이상 for-each 문도 사용할 수 없다.
    }

    // [1-2] 위 메서드를 재대로 설계하는 방법
    public static int min(int firatArg, int... remainingArgs) {
        int min = firatArg;
        for (int arg : remainingArgs) {
            if (arg < min) {
                min = arg;
            }
        }
        return min;
    }

    // [2] 메서드 호출의 95%가 매개변수가 3개 이하라면...
    public void foo() {}
    public void foo(int a1) {}
    public void foo(int a1, int a2) {}
    public void foo(int a1, int a2, int a3) {}
    public void foo(int a1, int a2, int a3, int... a4) {}
    // foo(int int int) 까지는 가변인수(배열)을 생성하지 않기에 속도도 더 빠르다.
    // 5%의 경우에만 가변인수 메서드를 사용하게 된다.
}