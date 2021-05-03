package _9_generic_programing_principle;

public class Item63 {
    /**
     * [Item63] 문자열 연결은 느리니 주의하라
     * 
     * [핵심]
     * 원칙은 간단하다. 선으에 신경 써야 한다면 많은 문자열을 연결할 때는
     * 문자열 연결 연산자(+)를 피하자. 대신 StringBuilder의 append메서드를 사용하라.
     * 문자 배열을 사용하거나, 문자열을 (연결하지 않고) 하나씩 처리하는 방법도 있다.
     * 
     * ※ 문자열 연결 연산자로 문자열 n개를 잇는 시간은 n²에 비례한다.
     * 
     */
    
    public static void main(String[] args) {
        final int testCase = 10;

        // [1] 문자열 연결을 잘못 사용한 예
        {
            long bgnTime = System.currentTimeMillis();
            String result = "";
            for (int i = 0; i < testCase; ++i) {
                result += "attach_me!";
            }
            long endTime = System.currentTimeMillis();
            System.out.println("Case1:");
            System.out.println("Time: " + (endTime - bgnTime) + "ms.");
        }

        // [2] StringBuilder로 성능 개선
        {
            long bgnTime = System.currentTimeMillis();
            StringBuilder result = new StringBuilder(128);
            for (int i = 0; i < testCase; ++i) {
                result.append("attach_me!");
            }
            long endTime = System.currentTimeMillis();
            System.out.println("Case2:");
            System.out.println("Time: " + (endTime - bgnTime) + "ms.");
        }

        // 50000케이스 테스트 결과...
        // Case1:
        // Time: 9742ms.
        // Case2:
        // Time: 2ms.
        // 놀라울 정도로 속도 차이가 난다!
    }
}