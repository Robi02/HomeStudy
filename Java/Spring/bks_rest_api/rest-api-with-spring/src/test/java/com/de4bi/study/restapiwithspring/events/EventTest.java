package com.de4bi.study.restapiwithspring.events;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

public class EventTest {

    @Test
    public void builder() {
        // Given & When
        Event event = Event.builder()
            .name("Inflearn Spring REST API")
            .description("REST API development with Spring")
            .build();

        // Then
        assertThat(event).isNotNull();
    }

    @Test
    public void javaBean() {
        // Given
        String name = "Event";
        String description = "Spring";
        
        // When
        Event event = new Event();
        event.setName(name);
        event.setDescription(description);
        
        // Then
        assertThat(event.getName()).isEqualTo(name);
        assertThat(event.getDescription()).isEqualTo(description);
    }

    @ParameterizedTest // junit5부터 지원되는 기능으로, pl.pragmatists 라이브러리를 대용하여 사용해 보았음.
    //@CsvSource({"0,0,true","100,0,false","0,100,false"}) // CSV형식으로 파라미터 사용 (warning: type-unsafe)
    //@MethodSource("paramsForTestFree") // 메서드명을 명시적으로 지정 (type-safe)
    @MethodSource // 메서드명이 같으면 생략 가능 (type-safe)
    public void testFree(int basePrice, int maxPrice, boolean isFree) {
        // Given
        Event event = Event.builder()
            .basePrice(basePrice)
            .maxPrice(maxPrice)
            .build();

        // When
        event.update();

        // Then
        assertThat(event.isFree()).isEqualTo(isFree);
    }

    private static Stream<Arguments> testFree() { // static 메서드여야 함
        return Stream.of(
            Arguments.of(0, 0, true),
            Arguments.of(100, 0, false),
            Arguments.of(0, 100, false)
        );
    }

    @ParameterizedTest
    @MethodSource
    public void testOffline(String location, boolean isOffline) {
        // Given
        Event event = Event.builder()
            .location(location)
            .build();

        // When
        event.update();

        // Then
        assertThat(event.isOffline()).isEqualTo(isOffline);
    }

    public static Stream<Arguments> testOffline() {
        return Stream.of(
            Arguments.of("네이버 D2 코딩 팩토리", true),
            Arguments.of(null, false)
        );
    }
}
