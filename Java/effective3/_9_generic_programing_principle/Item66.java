package _9_generic_programing_principle;

public class Item66 {
    /**
     * [Item66] 네이티브 메서드(JNI)는 신중히 사용하라
     * 
     * [핵심]
     * 네이티브 메서드를 사용하려거든 한번 더 생각하라. 네이티브 메서드가 성능을
     * 개선해 주는 일은 많지 않다. 저수준 자원이나 네이티브 라이브러리를 사용해야만 해서
     * 어쩔 수 없더라도 네이티브 코드는 최소한만 사용하고 철저히 테스트하라.
     * 네이티브 코드 안에 숨은 단 하나의 버그가 여러분 애플리케이션 전체를 훼손할 수도 있다.
     * 
     * [전통적인 네이티브 메서드(C/C++등으로 작성된 코드)의 주요 쓰임]
     * 1. 레지스트리 같은 플랫폼 특화 기능 사용.
     * 2. 네이티브 코드로 작성된 기존 라이브러리를 사용.
     * 3. 성능 개선을 목적으로 성능에 결정적인 영향을 주는 영약만 따로 네이티브 언어로 작성.
     *    -> Java3 전이라면 몰라도 최신의 JVM에서는 거의 권장되지 않는다.
     * 
     */
}