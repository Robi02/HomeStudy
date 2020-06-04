public class Item35 {
    /**
     * [Item35] ordinal 메서드 대신 인스턴스 필드를 사용하라
     * 
     * [핵심]
     * 열거 타입 상수에 연결된 값은 ordinal 메서드로 얻지 말고, 인스턴스 필드에 저장하자.
     * ordinal() 메서드는 EnumSet과 EnumMap같이 열거 타입 기반의 범용 자료구조에 쓸 목적으로 설계되었다.
     * 
     */

    public enum Ensemble {
        SOLO, DUET, TRIO, QUARTER, QUINTET,
        SEXTET, SEPTET, OCTET, NONET, DECTET;

        // ordinal() 메서드를 사용할수는 있지만, 유지보수가 끔찍해진다...!
        // 이 방식으로 "복 4중주(8명)"를 추가할 수 있나?
        // 이미 OCTET이 자리잡고 있으므로 할당할 수 없다!
        // 11~19명을 건너 뛰고 20명 합주단이면? 어떻게 표현하지?
        public int numberOfMusicians() { return ordinal() + 1; }
    }

    // 이렇게 개선할 수 있다!
    public enum EnsembleEx {
        SOLO(1),
        DUET(2),
        TRIO(3),
        QUARTER(4),
        QUINTET(5),
        SEXTET(6),
        SEPTET(7),
        OCTET(8),
        DOUBLE_QUARTET(8),
        NONET(9),
        DECTET(10),
        TRIPLE_QUARTET(12);

        private final int numberOfMusicians;

        EnsembleEx(int numberOfMusicians) {
            this.numberOfMusicians = numberOfMusicians;
        }

        public int numberOfMusicians() { return this.numberOfMusicians; }
    }
}