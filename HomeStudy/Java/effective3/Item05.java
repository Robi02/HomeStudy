import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Item05 {
    /**
     *  [Item05] 자원을 직접 명시하지 말고 의존 객체 주입을 사용하라.
     * 
     *  [핵심]
     *  클래스가 내부적으로 하나 이상의 자원에 의존하고, 그 자원이 클래스 동작에 영향을 준다면
     *  싱글턴과 정적 유틸리티 클래스는 사용하지 않는 것이 좋다.
     *  이 자원들을 클래스가 직접 만들게 해서도 안 된다.
     *  대신 필요한 자원을 (혹은 그 자원을 만들어주는 팩토리를) 생성자에 (혹은 정적 팩터리나 빌더에)
     *  넘겨주자. 의존 객체 주입(Dependency Injection)이라 하는 이 기법은
     *  클래스의 유연성, 재사용성, 테스트 용이성을 기막히게 개선해준다.
     * 
     */

    public static class Lexicon {}

    // [1] 정적 유틸리티를 잘못 사용한 예 - 유연하지 않고 테스트하기 어렵다
    public static class SpellChecker1 {
        private static final Lexicon dictionary = new Lexicon();

        private SpellChecker1() {}; // 객체 생성 방지

        public static boolean isValid(String word) { return true; }
        public static List<String> suggestions(String typo) { return new ArrayList<String>(); }
    }

    // [2] 싱글턴을 잘못 사용한 예 - 유연하지 않고 테스트하기 어렵다
    public static class SpellChecker2 {
        private static final Lexicon dictionary = new Lexicon();

        private SpellChecker2() {};
        public static SpellChecker2 INSTANCE = new SpellChecker2();

        public static boolean isValid(String word) { return true; }
        public static List<String> suggestions(String typo) { return new ArrayList<String>(); }
    }

    // [1,2]방식 모두 사전을 단 하나만 사용한다는 점에서 훌륭해 보이지 않는다.
    // 실전에서는 사전(Lexicon)이 언어별로도 있고, 특수 어휘용도 있고, 테스트용 사전이 필요할수도 있다.
    
    // [3] DI - 의존 객체 주입은 유연성과 테스트 용이성을 높여준다
    public static class SpellChecker3 {
        private final Lexicon dictionary;

        public SpellChecker3(Lexicon dictionary) {
            this.dictionary = Objects.requireNonNull(dictionary);
        }

        public static boolean isValid(String word) { return true; }
        public static List<String> suggestions(String typo) { return new ArrayList<String>(); }
    }

    // 의존 객체 주입이 유연성과 테스트 용이성을 개선해주긴 하지만, 의존성이 수천개나 되는
    // 큰 프로젝트에서는 코드를 어지럽게 만들기도 한다. 대거(Dagger), 주스(Guice), 스프링(Spring)
    // 같은 의존 객체 주입 프레임워크를 사용하면 이런 어질러짐을 해소할 수 있다.
}