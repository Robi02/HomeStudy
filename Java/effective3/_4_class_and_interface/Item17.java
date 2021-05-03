package _4_class_and_interface;

public class Item17 {
    /**
     * [Item17] 변경 가능성을 최소화하라
     * 
     * [핵심]
     * 클래스는 꼭 필요한 경우가 아니라면 불변이어야 한다.
     * 불변으로 만들 수 없는 클래스라도 변경할 수 있는 부분을 최소화하자.
     * 따라서, 다른 합당한 이유가 없다면 모든 필드는 private final 이어야 한다.
     * 생성자는 불변식 설정이 모두 완료된, 초기화가 완벽히 끝난 상태의 객체를 생성해야 한다.
     * 아래 장단점을 잘 기억해 두자.
     * 
     * [대표적인 불변 클래스]
     * 1. String
     * 2. BigInteger, BigDecimal
     * 
     * [장점]
     * 1) 가변 클래스보다 설계하고 구현하고 사용하기 쉽고, 오류가 생길 여지가 적고 안전하다.
     * 2) 근본적으로 thread-safe하여 따로 동기화할 필요가 없다.
     * 3) 불변 클래스 객체는 여러 클래스에서 공유로 사용할 수 있는 정적 팩터리를 제공할 수 있다.
     *    -> 메모리 사용량과 가비지 컬렉션 비용이 줄어든다.
     * 4) 자유로운 공유가 가능하며 불변 객체끼리 내부 데이터를 공유할 수 있다. (BigInteger의 negate()가 그 예이다)
     * 5) 자체적으로 실패 원자성(메서드에서 예외가 발생한 후에도 그 객체는 여전히 메서드 호출전과 같아야 함)을 제공한다.
     * 
     * [단점]
     * 1) 값이 다르면 반드시 독립된 객체로 만들어야 한다. (값의 가짓수가 많으면 메모리등 비용이 크다)
     * 
     * [단점을 극복하기 위해]
     * 1) 다단계 연산(multistep operation)들을 예측하여 기본 기능(가변 동반 클래스 등)으로 제공한다.
     *    -> (String + StringBuilder) 불변 클래스 String을 어느정도 가변적으로 다룰 수 있다.
     * 
     * [불변 클래스의 규칙]
     * 1) 외부에 비치는 객체상태를 변경하는 메서드(변경자)를 제공하지 않음.
     * 2) 클래스를 확장할 수 없도록 함.
     * 3) 모든 필드를 final로 선언.
     * 4) 모든 필드를 private로 선언.
     * 5) 자신 외에는 내부의 가변 컴포넌트에 접근할 수 없도록 함.
     * 
     */

    // 불변 복소수 클래스
    // [2] 자신을 상속하지 못하게 하려면 final 클래스도 있지만 다음과 같은 방법도 있다.
    // (모든 생성자를 private 혹은 package-private으로 만들고 public 정적 팩터리를 제공)
    public static final class Complex { // [2]
        private final double re; // [3,4]
        private final double im; // [3,4]

        // [1,5]

        private Complex(double re, double im) {
            this.re = re;
            this.im = im;
        }

        public static Complex valueOf(double re, double im) {
            // 정적 팩터리 메서드로 조금 더 유연해진 모습
            return new Complex(re, im);
        }

        public double realPart() { return re; }
        public double imaginaryPart() { return im; }

        // plus() 메서드가 객체의 값을 변경하지 않는다는 사실을 강조하는 의도로
        // add같은 동사 대신에 전치사 plus를 사용했다.
        public Complex plus(Complex c) {
            return new Complex(re + c.re, im + c.im);
            // 위처럼 피연산자에 연산한 결과를 반환하지만,
            // 피연산자 자체는 그대로인 프로그래밍 패턴을 함수형 프로그래밍이라 한다.
        }

        public Complex minus(Complex c) {
            return new Complex(re - c.re, im - c.im);
        }

        public Complex times(Complex c) {
            return new Complex(re * c.re - im * c.im, re * c.im + im * c.re);
        }

        public Complex dividedBy(Complex c) {
            double tmp = c.re * c.re + c.im * c.im;
            return new Complex((re * c.re + im * c.im) / tmp, (im * c.re - re * c.im) / tmp);
        }

        @Override public boolean equals(Object o) {
            if (o == this) {
                return true;
            }

            if (!(o instanceof Complex)) {
                return false;
            }

            Complex c = (Complex) o;

            return Double.compare(c.re, re) == 0 && Double.compare(c.im, im) == 0;
        }

        @Override public int hashCode() {
            return 31 * Double.hashCode(re) + Double.hashCode(im);
        }

        @Override public String toString() {
            return "(" + re + " + " + im + "i)";
        }

        // cloan() 메서드나 복사 생성자 Complex(Complex c) 같은 메서드는
        // 불변클래스의 특성상 필요하지 않다.
        // 불변인 자기 자신을 사용하면 되는데, 똑같은 객체가 하나 더 필요한 이유가 무엇인가?
        // String 클래스의 String(String original) 복사 생성자는 이 사실을 잘 이해하지 못한 초창기에 만들어졌다고 한다.
    }
}