package _12_serialize;

public class Item86 {
    /**
     * [Item86] Serializable을 구현할지는 신중히 결정하라
     * 
     * [핵심]
     * Serializable은 구현한다고 선언하기는 아주 쉽지만, 그것은 눈속임일 뿐이다.
     * 한 클래스의 여러 버전이 상호작용할 일이 없고 서버가 신뢰할 수 없는 데이터에
     * 노출될 가능성이 없는 등, 보호된 환경에서만 쓰일 클래스가 아니라면 Serializable
     * 구현은 아주 신중하게 이뤄져야 한다. 상속할 수 있는 클래스라면 주의사항이 더욱 많아진다.
     * 
     * + 대부분 인터페이스/상속용 클래스는 Serializable을 확장/구현하면 안 된다.
     * + 내부 클래스는 직렬화를 구현하지 말아야 한다. (정적 멤버 클래스는 괜찮다)
     * 
     * [이유]
     * 1. Serializable을 구현하면 릴리스한 뒤에는 수정하기 어렵다.
     * 2. 버그와 보안 구명이 생길 위험이 높아진다.
     * 3. 해당 클래스의 신버전을 릴리스할 때 테스트할 것이 늘어난다.
     * 
     */
}