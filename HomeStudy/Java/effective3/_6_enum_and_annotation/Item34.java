package _6_enum_and_annotation;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Item34 {
    /**
     * [Item34] int 상수 대신 열거 타입을 사용하라
     * 
     * [핵심]
     * 열거 타입은 확실히 정수 상수보다 뛰어나다.
     * 더 일기 쉽고 안전하고 강력하다.
     * 대다수 열거 타입이 명시적 생성자나 메서드 없이 쓰이지만,
     * 각 상수를 특정 데이터와 연결짓거나 상수마다 다르게 동작하게 할 때는 필요하다.
     * 드물게는 하나의 메서드가 상수별로 다르게 동작해야 할 때도 있다.
     * 이런 열거 타입에서는 switch 문 대신 [3]상수별 메서드 구현을 사용하자.
     * 열거 타입 상수 일부가 같은 동작을 공유한다면 [4]전략 열거 타입 패턴을 사용하자.
     * 
     * [enum의 올바른 선언]
     * 1) 열거형 클래스 혹은 해당 패키지에서만 유용한 기능은 private나 package-private 메서드로 구현.
     * 2) 널리 쓰이는 열거 타입은 톱레벨 클래스로, 특정 톱레벨 클래스에서만 쓰인다면 클래스의 멤버 클래스로 선언.
     * 
     */

    // [1] 정수 열거 패턴 - 상당히 취약하다!
    public static final int APPLE_FUJI          = 0;
    public static final int APPLE_PIPIN         = 1;
    public static final int APPLE_GRANNY_SMITH  = 2;
    
    public static final int ORANGE_NAVEL    = 0;
    public static final int ORANGE_TEMPLE   = 1;
    public static final int ORANGE_BLOOD    = 2;

    // [2-1] 가장 단순한 열거 타입
    public enum Apple { FUJI, PIPPIN, GRANNY_SMITH };
    public enum Orange { NAVEL, TEMPLE, BLOOD };

    // [2-2] 데이터와 메서드를 갖는 열거 타입
    public enum Planet {
        MERCURY (3.302e+32, 2.439e6), // ≒ public static final Planet MERCURY = new Planet(3.302e+32, 2.439e6);
        VENUS   (4.869e+24, 6.052e6),
        EARTH   (5.975e+24, 6.378e6),
        MARS    (6.419e+23, 3.393e6),
        JUPITER (1.899e+27, 7.149e7),
        SATURN  (5.685e+26, 6.027e7),
        URANUS  (8.683e+25, 2.556e7),
        NEPTUNE (1.024e+26, 2.477e7);

        // 열거형은 기본적으로 "불변"이라 모든 필드가 final 이어야 한다.
        private final double mass;              // 질량(단위: 킬로그램)
        private final double radius;            // 반지름(단위: 미터)
        private final double surfaceGravity;    // 표면중력(단위: m/s²)

        // 중력 상수 (단위: m³/kg s²)
        private static final double G = 6.67300e-11;

        Planet(double mass, double radius) {
            this.mass = mass;
            this.radius = radius;
            this.surfaceGravity = G * mass / (radius * radius);
        }

        public double mass()            { return this.mass; }
        public double radius()          { return this.radius; }
        public double surfaceGravity()  { return this.surfaceGravity; }

        public double surfaceWeight(double mass) {
            return this.mass * this.surfaceGravity; // F = ma
        }
    }

    // [3] 상수별 메서드 구현을 구현한 열거 타입
    // + 상수별 클래스 몸체와 데이터를 사용한 열거 타입
    public enum Operation {
        PLUS("+")    {public double apply(double x, double y){return x + y;}},
        MINUS("-")   {public double apply(double x, double y){return x - y;}},
        TIMES("*")   {public double apply(double x, double y){return x * y;}},
        DIVIDE("/")  {public double apply(double x, double y){return x / y;}};

        private final String symbol;
        
        Operation(String symbol) {
            this.symbol = symbol;
        }
        
        @Override public String toString() { return this.symbol; }
        
        // toString() 재정의 시 고려해볼만한 fromString() 메서드
        private static final Map<String, Operation> stringToEnum;

        static {
            stringToEnum = new HashMap<>();
            
            for (Operation op : values()) {
                stringToEnum.put(op.symbol, op);
            }
        }

        public static Optional<Operation> formString(String symbol) {
            return Optional.ofNullable(stringToEnum.get(symbol));
        }

        public abstract double apply(double x, double y);
    }

    // [4] 전략 열거 타입 패턴
    public enum PayrollDay {
        MONDAY      (PayType.WEEKDAY),
        TUESDAY     (PayType.WEEKDAY),
        WEDNESDAY   (PayType.WEEKDAY),
        THURSDAY    (PayType.WEEKDAY),
        FRIDAY      (PayType.WEEKDAY),
        SATURDAY    (PayType.WEEKEND),
        SUNDAY      (PayType.WEEKEND);

        private final PayType payType;

        PayrollDay(PayType payType) { this.payType = payType; }

        int pay(int minuesWorked, int payRate) {
            return payType.pay(minuesWorked, payRate);
        }

        enum PayType {
            WEEKDAY {
                int overtimePay(int minsWorked, int payRate) {
                    return minsWorked <= MINS_PER_SHIFT ? 0 :
                        (minsWorked - MINS_PER_SHIFT) * payRate / 2;
                }
            },
            WEEKEND {
                int overtimePay(int minsWorked, int payRate) {
                    return minsWorked * payRate / 2;
                }
            };

            abstract int overtimePay(int mins, int payRate);
            private static final int MINS_PER_SHIFT = 8 * 60;

            int pay(int minsWorked, int payRate) {
                int basePay = minsWorked * payRate;
                return basePay + overtimePay(minsWorked, payRate);
            }
        }
    }

    public static void main(String[] args) {
        // [1] 향긋한 오렌지 향의 사과 소스?
        // 타입 안전을 보장할 방법도 없고 표현력도 별로다.
        int i = (APPLE_FUJI - ORANGE_TEMPLE) / APPLE_PIPIN;

        // [2] enum은 강력하다
        double earthWeight = Double.parseDouble("185.0");
        double mass = earthWeight / Planet.EARTH.surfaceGravity();

        for (Planet p : Planet.values()) {
            System.out.printf("%s에서의 무게는 %f이다.%n", p, p.surfaceWeight(mass));
        }

        // [3]
        double x = 2.0;
        double y = 4.0;

        for (Operation op : Operation.values()) {
            System.out.printf("%f %s %f = %f (%s)%n", x, op, y, op.apply(x, y), Operation.formString(op.toString()));
        }
    }
}