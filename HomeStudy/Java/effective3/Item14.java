import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Item14 {
    /**
     * [Item14] Comparable을 구현할지 고려하라
     * 
     * [핵심]
     * 순서를 고려해야 하는 값 클래스를 작성한다면 꼭 Comparable 인터페이스를 구현하여,
     * 그 인스턴스들을 쉽게 정렬하고, 검색하고, 비교 기능을 제공하는 컬렉션과 어우러지도록 해야 한다.
     * compareTo 메서드에서 필드의 값을 비교할 때 '<'와 '>'연산자는 쓰지 말야아 한다.
     * 그 대신 박싱된 깆본 타입 클래스가 제공하는 정적 compare 메서드나 (Integer.compare(...))
     * Comparator 인터페이스가 제공하는 비교자 생성 메서드를 사용하자.
     * 
     * 
     * [compareTo() 규약]
     * 1) x.compareTo(y) == -(y.compareTo(x)) -> 따라서, x.compareTo(y)는 y.compareTo(x)가 예외를 던질 때에 한해 예외를 던져야 한다.
     * 2) x.compareTo(y) > 0 , y.compareTo(z) > 0 , x.compareTo(z) > 0 -> 추이성 보장
     * 3) 모든 z에 대해 x.compareTo(y) == 0 이면, x.compareTo(z) == y.compareTo(z)
     * 4) x.compareTo(y) == 0 == x.equals(y) (이 규약은 필수는 아니지만, 꼭 지키는게 좋다. 이 권고를 지키지 않는 모든 클래스는 그 사실을 아래와 같이 명시해야 한다
     *    "주의: 이 클래스의 순서는 equals 메서드와 일관되지 않다.")
     * 
     */

    public static class PhoneNumber implements Comparable<PhoneNumber> {
        private final short areaCode, prefix, lineNum;

        public PhoneNumber(final int areaCode, final int prefix, final int lineNum) {
            this.areaCode = rangeCheck(areaCode, 999, "지역코드");
            this.prefix = rangeCheck(prefix, 999, "프리픽스");
            this.lineNum = rangeCheck(lineNum, 9999, "가입자 번호");
        }

        private static short rangeCheck(final int val, final int max, final String arg) {
            if (val < 0 || val > max) {
                throw new IllegalArgumentException(arg + ": " + val);
            }

            return (short) val;
        }

        @Override public boolean equals(final Object o) {
            if (o == this) {
                return true;
            }

            if (!(o instanceof PhoneNumber)) {
                return false;
            }

            final PhoneNumber pn = (PhoneNumber) o;

            return pn.lineNum == lineNum && pn.prefix == prefix && pn.areaCode == areaCode;
        }

        // [1] Comparable<PhoneNumber> 인터페이스의 구현체
        /* @Override public int compareTo(final PhoneNumber pn) {
            int result = Short.compare(areaCode, pn.areaCode); // 가장 중요한 필드부터 비교
            if (result == 0) {
                result = Short.compare(prefix, pn.prefix); // 두 번째로 중요한 필드
                if (result == 0) {
                    result = Short.compare(lineNum, pn.lineNum); // 세 번째로 중요한 필드
                }
            }
            return result;
        } */

        // [2] 비교자 생성 메서드를 활용한 비교자 (Java 1.8)
        private static final Comparator<PhoneNumber> COMPARATOR = 
            Comparator.comparingInt((PhoneNumber pn) -> pn.areaCode)
                .thenComparingInt(pn -> pn.prefix)
                .thenComparingInt(pn -> pn.lineNum);

        public int compareTo(PhoneNumber pn) {
            // 성능면에서 [1]에 비해 10%정도 느리다
            return COMPARATOR.compare(this, pn);
        }
    }

    public static void main(final String[] args) {
        final BigDecimal bd1 = new BigDecimal("1.0");
        final BigDecimal bd2 = new BigDecimal("1.00");

        System.out.println("db1.equals(bd2)     : " + bd1.equals(bd2));     // db1.equals(bd2)     : false
        System.out.println("db1.compapreTo(bd2) : " + bd1.compareTo(bd2));  // db1.compapreTo(bd2) : 0

        // compareTo와 equals가 다르게 동작하는 대표적인 예 (규약 4번을 지키지 않은 경우)

        final Set<BigDecimal> set1 = new HashSet<BigDecimal>();
        final Set<BigDecimal> set2 = new TreeSet<BigDecimal>();
        
        set1.add(bd1);  set1.add(bd2);
        set2.add(bd1);  set2.add(bd2);

        System.out.println("set1.size() : " + set1.size()); // set1.size() : 2
        System.out.println("set2.size() : " + set2.size()); // set2.size() : 1

        // 내부적으로 HashSet은 equals()로, TreeSet은 compareTo()로 원소를 구분하기 때문에 사이즈가 다르다!
    }
}