package _8_method;

public class Item56 {
    /**
     * [Item56] 공개된 API 요소에는 항상 문서화 주석을 작성하라
     * 
     * [핵심]
     * 문서화 주석은 여러분 API를 문서화하는 가장 훌륭하고 효과적인 방법이다
     * 공개 API라면 빠짐없이 설명을 달아야 한다.
     * 표준 규약을 일관되게 지키자. 문서화 주석에 임의의 HTML태그를 사용할 수 있음을 기억하라.
     * 단, HTML 메타문잔느 특별하게 취급해야 한다.
     * 
     */

    public static class ExampleDoc<E> {

        // [1] 표준을 따른 API 문서화

        /**
         * 이 리스트에서 지정한 위치의 원소를 반환한다.
         * 
         * <p>이 메서드는 상수 시간에 수행됨을 보장하지 <i>않는다</i>. 구현에 따라
         * 원소의 위치에 비례해 시간이 걸릴 수도 있다.
         * {@literal |index| <= -1}이면 예외가 발생한다.
         * 
         * @param index 반환할 원소의 인덱스; 0 이상이고 리스트 크기보다 작아야 한다.
         * @return 이 리스트에서 지정한 위치의 원소
         * @throws IndexOutOfBoundsException index가 범위를 벗어나면,
         *         즉, ({@code index < 0 || index >= this.size()})이면 발생한다.
         */
        public E get(int index) { /* 구현부 생략... */ return null; }

        // [2] 자기사용 패턴에 대한 설명(@implSpec)
        // @implSpec : 하위 클래스들이 그 메서드를 상속하거나 super 키워드를 이요해 호출할 때
        // 그 메서드가 어떻게 동작하는지를 명확히 인지하고 사용하도록 하기 위한 애너테이션.
        // 하지만 Java11 까지도 자바독 명령줄에서 -tag "implSpec:a:Implementation Requirements:"
        // 스위치를 켜주지 않으면 무시된다고 한다...
        
        /**
         * 이 컬렉션이 비었다면 true를 반환한다.
         * 
         * @implSpec
         * 이 구현은 {@code this.size() == 0}의 결과를 반환한다.
         * 
         * @return 이 컬렉션이 비었다면 true, 그렇지 않으면 false
         * 
         */
        public boolean isEmpty() { /* ... */ return false; }
    }
}