import java.util.Arrays;
import java.util.Collection;

public class Item38 {
    /**
     * [Item38] 확장할 수 있는 열거 타입이 필요하면 인터페이스를 사용하라
     * 
     * [핵심]
     * 열거 타입 자체는 확장할 수 없지만, 인터페이스와 그 인터페이스를 구현하는 기본
     * 열거 타입을 함게 사용해 같은 효과를 낼 수 있다.
     * 이렇게 하면 클라이언트는 이 인터페이스를 구현해 자신만의 열거 타입
     * (혹은 다른 타입)을 만들 수 있다.
     * 그리고 API가 (기본 열거 타입을 직접 명시하지 않고) 인터페이스 기반으로 작성되었다면
     * 기본 열거 타입의 인스턴스가 쓰이는 모든 곳을 새로 확장한 열거 타입의 인스턴스로
     * 대체해 사용할 수 있다.
     * 
     */

    // [1] 인터페이스를 이용해 확장 가능 열거 타입을 흉내냄
    public interface Operation {
        double apply(double x, double y);
    }

    public enum BasicOperaiton implements Operation {
        PLUS("+") {
            public double apply(double x, double y) { return x + y; }
        },
        MINUS("-") {
            public double apply(double x, double y) { return x - y; }
        },
        TIMES("*") {
            public double apply(double x, double y) { return x * y; }
        },
        DIVIDE("/") {
            public double apply(double x, double y) { return x / y; }
        };

        private final String symbol;

        BasicOperaiton(String symbol) {
            this.symbol = symbol;
        }

        @Override public String toString() {
            return symbol;
        }
    }

    public enum ExtendedOperation implements Operation {
        EXP("^") {
            public double apply(double x, double y) {
                return Math.pow(x, y);
            }
        },
        REMAINDER("%") {
            public double apply(double x, double y) {
                return x % y;
            }
        };

        private final String symbol;

        ExtendedOperation(String symbol) {
            this.symbol = symbol;
        }

        @Override public String toString() {
            return symbol;
        }
    }

    public static void main(String[] args) {
        double x = 2.0;
        double y = 3.0;

        // 1
        test1(BasicOperaiton.class, x, y);
        test1(ExtendedOperation.class, x, y);

        // 2
        test2(Arrays.asList(BasicOperaiton.values()), x, y);
        test2(Arrays.asList(ExtendedOperation.values()), x, y);
    }

    private static <T extends Enum<T> & Operation> void test1(Class<T> opEnumType, double x, double y) {
        for (Operation op : opEnumType.getEnumConstants()) {
            System.out.printf("%f %s %f = %f%n", x, op, y, op.apply(x, y));
        }
    }

    public static void test2(Collection<? extends Operation> opSet, double x, double y) {
        for (Operation op : opSet) {
            System.out.printf("%f %s %f = %f%n", x, op, y, op.apply(x, y));
        }
    }
}