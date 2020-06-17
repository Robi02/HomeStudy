package _7_lambda_and_stream;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Item45 {
    /**
     * [Item45] 스트림은 주의해서 사용하라
     * 
     * [핵심]
     * 스트림을 사용해야 멋지게 처리할 수 있는 일이 있고, 반복 방식이 더 알맞은 일도 있다.
     * 그리고 수많은 작업이 이 둘을 조합했을 때 가장 멋지게 해결된다.
     * 어느 쪽을 선택하는 확고부동한 규칙은 없지만 참고할 만한 지침 정도는 있다.
     * "스트림과 반복 중 어느 쪽이 더 나은지 확신하기 어렵담녀 둘 다 해보고 더 나은 쪽을 택하라."
     * 나 뿐만 아니라, 동료들도 스트림 코드를 이해할 수 있고 선호한다면 스트림 방식을 사용해도 될 것이다.
     * 
     * [코드 블록과 스트림에 맞는 연산]
     * 1) 코드 블록
     *  (1) 범위 안의 지역변수를 수정할 수 있다 <-> 스트림 람다는 final인 값만 읽을 수 있다
     *  (2) return, continue, break 사용이 가능하다 <-> 그런거 없다
     * 
     * 2) 스트림
     *  (1) 원소의 시퀀스를 일관되게 변환한다.
     *  (2) 원소들의 시퀀스를 필터링한다.
     *  (3) 원소들의 시퀀스를 하나의 연산을 사용해 결합한다.
     *  (4) 원소들의 시퀀스를 컬렉션에 모은다.
     *  (5) 원소들의 시퀀스에서 특정 조건을 만족하는 원소를 찾는다.
     * 
     */

    
    public static class Anagrams {
        public static void main(String[] args) {
            // [1] 사전 하나를 훑어 원소 수가 많은 아나그램 그룹들을 출력
            {
                File dictionary = new File(args[0]);
                int minGroupSize = Integer.parseInt(args[1]);

                Map<String, Set<String>> groups = new HashMap<>();
                try (Scanner s = new Scanner(dictionary)) {
                    while (s.hasNext()) {
                        String word = s.next();
                        groups.computeIfAbsent(alphabetize(word),
                            (unused) -> new TreeSet<>()).add(word);

                        // computeIfAbsent() 맵에 키가 있는지 확인한 후, 있다면 그 키에 매핑된 값을 반환.
                        // 키가 없으면 건네진 함수 객체를 키에 적용하여 값을 계산 후 키와 값을 매핑한후 계산된 값 반환.
                    }

                    for (Set<String> group : groups.values()) {
                        if (group.size() >= minGroupSize) {
                            System.out.println(group.size() + ": " + group);
                        }
                    }
                }
                catch (IOException e) {}
            }

            // [2] 스트림을 과하게 사용한 경우 - 따라 하지 말 것!
            {
                Path dictionary = Paths.get(args[0]);
                int minGroupSize = Integer.parseInt(args[1]);

                try (Stream<String> words = Files.lines(dictionary)) {
                    words.collect(
                        Collectors.groupingBy(word -> word.chars().sorted()
                            .collect(StringBuilder::new, (sb, c) -> sb.append((char) c),
                                StringBuilder::append).toString())
                    )
                    .values().stream()
                    .filter(group -> group.size() >= minGroupSize)
                    .map(group -> group.size() + ": " + group)
                    .forEach(System.out::println);

                    // 좋지 않은 코드!
                    // 스트림의 과용으로 코드 읽기가 매우 난해해졌다...
                }
                catch (IOException e) {}
            }

            // [3] 둘 사이의 절충지점 - 깔끔하고 명료해진다!
            {
                Path dictionary = Paths.get(args[0]);
                int minGroupSize = Integer.parseInt(args[1]);

                try (Stream<String> words = Files.lines(dictionary)) {
                    words.collect(Collectors.groupingBy(word -> alphabetize(word)))
                    .values().stream()
                    .filter(group -> group.size() >= minGroupSize)
                    .forEach(group -> System.out.println(group.size() + ": " + group));
                }
                catch (IOException e) {}
            }

            // [4] 메르센 소수 출력하기
            {
                primes().map(p -> BigInteger.valueOf(2L).pow(p.intValueExact()).subtract(BigInteger.ONE))
                    .filter(mersenne -> mersenne.isProbablePrime(50))
                    .limit(20)
                    .forEach(System.out::println);
            }

            // [5-1] 데카르트 곱 계산을 반복 방식으로 구현
            {
                // result 안에 모든 카드들을 넣는다
                List<Card> result = new ArrayList<>();
                for (Suit suit : Suit.values()) {
                    for (Rank rank : Rank.values()) {
                        result.add(new Card(suit, rank));
                    }
                }
            }

            // [5-2] 스트림을 사용하여 구현
            {
                // result 안에 모든 카드들을 넣는다
                List<Card> result =
                    Stream.of(Suit.values()).flatMap(suit ->
                        Stream.of(Rank.values())
                            .map(rank -> new Card(suit, rank)))
                    .collect(Collectors.toList());
            }
        }

        public static String alphabetize(String s) {
            char[] a = s.toCharArray();
            Arrays.sort(a);
            return new String(a);
        }

        public static Stream<BigInteger> primes() {
            return Stream.iterate(BigInteger.valueOf(2L), BigInteger::nextProbablePrime);
        }

        public static class Card {
            final Suit suit;
            final Rank rank;

            public Card(Suit suit, Rank rank) {
                this.suit = suit;
                this.rank = rank;
            }
        }

        public static enum Suit {
            SPADE, DIAMOND, HEART, CLOVER;
        }

        public static enum Rank {
            A, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, J, Q, K;
        }
    }
}