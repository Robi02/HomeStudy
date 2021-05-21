package com.de4bi.study.restapiwithspring.events;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// 입력을 받을 수 있는 값을 다루기 위해 DTO객체를 만듬.
// 단점: 중복이 생긴다. (Event.java와 중복되는 필드가 매우 많다)
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class EventDto {
    
    private String name;
    private String description;
    private LocalDateTime beginEnrollmentDateTime;
    private LocalDateTime closeEnrollmentDateTime;
    private LocalDateTime beginEventDateTime;
    private LocalDateTime endEventDateTime;
    private String location; // (optional) 값이 없는 경우 온라인 모임
    private int basePrice; // (optional)
    private int maxPrice; // (optional)
    private int limitOfEnrollment;
}
