package _2_object_ctor_dtor;

public class Item01 {

    /**
     *  [Item01] 생성자 대신 정적 팩터리 메서드(static factory method)를 고려하라
     * 
     *  [장점]
     *  1. 이름을 가질 수 있다.
     *  2. 호출될 때마다 인스턴스를 새로 생성하지는 않아도 된다.
     *  3. 반환 타입의 하위 타입 객체를 반환할 수 있는 능력이 있다.
     *  4. 입력 매개변수에 따라 매번 다른 클래스의 객체를 반환할 수 있다.
     *  5. 정적 펙터리 메서드를 작성하는 시점에는 반환할 객체의 클래스가 존재하지 않아도 된다.
     * 
     *  [단점]
     *  1. 상속을 하려면 public이나 protected 생성자가 필요하니 정적 팩터리 메서드만 제공하면 하위 클래스를 만들 수 없다.
     *  2. 정적 팩터리 메서드는 프로그래머가 찾기 어렵다.
     * 
     *  [핵심]
     *  정적 팩터리 메서드와 public 생성자는 각자의 쓰임새가 있으니 상대적인 장단점을 이해하고 사용하는것이 좋다.
     *  그렇다고 하더라도 정적 팩터리를 사용하는게 유리한 경우가 더 많으므로 무작정 public 생성자를 제공하던 습관이 있다면 고지차.
     * 
     */

    public static void main(String[] args) {

        // 정적 팩터리 메서드에 흔히 사용하는 명명 방식들
        // from()           :: Date d = Date.from(instant);
        // of()             :: Set<Rank> faceCards = EnumSet.of(JACK, QUEEN, KING);
        // valueOf()        :: BigInteger prime = BigInteger.valueOf(Integer.MAX_VALUE);
        // getInstance()    :: StackWalker luke = StackWalker.getInstance(options);
        // getType()        :: FileStore fs = Files.getFileStore(path);
        // newType()        :: BufferedReader br = Files.newBufferedReader(path);
        // type()           :: List<Complaint> litany = Collections.list(legacyLitany);

    }
}