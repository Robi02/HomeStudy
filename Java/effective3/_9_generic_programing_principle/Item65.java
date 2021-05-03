package _9_generic_programing_principle;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Set;

public class Item65 {
    /**
     * [Item65] 리플렉션보다는 인터페이스를 사용하라
     * 
     * [핵심]
     * 리플렉션은 복잡한 특수 시스템을 개발할 대 필요한 강력한 기능이지만, 단점도 많다.
     * 컴파일 타임에는 알 수 없는 클래스를 사용하는 프로그램을 작성한다면 리픅렉션을 사용해야 할 것이다.
     * 단, 되도록 객체 생성에만 사용하고, 생성한 객체를 이용할 때는 적절한 인터페이스나
     * 컴파일타임에 알 수 있는 상위 클래스로 형변환해 사용해야 한다.
     * 
     * 
     * [리플랙션의 단점]
     * 1. 컴파일타임 타입/예외 검사가 주는 이점을 하나도 누릴 수 없다.
     * 2. 리플렉션을 이용하면 코드가 지저분하고 장황해진다.
     * 3. 성능이 떨어진다. (매개변수가 없어도 11배 정도?)
     * 
     * -> 리플렉션은 아주 제한된 형태로만 사용해야 그 단점을 피하고 이점만 취할 수 있다.
     * 
     */

    // 리플렉션으로 생성하고 인터페이스로 참조해 활용
    public static void main(String[] args) {
        // 클래스 이름을 Class 객체로 변환
        Class<? extends Set<String>> cl = null;
        try {
            cl = (Class<? extends Set<String>>) Class.forName(args[0]); // 비검사 형변환!
        }
        catch (ClassNotFoundException e) {
            System.out.println("'" + args[0] + "' 클래스를 찾을 수 없습니다.");
            return;
        }

        // 생성자를 얻는다
        Constructor<? extends Set<String>> cons = null;
        try {
            cons = cl.getDeclaredConstructor();
        }
        catch (NoSuchMethodException e) {
            System.out.println("매개변수 없는 생성자를 찾을 수 없습니다.");
            return;
        }

        // 집합의 인스턴스 생성
        Set<String> s = null;
        try {
            s = cons.newInstance();
        }
        catch (IllegalAccessException e) {
            System.out.println("생성자에 접근할 수 없습니다.");
            return;
        }
        catch (InstantiationException e) {
            System.out.println("클래스를 인스턴스화할 수 없습니다.");
            return;
        }
        catch (InvocationTargetException e) {
            System.out.println("생성자가 예외를 던졌습니다: " + e.getCause());
            return;
        }
        catch (ClassCastException e) {
            System.out.println("Set을 구현하지 않은 클래스입니다.");
            return;
        }

        // 생성한 집합을 사용한다
        s.addAll(Arrays.asList(args).subList(1, args.length));

        // 위 코드로 보는 리플렉션의 단점!
        // 1. 런타임에 6개의 예외를 던졌는데, 리플렉션을 안 썼다면 컴파일타임에 잡아낼 수 있을 예외들이다.
        //    (상위 예외인 ReflectiveOperationException을(Java7) 사용하면 1개로 줄일 수 있긴 하다.)
        // 2. 클래스 이름만으로 인스턴스를 생성해내기 위해 무려 25줄이나 되는 코드를 작성했다.
    }
}