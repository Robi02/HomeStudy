package _8_method;

import java.time.Instant;
import java.util.Date;

public class Item50 {
    /**
     * [Item50] 적시에 방어적 복사본을 만들라
     * 
     * [핵심]
     * 클래스가 클라이언트로부터 받은 혹은 클라이언트로 반환하는 구성요소가 가변이라면
     * 그 요소는 반드시 방어적으로 복사해야 한다. 복사 비용이 너무 크거나 클라이언트가
     * 그 요소를 잘못 수정할 일이 없음을 신뢰한다면 방어적 복사를 수행하는 대신
     * 해당 구성요소를 수정했을 대의 책임이 클라이언트에 있음을 문서에 명시하도록 하자.
     * 
     * "항상 클라이언트가 여러분의 불변식을 깨뜨리려 혈안이
     *  되어 있다고 가정하고 방어적으로 프로그래밍 해야 한다."
     * 
     */

     // [1] 불변처럼 보이는 클래스 -> 그러나 불변식을 지키지 못했다
    public static final class Period {
        private final Date start;
        private final Date end;
        
        /**
         * @param start 시작 시각
         * @param end 종료 시각; 시작 시간보다 뒤여야 한다.
         * @throws IllegalArgumentException 시작 시간이 종료 시각보다 늦을 때 발생한다.
         * @throws NullPointerException start나 end가 null이면 발생한다.
         */
        public Period(Date start, Date end) {
            if (start.compareTo(end) > 0) {
                throw new IllegalArgumentException(
                    start + "가 " + end + "보다 늦다.");
            }

            this.start = start;
            this.end = end;
        }

        // [1-2] 불변식을 지켜낸 생성자
        public Period(Date start, Date end, boolean isSafeConstructor) {
            this.start = new Date(start.getTime());
            this.end = new Date(end.getTime());

            if (this.start.compareTo(this.end) > 0) {
                throw new IllegalArgumentException(
                    this.start + "가 " + this.end + "보다 늦다.");
            }

            // 매개변수 검사가 더 뒤에 있는것이 부자연스럽지만,
            // 멀티스레딩 환경을 고려하면 반드시 이렇게 작성해야 한다.
            // 원본을 검사하는 찰나의 순간 불변식이 깨질 수 있기 때문.

            // Date의 clone()을 사용하지 않은 이유는 Date클래스가 final이 아니기 때문.
            // 악의를 가진 하위 클래스의 clone()을 호출할 가능성도 있다.
        }

        // [1-3] 불변식을 지켜낸 getter
        public Date start() {
            return new Date(this.start.getTime());
            // 생성자와는 달리 Period가 가지고 있는 Date객체는
            // java.util.Date 임이 확실하기에, clone() 메서드를
            // 사용하여 반환해도 된다. 그렇지만, Item13에서 설명한
            // (clone 재정의는 주의해서 진행해라) 이유 때문에
            // 일반적으로 생성자나 정적 팩터리를 쓰는게 좋다.
        }

        public Date end() {
            return new Date(this.end.getTime());
        }

        // ...
    }

    public static void main(String[] args) {
        // [1] Period 클래스의 내부를 공격해 보자.
        Date start = new Date();
        Date end = new Date();
        Period p = new Period(start, end);
        end.setYear(78); // p의 내부를 수정했다!

        // Date는 낡은 API로, 새로운 코드를 작성할 때는 더 이상 사용하지 않길 권장한다.
        // 이 코드에서 불변식을 지키려면 Date 대신, Instant혹은 LocalDateTime, ZonedDateTime 사용을 추천한다.

        // 외부 공격으로부터 Period인스턴스의 내부를 보호하려면,
        // 생성자에서 받은 가변 매개변수 각각을 방어적으로 복사(defensive copy)해야 한다.
    }
}