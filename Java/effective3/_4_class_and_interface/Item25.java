package _4_class_and_interface;

public class Item25 {
    /**
     * [Item25] 톱레벨 클래스는 한 파일에 하나만 담으라
     * 
     * [핵심]
     * 교훈은 명확하다. 소스 파일 하나에는 반드시 톱레벨 클래스(혹은 톱레벨 인터페이스)를 하나만 담자.
     * 이 규칙만 따른다면 컴파일러가 한 클래스에 대한 정의를 여러 개 만들어 내는 일은 사라진다.
     * 소스 파일을 어떤 순서로 컴파일하든 바이너리 파일이나 프로그램의 동작이 달라지는 일은 결코 일어나지 않을 것이다.
     * 
     */

    public static void main(String[] args) {
        System.out.println(Item25_A.NAME + "/" + Item25_B.NAME);
    }
}

// 이런 위험한 클래스 선언은 하지 말자...
// 컴파일 순서에 따라 컴파일 에러(차라리 다행이다) 혹은 잡아내기 힘든 동작이 발생할 수 있다.
class Item25_A {
    static final String NAME = "ITEM25_A";
}

class Item25_B {
    static final String NAME = "ITEM25_B";
}