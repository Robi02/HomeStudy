import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Item37 {
    /**
     * [Item37] ordinal 인덱싱 대신 EnumMap을 사용하라
     * 
     * [핵심]
     * 배열의 인덱스를 얻기 위해 ordinal을 쓰는 것은 일반적으로 좋지 않으니,
     * 대신 EnumMap을 사용하라. 다차원 관계는 EnumMap<..., EnumMap<...>>으로 표현하라.
     * "애플리케이션 프로그래머는 Enum.ordinal을 (웬만해서는) 사용하지 말아야 한다. (Item35)"
     * 는 일반 원칙의 특수한 사례다.
     * 
     */

    public static class Plant {
        enum LifeCycle { ANNUAL, PERENNIAL, BIENNIAL }

        final String name;
        final LifeCycle lifeCycle;

        Plant(String name, LifeCycle lifeCycle) {
            this.name = name;
            this.lifeCycle = lifeCycle;
        }

        @Override public String toString() {
            return this.name;
        }
    }

    public static void main(String[] args) {
        // [1] oridinal()을 배열 인덱스로 사용 - 따라 하지 말 것!
        List<Plant> garden = new ArrayList<>();

        Set<Plant>[] plantsByLifeCycle = (Set<Plant>[]) new Set[Plant.LifeCycle.values().length]; // 제네릭 + 배열 (위험!)
        for (int i = 0; i < plantsByLifeCycle.length; ++i) {
            plantsByLifeCycle[i] = new HashSet<>();
        }

        for (Plant p : garden) {
            plantsByLifeCycle[p.lifeCycle.ordinal()].add(p);
        }

        // garden안의 식물을 LifeCycle을 기준으로 분류하려는 이 동작은
        // 운이 없다면 잘못된 동작을 묵묵히 수행할 것이고,
        // 운이 좋다면 ArrayIndexOutOfBoundsException이 던져질 것이다.

        // [2] EnumMap을 사용해 데이터와 열거 타입을 매핑
        Map<Plant.LifeCycle, Set<Plant>> plantsByLifeCycle2 = new EnumMap<>(Plant.LifeCycle.class);
        for (Plant.LifeCycle lc : Plant.LifeCycle.values()) {
            plantsByLifeCycle2.put(lc, new HashSet<>());
        }

        for (Plant p : garden) {
            plantsByLifeCycle2.get(p.lifeCycle).add(p);
        }

        System.out.println(plantsByLifeCycle2);
    }

    // [3] 이중배열의 인덱스들에 ordinal()을 사용 - 따라 하지 말 것!
    public static enum Phase {
        SOLID, LIQUID, GAS; // [4-2] PLASMA; // 플라즈마 상태가 추가된다면...?
    
        public static enum Transition {
            MELT, FREEZE, BOIL, CONDENSE, SUBLIME, DEPOSIT;

            // 행은 from의 ordinal을, 열은 to의 ordinal을 인덱스로 쓴다.
            private static final Transition[][] TRANSITIONS = {
                { null, MELT, SUBLIME },
                { FREEZE, null, BOIL },
                { DEPOSIT, CONDENSE, null }
            };

            // 한 상태에서 다른 상태로의 전이를 반환한다
            public static Transition from(Phase from, Phase to) {
                return TRANSITIONS[from.ordinal()][to.ordinal()];
            }

            // 멋져보이는가? 지금 당장은 그럴 지 몰라도,
            // 값이 추가되거나 삭제되거나 하는 경우 TRANSITIONS을
            // 재정렬하지 않으면 지옥을 맛보게 될 것이다.
            // EnumMap을 사용하는것이 훨씬 낫다.
        }

        // [4] 중첩 EnumMap으로 데이터와 열거 타입 쌍을 연결하여 개선
        public static enum TransitionEx {
            MELT(SOLID, LIQUID), FREEZE(LIQUID, SOLID),
            BOIL(LIQUID, GAS), CONDENSE(GAS, LIQUID),
            SUBLIME(SOLID, GAS), DEPOSIT(GAS, SOLID);
            // IONIZE(GAS, PLASMA), DEIONIZE(PLASMA, GAS); // [4-2] 단 두줄의 코드만 추가되면 된다!
            
            private final Phase from;
            private final Phase to;

            TransitionEx(Phase from, Phase to) {
                this.from = from;
                this.to = to;
            }

            // 상전이 맵을 초기화한다
            private static final Map<Phase, Map<Phase, TransitionEx>>
                m = new EnumMap<>(Phase.class);
            
            static {
                for (Phase p : Phase.values()) {
                    m.put(p, new EnumMap<>(Phase.class));
                }

                for (TransitionEx t : TransitionEx.values()) {
                    m.get(t.from).put(t.to, t);
                }
            }

            public static TransitionEx from(Phase from, Phase to) {
                return m.get(from).get(to);
            }
        }
    }
}