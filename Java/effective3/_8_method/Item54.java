package _8_method;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Item54 {
    /**
     * [Item54] null이 아닌, 빈 컬렉션이나 배열을 반환하라
     * 
     * [핵심]
     * null이 아닌, 빈 배열이나 컬렉션을 반환하라. null을 반환하는 API는 사용하기 어렵고
     * 오류 처리 코드도 늘어난다. 그렇다고 성능이 좋은 것도 아니다.
     * 
     */

    // 빈 컬렉션을 매번 할당하지 않고, 불변객체를 반환하면 된다!
    public List<String> getListStr() {
        return Collections.emptyList();
    }

    // 길이가 0인 배열은 모두 불변이다!
    private static final String[] EMPTY_STRING_ARRAY = new String[0];
    public String[] getArrayStr() {
        List<String> temp = new ArrayList<>();
        return temp.toArray(EMPTY_STRING_ARRAY);
    }

    // 항상 'size() != 0' 인 컬랙션을 반환하는 메서드가
    // 어쩌다가 null을 반환하는 케이스의 경우
    // 사용자가 null체크를 안해서 불시에 터져버리는 경우가 있다!
}