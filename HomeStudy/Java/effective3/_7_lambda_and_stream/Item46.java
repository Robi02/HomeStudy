package _7_lambda_and_stream;

import static java.util.stream.Collectors.*;

import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Item46 {
    /**
     * [Item46] 스트림에서는 부작용 없는 함수를 사용하라
     * 
     * -> 지금 이 단원을 완벽히 공부하기에는 스트림에 대한 이해가 부족했다.
     *    추후 스트림에 대해 익숙해지면 다시 돌아와서 살펴보자.
     * 
     * [핵심]
     * 스트림 파이프라인 프로그래밍의 핵심은 부작용 없는 함수 객체에 있다.
     * 스트림뿐 아니라 스트림 관련 객체에 건네지는 모든 함수 객체가 부작용이 없어야 한다.
     * 종단 연산 중 forEach는 스트림이 수행한 계산 결과를 보고할 때만 이용해야 한다.
     * 계산 자체에는 이용하지 말자. 스트림을 올바로 사용하려면 수집기를 잘 알아둬야 한다.
     * 가장 중요한 수집기 팩터리는 toList, toSet, toMap, groupingBy, joining이다.
     * 
     */

    public static void main(String[] args) {
        // [1-1] 스트림 패러다임을 이해하지 못한 채 API만 사용한 코드 - 따라 하지 말 것!
        List<String> wordList = new ArrayList<>();
        final Map<String, Long> freq = new HashMap<>();
        try (Stream<String> words = wordList.stream()) {
            words.forEach(word -> {
                freq.merge(word.toLowerCase(), 1L, Long::sum);
                // forEach 연산 내에서는 스트림 계산 결과를 보고할 때만 사용하고
                // 계산하는 데는 쓰지 않는 것이 좋다.
            });
        }

        // [1-2] 스트림을 재대로 활용한 경우
        Map<String, Long> freq2 = new HashMap<>();
        try (Stream<String> words = wordList.stream()) {
            freq2 = words.collect(groupingBy(String::toLowerCase, counting()));
        }


    }
}