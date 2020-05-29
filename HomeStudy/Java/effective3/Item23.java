public class Item23 {
    /**
     * [Item23] 태그 달린 클래스보다는 클래스 계층구조를 활용하라
     * 
     * [핵심]
     * 태그 달린 클래스를 써야 하는 상황은 거의 없다.
     * 새로운 클래스를 작성하는 데 태그 필드가 등장한다면 태그를 없애고
     * 계층구조로 대체하는 방법을 생각해보자.
     * 기존 클래스가 태그 필드를 사용하고 있다면 계층구조로 리팩터링하는 걸 고민해보자.
     * 
     */
    
    // [1] 태그 달린 클래스 예시 - 클래스 계층구조보다 훨씬 나쁘다!
    // -> 장황하고, 오류를 내기 쉽고, 비효율적이다!
    public static class Figure {
        enum Shape { RECTANGLE, CIRCLE };

        // 태그 필드 - 현재 모양을 나타낸다.
        final Shape shape;

        // 다음 필드는 모양이 사각형일때만 쓰인다.
        double length;
        double width;

        // 다음 필드는 모양이 원일때만 쓰인다.
        double radius;

        // 원용 생성자
        public Figure(double radius) {
            shape = Shape.CIRCLE;
            this.radius = radius;
        }

        // 사각형용 생성자
        Figure(double length, double width) {
            shape = Shape.RECTANGLE;
            this.length = length;
            this.width = width;
        }

        double area() {
            switch (shape) {
                case RECTANGLE:
                    return length * width;
                case CIRCLE:
                    return Math.PI * (radius * radius);
                default:
                    throw new AssertionError(shape);
            }
        }
    }

    // [2] 태그 달린 클래스를 클래스 계층구조로 변환
    // -> 간결하고 명확하며, 쓸데없는 코드도 모두 사라졌다.
    abstract static class FigureEx {
        
        // 계층구조의 루트가 될 추상 클래스(FigureEx)를 정의하고, 태그 값에 따라
        // 동작이 달라지는 메서드들을 루트 클래스의 추상 메서드(area())로 선언한다.
        // 그 다음, 태그 값에 상관없이 동작이 일정한 메서드들을 루트 클래스에 일반 메서드로 추가한다.
        // 모든 하위 클래스에서 공통으로 사용하는 데이터 필드들도 전부 루트 클래스로 올린다.

        abstract double area();
    }

    static class Circle extends FigureEx {
        final double radius;

        Circle(double radius) { this.radius = radius; }

        @Override double area() { return Math.PI * (radius * radius); }
    }

    static class Rectangle extends FigureEx {
        final double length;
        final double width;

        Rectangle(double length, double width) {
            this.length = length;
            this.width = width;
        }

        @Override double area() { return length * width; }
    }
}