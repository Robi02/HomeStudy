public class Q5 {
    
    public String solution(int[] testCase) {
        return null;
    }

    public static void main(String[] args) {
        // ================================================================
        final Q5 solution = new Q5();
        final int[][] testCases = new int[][] { // #1 Set input type!
            { 3, 3, 3, 3, 3, 3 },
            { 1, 1, 1, 1, 1, 1 }
        };
        // #2 Add extra input!
        // ================================================================
        final long bgnTime = System.currentTimeMillis();
        for (int i = 0; i < testCases.length; ++i) {
            final String result = solution.solution(testCases[i]); // #3 Set return type!
            System.out.println("Result : " + result); // #4 Set output type!
        }
        final long elapsedTime = System.currentTimeMillis() - bgnTime;
        // ================================================================
        System.out.println("ElapsedTime : " + elapsedTime + "ms");
        // ================================================================
    }
}