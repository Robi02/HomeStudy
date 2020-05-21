
public class Item10 {
    /**
     * [Item10] equals는 일반 규약을 지켜 재정의하라
     * 
     * [핵심]
     * 꼭 필요한 경우가 아니라면 equals를 재정의하지 말자. 많은 경우에 Object의 equals가
     * 여러분의 원하는 비교를 정확히 수행해 준다. 재정의해야 할 때는 그 클래스의 핵심 필드
     * 모두를 빠짐없이, 다섯 가지 규약을 확실히 지켜가며 비교해야 한다.
     * 
     * [재정의하지 않아야 하는 경우]
     * 1) 각 인스턴스가 본질적으로 고유한 경우. (ex. 값을 표현하는게 아닌 동작하는 객체를 표현: Thread)
     * 2) 인스턴스의 논리적 동치성을 검사할 일이 없는 경우.
     * 3) 상위 클래스의 equals()가 하위 클래스에서도 딱 들어맞는 경우.
     * 4) 클래스가 private이거나 package-private이고 equals() 메서드를 호출할 일이 없는 경우.
     * 
     * [재정의해야 하는 경우]
     * 객체 식별성이 아니라 논리적 동치성을 비교해야 하는데, 상위 클래스에서 equals()가
     * 논리적 동치성을 비교하도록 재정의되지 않은 경우.
     * 
     * [equals()의 규약]
     * 1) 반사성(reflexivity): x.equals(x) == true. (x != null)
     * 2) 대칭성(symmetry): x.equals(y) == y.equals(x). (x, y != null)
     * 3) 추이성(transitivity): x.equals(y) == y.equals(z) == x.equals(z) (x, y, z != null)
     * 4) 일관성(consistency): x.equals(y) == x.equals(y) == x.equals(y) == ... (x, y != null)
     * 5) not-null: x.equals(null) == false (x != null)
     * 
     * [추가로 주의해야 할 사항]
     * 1) equals()를 재정의할 땐 hashCode() 반드시 재정의하자. (Item11)
     * 2) 너무 복잡하게 해결하려 들지 말자. 일반적으로 별칭(alias)은 비교하지 않는것이 좋다. (File클래스라면 파일의 심볼릭 링크등...)
     * 3) Object 외의 타입을 매개변수로 받는 equals() 메서드는 선언하지 말자.
     * 
     */

    // 전형적인 equals 메서드의 예
    public static class PhoneNumber {
        private final short areaCode, prefix, lineNum;

        public PhoneNumber(int areaCode, int prefix, int lineNum) {
            this.areaCode = rangeCheck(areaCode, 999, "지역코드");
            this.prefix = rangeCheck(prefix, 999, "프리픽스");
            this.lineNum = rangeCheck(lineNum, 9999, "가입자 번호");
        }

        private static short rangeCheck(int val, int max, String arg) {
            if (val < 0 || val > max) {
                throw new IllegalArgumentException(arg + ": " + val);
            }

            return (short) val;
        }

        @Override public boolean equals(Object o) {
            if (o == this) {
                return true;
            }

            if (!(o instanceof PhoneNumber)) {
                return false;
            }

            PhoneNumber pn = (PhoneNumber) o;

            return pn.lineNum == lineNum && pn.prefix == prefix && pn.areaCode == areaCode;
        }
    }

    public static void main(String[] args) {
        
    }
}