package _12_serialize;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Date;

public class Item90 {
    /**
     * [Item90] 직렬화된 인스턴스 대신 직렬화 프록시 사용을 검토하라
     * 
     * [핵심]
     * 제 3자가 호가장할 수 없는 클래스라면 가능한 한 직렬화 프록시 패턴을 사용하자.
     * 이 패턴이 아마도 중요한 불변식을 안정적으로 직렬화해주는 가장 쉬운 방법일 것이다.
     * (단, 방어적 복사 때보다 14%정도 느린 성능을 보일 수 있다.)
     * 
     * [한계]
     * 1. 클라이언트가 멋대로 확장할 수 있는 클래스에는 적용할 수 없다.
     * 2. 객체 그래프에 순환이 있는 클래스에 적용할 수 없다.
     * 
     */

    // Item50의 Period클래스
    public static final class Period {
        private final Date start;
        private final Date end;

        public Period(Date start, Date end) {
            this.start = new Date(start.getTime());
            this.end = new Date(end.getTime());

            if (this.start.compareTo(this.end) > 0) {
                throw new IllegalArgumentException(
                    this.start + "가 " + this.end + "보다 늦다.");
            }
        }

        public Date start() {
            return new Date(this.start.getTime());
        }

        public Date end() {
            return new Date(this.end.getTime());
        }

        // 추가됨: 직렬화 프록시 패턴용 writeReplace 메서드
        private Object writeReplace() {
            return new SerializationProxy(this);
        }

        // 추가됨: 직렬화 프록시 패턴용 readObject 메서드
        private void readObject(ObjectInputStream stream) throws InvalidObjectException {
            // 불변식 훼손 시도를 가볍게 막아낼 수 있다
            throw new InvalidObjectException("프록시가 필요합니다.");
        }
    }

    public static class SerializationProxy implements Serializable {
        private final Date start;
        private final Date end;

        SerializationProxy(Period p) {
            this.start = p.start;
            this.end = p.end;
        }

        // Period.SerializationProxy용 readResolve 메서드
        private Object readResolve() {
            return new Period(start, end);
        }

        private static final long serialVersionUID = 2340982438234858285L;
    }
}