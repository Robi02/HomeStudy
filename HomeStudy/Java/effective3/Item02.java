import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

public class Item02 {

    /**
     *  [Item02] 생성자에 매개변수가 많다면 빌더를 고려하라 
     * 
     * 
     *  [장점]
     *  1. 상당히 유연하고 가독성이 좋다.
     *  2. 가변인수 매개변수를 여러 개 사용할 수 있다.
     *  3. 특정 필드는 빌더가 알아서 채우도록 할 수 있다.
     * 
     *  [단점]
     *  1. 객체 생성 전에 빌더부터 생성해야 하므로 성능에 민감한 상황에서는 문제가 될 수 있다.
     *  2. 점층적 생성자 패턴보다는 코드가 장황해서 매개변수가 4개 이상은 되어야 값어치를 한다. 
     * 
     *  [핵심]
     *  생성자나 정적 팩터리가 처리해야 할 매개변수가 많다면 빌더 패턴을 선택하는게 더 낫다.
     *  매개변수 중 다수가 필수가 아니거나 같은 타입이면 특히 더 그렇다.
     *  빌더는 점층적 생성자보다 클라이언트 코드를 읽고 쓰기가 간결하고, 자바빈즈보다 훨씬 안전하다.
     * 
     */

    public static void main(String[] args) {
        // 초기화해주어야 할 필드가 많은 객체를 생성하는 방법
        //
        // 1. 점층적 생성자 패턴
        //  new Item(1, 2, 3);
        //  new Item(1, 2);
        //  new Item(1);
        //   ...
        // -> 매개변수 개수가 많아지면 코드 작성이나 읽기가 어려워진다
        // -> 생성자에 잘못된 값을넣는 실수하기 딱 좋다
        //
        // 2. 자바빈즈 패턴
        //  Item item = new Item();
        //  item.set1(1);
        //  item.set2(2);
        //  item.set3(3);
        //  ...
        // -> 객체가 완전히 생성되기 전까지는 일관성이 무너진 상태에 놓인다
        // -> 클래스를 불변으로 만들 수 없다
        // 
        // 3. 빌더 패턴
        NutritionFacts coke = new NutritionFacts.Builder(240, 8)
            .calories(100).sodium(35).carbohydarte(27).build();
        // -> 파이썬과 스칼라에 있는 명명된 선택적 매개변수(named optional parameters)를 흉내낸 것

        // 사이즈옵션이 있는 뉴욕피자 생성
        Pizza pizza1 = new NyPizza.Builder(NyPizza.Size.LARGE)
            .addTopping(Pizza.Topping.SAUSAGE).build();
        // 소스옵션이 있는 뉴욕피자 생성
        Pizza pizza2 = new Calzone.Builder().sauceInside()
            .addTopping(Pizza.Topping.MUSHROOM).addTopping(Pizza.Topping.PEPPER).build();
        // -> 이처럼 계층적으로 설계된 클래스와 합이 잘 맞는다
    }

    // 빌더패턴 예시 NutritionFacts 클래스
    public static class NutritionFacts {
        private final int servingSize;
        private final int servings;
        private final int calories;
        private final int fat;
        private final int sodium;
        private final int carbohydarte;

        public static class Builder {
            // 필수 매개변수
            private final int servingSize;
            private final int servings;

            // 선택 매개변수 - 기본값으로 초기화
            private int calories = 0;
            private int fat = 0;
            private int sodium = 0;
            private int carbohydarte = 0;

            public Builder(int servingSize, int servings) {
                this.servingSize = servingSize;
                this.servings = servings;
            }

            public Builder calories(int val) {
                this.calories = val;
                return this;
            }
            
            public Builder fat(int val) {
                this.fat = val;
                return this;
            }

            public Builder sodium(int val) {
                this.sodium = val;
                return this;
            }

            public Builder carbohydarte(int val) {
                this.carbohydarte = val;
                return this;
            }

            public NutritionFacts build() {
                return new NutritionFacts(this);
            }
        }

        private NutritionFacts(Builder builder) {
            servingSize = builder.servingSize;
            servings = builder.servings;
            calories = builder.calories;
            fat = builder.fat;
            sodium = builder.sodium;
            carbohydarte = builder.carbohydarte;
        }
    }

    // 점층적인 클래스 구조에 유리한 빌더패턴
    public static abstract class Pizza {
        public enum Topping { HAM, MUSHROOM, ONION, PEPPER, SAUSAGE }
        final Set<Topping> toppings;

        abstract static class Builder<T extends Builder<T>> {
            EnumSet<Topping> toppings = EnumSet.noneOf(Topping.class);
            public T addTopping(Topping topping) {
                toppings.add(Objects.requireNonNull(topping));
                return self();
            }

            abstract Pizza build();

            protected abstract T self();
        }

        private Pizza(Builder<?> builder) {
            toppings = builder.toppings.clone();
        }
    }

    // 뉴욕피자는 사이즈 옵션이 있다
    public static class NyPizza extends Pizza {
        public enum Size { SMALL, MEDIUM, LARGE }
        private final Size size;

        public static class Builder extends Pizza.Builder<Builder> {
            private final Size size;

            public Builder(Size size) {
                this.size = Objects.requireNonNull(size);
            }

            @Override public NyPizza build() {
                return new NyPizza(this);
            }

            @Override protected Builder self() { return this; }
        }

        private NyPizza(Builder builder) {
            super(builder);
            size = builder.size;
        }
    }

    // 칼초네 피자는 소스옵션이 있다
    public static class Calzone extends Pizza {
        private final boolean sauceInside;

        public static class Builder extends Pizza.Builder<Builder> {
            private boolean sauceInside = false; // 기본값

            public Builder sauceInside() {
                this.sauceInside = true;
                return this;
            }

            @Override public Calzone build() {
                return new Calzone(this);
            }

            @Override protected Builder self() { return this; }
        }

        private Calzone(Builder builder) {
            super(builder);
            sauceInside = builder.sauceInside;
        }
    }
}