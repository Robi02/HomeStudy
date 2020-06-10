import java.util.HashMap;
import java.util.Map;

public class Item43 {
    /**
     * [Item43] 람다보다는 메서드 참조를 사용하라
     * 
     * [핵심]
     * 메서드 참조는 람도의 간단명료한 대안이 될 수 있다.
     * 메서드 참조 쪽이 짧고 명확하다면 메서드 참조를 쓰고,
     * 그렇지 않을 때만 람다를 사용하라.
     * 
     */

    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();

        // [1] 똑같은 효과의 4가지 접근법.
        // 어떤 방법이 가장 간결하고 효율적으로 보이는가?

        // [1-1] Legacy Java
        Integer i = map.get("key");
        if (i == null)
            map.put("key", 1);
        else
            map.put("key", i += 1);

        // [1-2] Java8
        map.clear();
        i = map.getOrDefault("key", 1);
        if (i > 1)
            map.put("key", i += 1);

        // [1-3] 람다
        map.clear();
        map.merge("key", 1, (count, incr) -> count + incr);

        // [1-4] 메서드 참조
        map.clear();
        map.merge("key", 1, Integer::sum);
    }
}