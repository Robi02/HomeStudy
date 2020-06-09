import java.util.HashSet;
import java.util.Set;

public class Item40 {
    /**
     * [Item40] @Override 애너테이션을 일관되게 사용하라
     * 
     * [핵심]
     * 재정의한 모든 메서드에 @Override 애너테이션을 의식적으로 달면 여러분이 실수했을 때
     * 컴파일러가 바로 알려줄 것이다. 예외는 한 가지뿐이다. 구체 클래스에서 상위 클래스의 추상
     * 메서드를 재정의한 경우엔 이 애너테이션을 달지 않아도 된다. (단다고 해서 해로울건 없다)
     * 
     */

    public static class Bigram { // 영어 알파벳 2개로 구성된 문자열을 표현하는 클래스
        private final char first;
        private final char second;

        public Bigram(char first, char second) {
            this.first = first;
            this.second = second;
        }

        // 오버로딩된 메서드
        /* @Override */ public boolean equals(Bigram b) {
            // @Override 애너테이션을 제거하면 컴파일 에러로
            // equals(Bigram b)가 슈퍼클래스에 존재하지 않음을 알 수 있다.
            // equals(Object obh)가 맞는 타입이기 때문이다.
            return (b.first == this.first && b.second == this.second);
        }

        // 오버라이딩된 메서드
        @Override public boolean equals(Object obj) {
            if (!(obj instanceof Bigram))
                return false;
            Bigram b = (Bigram) obj;
            return (b.first == this.first && b.second == this.second);
        }

        public int hashCode() {
            return 31 * first + second;
        }
    }

    public static void main(String[] args) {
        Set<Bigram> s = new HashSet<>();
        
        for (int i = 0; i < 10; ++i) {
            for (char ch = 'a'; ch <= 'z'; ++ch) {
                s.add(new Bigram(ch, ch));
            }
        }

        System.out.println(s.size());

        // Set<>은 중복을 허용하지 않으므로 26이 출력되어야 하나, 260이 출력된다!
        // equals(Bigram b)메서드가 "오버로딩" 되어 Set<> 내부에서 호출되지 않기 때문이다.
        // equals(Object obj)메서드가 "오버라이딩" 되도록 작성해 줘야 한다.
    }
}