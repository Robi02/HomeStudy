public class Item27 {
    /**
     * [Item27] 비검사 경고를 제거하라
     * 
     * [핵심]
     * 비검사 경고는 중요하니 무시하지 말자. 모든 비검사 경고는 런타임에 ClassCastException
     * 을 일으킬 수 있는 잠재적 가능성을 뜻하니 최선을 다해 제거하라.
     * 경고를 없앨 방법을 찾지 못하겠다면, 그 코드가 타입 안전함을 증명하고 가능한 한 범위를 좁혀
     * @SuppressWarnings("unchecked") 애너테이션으로 경고를 숨겨라.
     * 그런 다음 경고를 숨기기로 한 근거를 주석으로 남겨라.
     * 
     */

    /* ArrayList의 toArray() 메서드를 살펴보자...
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            // 생성한 배열과 매개변수로 받은 배열의 타입이 모두 T[]로 같으므로 올바른 형변환이다.
            @SuppressWarnings("unchecked") T[] result =
                (T[]) Arrays.copyOf(elements, size, a.getClass());
            return result;

            // return 문에는 @SuppressWarnings를 다는게 불가능하다.
            // 해당 어노테이션은 "선언"문에만 달 수 있으므로 메서드의 선언문이나 클래스의
            // 선언문에 넣을 수도 있지만, 범위를 "최소화" 하는게 좋으므로
            // result 라는 지역변수를 선언하여 어노테이션을 단 것을 확인할 수 있다.
            // 또한, 그 경고를 무시해도 안전한 이유를 항상 주석으로 달아주어야 한다.
        }

        System.arraycopy(elements, 0, a, 0, size);
        if (a.length > size) 
            a[size] = null;
        return a;
    }
    */
}