import java.math.BigInteger;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Item48 {
    /**
     * [Item48] 스트림 병렬화는 주의해서 적용하라
     * 
     * [핵심]
     * 계산도 올바로 수행하고 성능도 빨라질 거라는 "확신" 없이는 스트림 파이프라인
     * 병렬화는 시도조차 하지 말라. 스트림을 잘못 병렬화하면 프로그램을 오동작하게 하거나
     * 성능을 급격히 떨어뜨린다. 병렬화하는 편이 낫다고 믿더라도, 수정 후의 코드가 여전히
     * 정확한지 확인하고 운영 환경과 유사한 조건에서 수행해보며 성능지표를 유심히 관찰하라.
     * 그래서 계산도 정확하고 성능도 좋아졌음이 확실해졌을 때, 오직 그럴 때만
     * 병렬화 버전 코드를 운영 코드에 반영하라.
     * 
     */

    // 실패한 병렬성의 예 - 실행하지 말 것!
    public static void mersenne() {
        Stream.iterate(BigInteger.valueOf(2L), BigInteger::nextProbablePrime)
            .parallel() // Item45의 코드에 병렬성을 추가하기 위해 parallel을 추가했다. (재앙)
            .map(p -> BigInteger.valueOf(2L).pow(p.intValueExact()).subtract(BigInteger.ONE))
            .filter(mersenne -> mersenne.isProbablePrime(50))
            .limit(20)
            .forEach(System.out::println);
        
        // parallel()을 추가하여 병렬로 계산한다고 더 빨라질까?
        // 전혀 아니다! 12초면 끝날 모든 출력이 1시간 30분이 지나도 끝나지 않는다!
        // 환경이 아무리 좋아도 데이터 소스가 Stream.iterate거나 중간 연산으로 limit을 쓰면
        // 파이프라인 병렬화로는 성능 개선을 기대할 수 없다.
        
        // limit을 사용하는 경우 CPU코어가 남는다면 원소를 몇개 더 처리한 후의 결과를 버려도
        // 상관없다고 가정한다. (15개의 코어로 limit(20)을 계산한다고 치면, 먼저 15개를 처리하고
        // 남은 5개 데이터를 15개의 코어로 연산하여 불필요한 10번의 연산을 더 하게 된다.
        // 소수를 구하는 이 메서드의 구조 상, 1번의 추가 연산당 소모되는 자원은 엄청나게 커진다!)

        // 병렬화에도 비용이 필요하다. 성능이 향상될지를 추정해보려면 스트림 안의 원소 수와
        // 수행되는 코드 줄 수를 곱해서 최소 수십만정도 된다면 기대해볼만 하다. [Lea14]
    }

    // 병렬화 하지 않은 n이하 소수 계산 스트림 -> 병렬화에 적합하다
    public static long pi(long n) {
        return LongStream.rangeClosed(2, n)
            .mapToObj(BigInteger::valueOf)
            .filter(i -> i.isProbablePrime(50))
            .count();
    }

    // 병렬화한 n이하 소수 계산 스트림 (CPU에 따라 다르지만, 저자는 약 3.37배 빠르다고 한다.)
    public static long pi2(long n) {
        return LongStream.rangeClosed(2, n)
            .parallel()
            .mapToObj(BigInteger::valueOf)
            .filter(i -> i.isProbablePrime(50))
            .count();
    }

    public static void main(String[] args) {
        System.out.println(pi(100));
        System.out.println(pi2(100));
    }
}