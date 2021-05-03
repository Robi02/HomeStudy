package _5_generic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

public class Item33 {
    /**
     * [Item33] 타입 안전 이종 컨테이너를 고려하라
     * 
     * [핵심]
     * 컬렉션 API로 대표되는 일반적인 제네릭 형태에서는 한 컨테이너가 다룰 수 있는 타입
     * 매개변수의 수가 고정되어 있다. 하지만 컨테이너 자체가 아닌 키를 타입 매개변수로
     * 바꾸면 이런 제약이 없는 타입 안전 이종 컨테이너를 만들 수 있다.
     * 타입 안전 이종 컨테이너는 Class를 키로 쓰며, 이런 시으로 쓰이는 Class객체를 타입 토큰이라 한다.
     * 또한, 직접 구현한 키 타입도 쓸 수 있다. 예컨대 데이터베이스의 행(컴테이너)을 표현한
     * DatabaseRow 타입에는 제네릭 타입은 Column<T>를 키로 사용할 수 있다.
     * 
     */

    // [1] 타입 안전 이종 컨테이터 패턴
    public static class Favorites {
        private Map<Class<?>, Object> favorites = new HashMap<>();
        
        public <T> void putFavorite(Class<T> type, T instance) {
            favorites.put(Objects.requireNonNull(type), type.cast(instance));
        }

        public <T> T getFavorite(Class<T> type) {
            return type.cast(favorites.get(type));
        }
    }

    public static void main(String[] args) {
        // [1]
        Favorites f = new Favorites();

        f.putFavorite(String.class, "Java");
        f.putFavorite(Integer.class, 0xcafebabe);
        f.putFavorite(Class.class, Favorites.class);

        // [1:단점]
        // 1) 악의적인 사용으로 타입 안정성을 깨트릴 수 있다
        // ClassCastExeception이 발생할 무지막지한 코드를 넣을 수는 있다.
        // 하지만, 컴파일러 비검사 경고가 발생한다! (아래 노란 줄이 보이는가?)
        f.putFavorite((Class) Integer.class, "Error?");

        // 이 코드랑 다를바가 없다!
        HashSet<Integer> set = new HashSet<>();
        ((HashSet) set).add("문자열이지롱!"); // (raw 타입으로 캐스팅)

        // 2) 실체화 불가 타입(Item28)에는 사용할 수 없다.
        // f.putFavorite(List<String>.class, Arrays.asList(new String[] {"A", "B", "C"})); // 컴파일 에러

        // -> 슈퍼 타입 토큰을 사용하면 제한적인 우회로를 만들 수는 있다고 한다. (http://bit.ly/2NGQi2S)
        

        String favoriteString = f.getFavorite(String.class);
        int favoriteInteger = f.getFavorite(Integer.class);
        Class<?> favoriteClass = f.getFavorite(Class.class);

        System.out.printf("%s %x %s%n", favoriteString,
            favoriteInteger, favoriteClass.getName());
    }

}