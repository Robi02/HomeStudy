import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Item39 {
    /**
     * [Item39] 명명 패턴보다 애너테이션을 사용하라
     * 
     * [핵심]
     * 애너테이션으로 할 수 있는 일을 명명 패턴으로 처리할 이유는 없다.
     * (public void testSomeApi() -> @Test public void someApi())
     * 자바 프로그래머라면 예외 없이 자바가 제공하는 애너테이션 타입들은 사용해야 한다. (Item27,40)
     * (ex: @SupressWarning...)
     * 
     */

    // [1] 마커 애너테이션을 사용한 프로그램 예
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public static @interface Test { /* 테스트 메서드임을 선언하는 애너테이션. 매개변수 없는 정적 메서드 전용. */}

    // [2] 배열 매개변수를 받는 애너테이션 타입
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public static @interface ExceptionTest {
        // 명시한 예외를 던져야만 성공하는 테스트 메서드용 애너테이션.
        Class<? extends Throwable>[] value();
    }

    // [3] 반복 가능한 애너테이션 타입
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @Repeatable(ExceptionTest2Container.class)
    public static @interface ExceptionTest2 {
        Class<? extends Throwable> value();
    }

    // 컨테이너 애너테이션
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public static @interface ExceptionTest2Container {
        ExceptionTest2[] value();
    }

    // [1]
    public static class Sample {
        @Test public static void m1() {} // 성공해야 한다
        public static void m2() {}
        @Test public static void m3() { throw new RuntimeException("Fail"); } // 실패해야 한다
        public static void m4() {}
        @Test public void m5() {} // 잘못 사용한 예: 정적 메서드가 아님
        public static void m6() {}
        @Test public static void m7() { throw new RuntimeException("Fail"); } // 실패해야 한다
        public static void m8() {}
    }

    // [2]
    public static class Sample2 {
        @ExceptionTest(ArithmeticException.class)
        public static void mm1() { // 성공해야 한다
            int i = 0;
            i = i / i;
        }
        @ExceptionTest(ArithmeticException.class)
        public static void mm2() { // 실패해야 한다 (다른 예외 발생)
            int[] a = new int[0];
            int i = a[1];
        }
        @ExceptionTest(value=ArithmeticException.class)
        public static void mm3() {} // 실패해야 한다 (예외가 발생하지 않음)

        @ExceptionTest({ IndexOutOfBoundsException.class, NullPointerException.class} )
        public static void mm4() { // 성공해야 한다
            // 자바 API명세에 따르면 다음 메서드는
            // IndexOutOfBoundsException이나 NullPointerException을 던질 수 있다.
            List<String> list = new ArrayList<>();
            list.addAll(5, null);
        }
    }

    // [3]
    public static class Sample3 {
        @ExceptionTest2(IndexOutOfBoundsException.class)
        @ExceptionTest2(NullPointerException.class)
        public static void mmm1() { // 성공해야 한다
            List<String> list = new ArrayList<>();
            list.addAll(5, null);
        }
    }

    public static class RunTests {
        // [1]
        public static void test(String[] args) throws Exception {
            int tests = 0;
            int passed = 0;
            Class<?> testClass = Class.forName(args[0]);
            for (Method m : testClass.getDeclaredMethods()) {
                if (m.isAnnotationPresent(Test.class)) { // @Test
                    ++tests;
                    try {
                        m.invoke(null);
                        ++passed;
                    }
                    catch (InvocationTargetException wrappedExc) {
                        Throwable exc = wrappedExc.getCause();
                        System.out.println(m + " 실패: " + exc);
                    }
                    catch (Exception exc) {
                        System.out.println("잘못 사용한 @Test: " + m);
                    }
                }
            }

            System.out.printf("성공: %d, 실패: %d%n", passed, tests - passed);
        }

        // [2]
        public static void test2(String[] args) throws Exception {
            int tests = 0;
            int passed = 0;
            Class<?> testClass = Class.forName(args[0]);
            for (Method m : testClass.getDeclaredMethods()) {
                if (m.isAnnotationPresent(ExceptionTest.class)) { // @ExceptionTest
                    ++tests;
                    try {
                        m.invoke(null);
                        System.out.printf("테스트 %s 실패: 예외를 던지지 않음%n", m);
                    }
                    catch (InvocationTargetException wrappedExc) {
                        Throwable exc = wrappedExc.getCause();
                        int oldPassed = passed;
                        Class<? extends Throwable>[] excTypes =
                            m.getAnnotation(ExceptionTest.class).value();
                        for (Class<? extends Throwable> excType : excTypes) {
                            if (excType.isInstance(exc)) {
                                ++passed;
                                break;
                            }
                        }
                        if (passed == oldPassed) {
                            System.out.printf("테스트 %s 실패: %s %n", m, exc);
                        }
                    }
                    catch (Exception exc) {
                        System.out.println("잘못 사용한 @ExceptionTest: " + m);
                    }
                }
            }

            System.out.printf("성공: %d, 실패: %d%n", passed, tests - passed);
        }

        // [3]
        public static void test3(String[] args) throws Exception {
            int tests = 0;
            int passed = 0;
            Class<?> testClass = Class.forName(args[0]);
            for (Method m : testClass.getDeclaredMethods()) {
                if (m.isAnnotationPresent(ExceptionTest2.class) || // @ExceptionTest2 or @ExceptionTest2Container
                    m.isAnnotationPresent(ExceptionTest2Container.class)) {
                    ++tests;
                    try {
                        m.invoke(null);
                        System.out.printf("테스트 %s 실패: 예외를 던지지 않음%n", m);
                    }
                    catch (InvocationTargetException wrappedExc) {
                        Throwable exc = wrappedExc.getCause();
                        int oldPassed = passed;
                        ExceptionTest2[] excTests = m.getAnnotationsByType(ExceptionTest2.class);
                        for (ExceptionTest2 excType : excTests) {
                            if (excType.value().isInstance(exc)) {
                                ++passed;
                                break;
                            }
                        }
                        if (passed == oldPassed) {
                            System.out.printf("테스트 %s 실패: %s %n", m, exc);
                        }
                    }
                    catch (Exception exc) {
                        System.out.println("잘못 사용한 @ExceptionTest: " + m);
                    }
                }
            }

            System.out.printf("성공: %d, 실패: %d%n", passed, tests - passed);
        }
    }

    public static void main(String[] args) {
        try {
            // [1] 마커 애너테이션 테스트
            Item39.RunTests.test(new String[] { Item39.Sample.class.getName() });

            // [2] 배열 매개변수 애너테이션 테스트
            Item39.RunTests.test2(new String[] { Item39.Sample2.class.getName() });

            // [3] 반복 가능한 애너테이션 테스트
            Item39.RunTests.test3(new String[] { Item39.Sample3.class.getName() });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}