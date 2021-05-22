package com.de4bi.study.restapiwithspring.events;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class) /* @org.junit.RunWith(SpringRunner.class) : junit4 */
@SpringBootTest // @SpringBootApplication을 찾아서 모든 Bean의 등록을 수행해줌
@AutoConfigureMockMvc
public class EventControllerTest {
    
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void createEvent() throws Exception {
        EventDto event = EventDto.builder()
                .name("Spring")
                .description("REST API Development with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2021, 5, 21, 20, 21))
                .closeEnrollmentDateTime(LocalDateTime.of(2021, 5, 22, 20, 21))
                .beginEventDateTime(LocalDateTime.of(2021, 5, 23, 20, 21))
                .endEventDateTime(LocalDateTime.of(2021, 5, 24, 20, 21))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 D2 스타텁 팩토리")
                .build();

        mockMvc.perform(post("/api/events/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(event)))
            .andDo(print()) // 결과 출력
            .andExpect(status().isCreated()) // 201 Created
            .andExpect(jsonPath("id").exists())
            .andExpect(header().exists(HttpHeaders.LOCATION))
            .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
            .andExpect(jsonPath("id").value(Matchers.not(100)))
            .andExpect(jsonPath("free").value(Matchers.not(true)))
            .andExpect(jsonPath("eventStatus").value(EventStatus.DRAFT.name()));
    }

    @Test
    public void createEvent_Bad_Request() throws Exception {
        Event event = Event.builder()
                .id(100)
                .name("Spring")
                .description("REST API Development with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2021, 5, 21, 20, 21))
                .closeEnrollmentDateTime(LocalDateTime.of(2021, 5, 22, 20, 21))
                .beginEventDateTime(LocalDateTime.of(2021, 5, 23, 20, 21))
                .endEventDateTime(LocalDateTime.of(2021, 5, 24, 20, 21))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 D2 스타텁 팩토리")
                .free(true)
                .offline(false)
                .eventStatus(EventStatus.PUBLISHED)
                .build();

        mockMvc.perform(post("/api/events/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(event)))
            .andDo(print()) // 결과 출력
            .andExpect(status().isBadRequest()) // 400 Bad Request
            ;    

        // application.properties 파일에
        // spring.jackson.deserialization.fail-on-unknown-properties=true 옵션 추가 필요
    }

    @Test
    public void createEvent_Bad_Request_Empty_Input() throws Exception {
        EventDto eventDto = EventDto.builder().build();

        mockMvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(eventDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createEvent_Bad_Request_Wrong_Input() throws Exception {
        EventDto eventDto = EventDto.builder()
            .beginEnrollmentDateTime(LocalDateTime.of(2021, 5, 23, 20, 21))
            .closeEnrollmentDateTime(LocalDateTime.of(2021, 5, 22, 20, 21))
            .beginEventDateTime(LocalDateTime.of(2021, 5, 25, 20, 21))
            .endEventDateTime(LocalDateTime.of(2021, 5, 24, 20, 21)) // -> Date error
            .basePrice(10000)
            .maxPrice(200) // -> Base:10000 / Max:200
            .limitOfEnrollment(100)
            .location("강남역 D2 스타텁 팩토리")
            .build();

        mockMvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(eventDto)))
                .andDo(print()) // 결과 출력
                .andExpect(status().isBadRequest());
    }
}
