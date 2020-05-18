import java.util.regex.Pattern;

public class Item06 {
    /**
     *  [Item06] 불필요한 객체 생성을 피하라
     * 
     *  [핵심]
     *  가능하면 객체를 재활용하여 사용할 수 있도록 하자.
     *  예를 들어, 객체를 불변으로 생성하여 캐싱하여 사용할 수 있다면 그렇게 하자.
     *  특히 오토박싱/언박싱에 주의하여 코딩하자.
     * 
     *  Item50(방어적 복사)와 대조적인 조언인데, 방어적 복사가 필요한 상황에서
     *  필요없는 객체를 반복 생성했을 때 보다 캐싱된 객체를 재사용했을때의 피해가 훨씬 크다는것에 유의하자.
     *  방어적 복사에 실패하면 언제 터질지 모르는 버그와 보안구멍이 생기지만,
     *  불필요한 객체 생성은 그저 코드 형태와 성능에만 영향을 준다.
     * 
     *  즉 "항상 신중하게 생각을 하면서 코딩을 해야 한다."
     * 
     */

    public static void main(String[] args) {
        // [1]
        String s1 = new String("robi");
        // 이 코드는 실행될 때 마다 String 인스턴스를 새로 만든다.
        // 반복문 안에 들어간다면 최악의 효율을 갖는 코드가 될 것이다.
        String s2 = "robi";
        // 이 코드도 위처럼 동작할 것 같지만 전혀 다르다.
        // 이 코드는 같은 JVM안에서 이와 똑같은 문자열 리터럴을 사용하는
        // 모든 코드가 같은 객체를 재사용함이 보장된다. [JLS 3.10.5]

        // [2]
        Boolean b1 = new Boolean("true");       // Java9에서는 deprecated API가 되어버렸다
        Boolean b2 = Boolean.valueOf("true");   // 정적 팩터리 메서드 내부에 캐싱해둔 객체를 반환한다

        // [3]
        isRomanNumeralLowPerformance(s1);
        isRomanNumeralHighPerformance(s2);
        // 아래 선언된 static 메서드들을 살펴보자.
        // HighPerformance 메서드는 캐싱된 Pattern 객체를 사용하여 더 고효율로 동작한다.

        // [4]
        // 지연 초기화를 사용하여 위 [3]의 ROMAN 객체를 HighPerformance메서드
        // 호출 전까지 생성하지 않는 코드도 작성이 가능할 것이다.
        // 이 경우 사용 전까지 메모리를 덜 먹을 수 있는 장점이 있지만,
        // 코드를 복잡하게 만들면서 성능 개선 효과가 미미한 경우가 많기에 권장하진 않는다.

        // [5]
        // 박싱에 주의하자. 만약 sum을 'long' 대신 'Long'으로 선언했더라면
        // i를 Long으로 변환하면서 객체를 21억여번 생성하는 과정이 있었을 것이다.
        // 이 경우 엄청난 비용을 지불하게 된다.
        long sum = 0L;
        
        for (long i = 0; i <= Integer.MAX_VALUE; ++i) {
            sum += i;
        }
    }

    // [3-1]
    public static boolean isRomanNumeralLowPerformance(String s) {
        return s.matches("^(?=.)M*(C[MD]|D?C{0,3})(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$");
    }

    // [3-2]
    private static final Pattern ROMAN = Pattern.compile(
        "^(?=.)M*(C[MD]|D?C{0,3})(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$"
    );

    // [3-3]
    public static boolean isRomanNumeralHighPerformance(String s) {
        return ROMAN.matcher(s).matches();
    }
}