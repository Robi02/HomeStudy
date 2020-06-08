import java.util.EnumSet;
import java.util.Set;

public class Item36 {
    /**
     * [Item36] 비트 필드 대신 EnumSet을 사용하라
     * 
     * [핵심]
     * 열거할 수 있는 타입을 한데 모아 집합 형태로 사용한다고 해도 비트 필드를 사용할 이유는 없다.
     * EnumSet 클래스가 비트 필드 수준의 명료함과 성능을 제공하고 Item34에서 설명한
     * 열거 타입의 장점까지 선사하기 때문이다.
     * EnumSet의 유일한 단점이라면 불변 EnumSet을 만들 수 없다는 것이다.
     * 그래도 향후 릴리스에서는 수정되리라 본다. (Java11까지 수정되지 않았다...)
     * 그때까지는 명확성과 성능이 조금 희생되지만 Collections.unmodifiableSet으로 EnumSet을 감싸 사용할 수 있다.
     * 
     */

    // [1] 비트 필드 열거 상수 - 구닥다리 기법!
    public static class Text {

        public static final int SYTLE_BOLD          = 1 << 0; // 1
        public static final int SYTLE_ITALIC        = 1 << 1; // 2
        public static final int SYTLE_UNDERLINE     = 1 << 2; // 4
        public static final int SYTLE_STRIKETHROUGH = 1 << 3; // 8

        // 매개변수 styles는 0개 이상의 STYLE_ 상수를 비트별 OR(|)한 값이다.
        public void applyStyles(int styles) {}
    }

    // [2] EnumSet - 비트 필드를 대체하는 현대적 기법
    public static class TextEx {
        
        public enum Style { BOLD, ITALIC, UNDERLINE, STRIKETHROUGH }

        // 어떤 Set을 넘겨도 되나, EnumSet이 가장 좋다.
        public void applyStyles(Set<Style> styles) {}
    }

    public static void main(String[] args) {
        // [1] 비트필드를 사용하는건 구닥다리 기법이다
        // 정수 열거 상수의 단점을 그대로 가지며, 비트필드값이 그대로 출력되면
        // 단순한 정수 열거 상수를 출력할 대보다 해석하기가 훨씬 어렵다.
        // 또한, 최대 몇 비트(32~64)가 필요한지 API 작성 시 미리 예측하여 적절한 타입을 선택해야 한다.
        Text text = new Text();
        text.applyStyles(Text.SYTLE_BOLD | Text.SYTLE_ITALIC);

        // [2] EnumSet 사용
        // EnumSet은 원소가 64개 이하라면 전체를 long변수 하나로 표현하여 비트필드에 비견하는 성능을 낸다.
        // 또한, Set인터페이스를 상속하여 removeAll(), retainAll() 같은 대량작업도 제공한다.
        // 심지어, 내부적으로 비트연산으로 처리하기에 상당히 빠른 효율을 보인다!
        TextEx textEx = new TextEx();
        textEx.applyStyles(EnumSet.of(TextEx.Style.BOLD, TextEx.Style.ITALIC, TextEx.Style.STRIKETHROUGH));
    }
}