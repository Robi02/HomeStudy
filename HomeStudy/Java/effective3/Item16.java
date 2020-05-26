public class Item16 {
    /**
     * [Item16] public 클래스에서는 public 필드가 아닌 접근자 메서드를 사용하라
     * 
     * [핵심]
     * public 클래스는 절대 가변 필드를 직접 노출해서는 안 된다.
     * 불변 필드라면 노출해도 덜 위험핮만 완전히 안심할 수는 없다.
     * 하지만 package-private 클래스나 private 중첩 틀래스에서는 종종
     * (불변이든 가변이든) 필드를 노출하는 편이 나을 때도 있다.
     * 
     */
    
    public static void main(String[] args) {
        java.awt.Dimension d = new java.awt.Dimension(100, 200);
        int h = d.height;
        int w = d.width;

        // java.awt.Dimension 클래스는 Item16을 지키지 않은 아주 좋은 예시이다.
        // 내부를 노출한 Dimenshion 클래스의 심각한 성능 문제는
        // 오늘날까지도 해결되지 못했다.
        // 개선과 동시에 하위버전 호환을 보장할 수 없기 때문이다.
    }

    public final class Time {
        // private
        private static final int HOURS_PER_DAY = 24;
        private static final int MINUTES_PER_HOUR = 60;

        // public
        public final int hour;      // public final(불변) 필드의 노출은 고려해볼 사항이다.
        public final int minute;

        public Time(int hour, int minute) {
            if (hour < 0 || hour >= HOURS_PER_DAY) {
                throw new IllegalArgumentException("시간: " + hour);
            }
            if (minute < 0 || minute >= MINUTES_PER_HOUR) {
                throw new IllegalArgumentException("분: " + minute);
            }
            this.hour = hour;
            this.minute = minute;
        }

        // ...
    }
}