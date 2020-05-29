// 정적 임포트
// import static com.PhysicalConstants;

public class Item22 {
    /**
     * [Item22] 인터페이스는 타입을 정의하는 용도로만 사용하라
     * 
     * [핵심]
     * 인터페이스는 타입을 정의하는 용도로만 사용해야 한다.
     * 상수 공개용 수단으로 사용하지 말자.
     * 
     */
    
    public static interface IPhysicalConstants {
        // 상수 인터페이스 안티패턴은 인터페이스를 잘못 사용한 예다.
        // 추후 이 상수들을 쓰지 않게 되더라도 바이너리 호환성을 위해 여전히 남아있어야 한다.
        // final이 아닌 클래스가 이 인터페이스를 구현한다면 모든 하위클래스의 네임스페이스가
        // 이 인터페이스가 정의한 상수로 오염되어버린다.
        // 이 방식보다 아래의 방식을 사용하자.
        static final double AVOGADROS_NUMBER   = 6.022_140_857e23; // 아보가드로 수 (1/몰)
        static final double BOLTZMANN_CONSTANT = 1.380_648_52e-23; // 볼츠만 상수 (J/K)
        static final double ELECTRON_MASS      = 9.109_383_56e-31; // 전자 질량 (kg)
    }

    public static class PhysicalConstants {
        private PhysicalConstants() {} // 인스턴스화 방지
        public static final double AVOGADROS_NUMBER   = 6.022_140_857e23; // 아보가드로 수 (1/몰)
        public static final double BOLTZMANN_CONSTANT = 1.380_648_52e-23; // 볼츠만 상수 (J/K)
        public static final double ELECTRON_MASS      = 9.109_383_56e-31; // 전자 질량 (kg)
    }

    public static void main(String[] args) {
        System.out.println(PhysicalConstants.AVOGADROS_NUMBER);
        
        // 정적 임포트를 사용한 경우 (맨 위 참고)
        // (패키지 경로를 잡아준다면 사용 가능해진다.)
        // System.out.println(AVOGADROS_NUMBER);
    }
}