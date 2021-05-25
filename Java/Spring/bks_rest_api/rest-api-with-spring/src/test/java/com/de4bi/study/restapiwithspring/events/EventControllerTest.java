package com.de4bi.study.restapiwithspring.events;

import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.relaxedResponseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDateTime;

import com.de4bi.study.restapiwithspring.common.RestDocConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class) /* @org.junit.RunWith(SpringRunner.class) : junit4 */
@SpringBootTest // @SpringBootApplication을 찾아서 모든 Bean의 등록을 수행해줌
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(RestDocConfiguration.class)
public class EventControllerTest {
    
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("정상적으로 이벤트를 생성하는 테스트")
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
            .andExpect(jsonPath("free").value(Matchers.not(true)))
            .andExpect(jsonPath("offline").value(true))
            .andExpect(jsonPath("eventStatus").value(EventStatus.DRAFT.name()))
            .andExpect(jsonPath("_links.self").exists())
            .andExpect(jsonPath("_links.query-events").exists())
            .andExpect(jsonPath("_links.update-event").exists()) // 문서화 하면서 체크하기 때문에 생략 가능한 필드가 몇개 있다.
            .andDo(
                document("create-event", 
                    links(
                        linkWithRel("self").description("link to self"),
                        linkWithRel("query-events").description("link to query events"),
                        linkWithRel("update-event").description("link to update an existing event"),
                        linkWithRel("profile").description("link to profile doc")
                    ),
                    requestHeaders(
                        headerWithName(HttpHeaders.ACCEPT).description("accept header"),
                        headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
                    ),
                    requestFields(
                        fieldWithPath("name").description("Name of new event"),
                        fieldWithPath("description").description("description of new event"),
                        fieldWithPath("beginEnrollmentDateTime").description("date time of begin of new event"),
                        fieldWithPath("closeEnrollmentDateTime").description("date time of close of new event"),
                        fieldWithPath("beginEventDateTime").description("date time of begin of new event"),
                        fieldWithPath("endEventDateTime").description("date time of end of new event"),
                        fieldWithPath("location").description("location of new event"),
                        fieldWithPath("basePrice").description("base price of new event"),
                        fieldWithPath("maxPrice").description("max price of new event"),
                        fieldWithPath("limitOfEnrollment").description("limit of enrollment")
                    ),
                    responseHeaders(
                        headerWithName(HttpHeaders.LOCATION).description("accept header"),
                        headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
                    ),
                    responseFields( //relaxedResponseFields::사용 시 응답의 일부분만 있어도 문서화 가능
                        fieldWithPath("id").description("identifier of new event"),
                        fieldWithPath("name").description("Name of new event"),
                        fieldWithPath("description").description("description of new event"),
                        fieldWithPath("beginEnrollmentDateTime").description("date time of begin of new event"),
                        fieldWithPath("closeEnrollmentDateTime").description("date time of close of new event"),
                        fieldWithPath("beginEventDateTime").description("date time of begin of new event"),
                        fieldWithPath("endEventDateTime").description("date time of end of new event"),
                        fieldWithPath("location").description("location of new event"),
                        fieldWithPath("basePrice").description("base price of new event"),
                        fieldWithPath("maxPrice").description("max price of new event"),
                        fieldWithPath("limitOfEnrollment").description("limit of enrollment"),
                        fieldWithPath("free").description("it tells is the event is free or not"),
                        fieldWithPath("offline").description("it tells if thie event is offline event or not"),
                        fieldWithPath("eventStatus").description("event status"),

                        fieldWithPath("_links.self.href").description("link to self"),
                        fieldWithPath("_links.query-events.href").description("link to query events"),
                        fieldWithPath("_links.update-event.href").description("link to update an existing event"),
                        fieldWithPath("_links.profile.href").description("link to profile")
                    )
            ))
        ;
    }

    @Test
    @DisplayName("입력 받을 수 없는 값을 사용하는 경우에 에러가 발생하는 테스트")
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
    @DisplayName("입력 값이 비어있는 경우에 에러가 발생하는 테스트")
    public void createEvent_Bad_Request_Empty_Input() throws Exception {
        EventDto eventDto = EventDto.builder().build();

        mockMvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(eventDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("입력 값이 잘못된 경우에 에러가 발생하는 테스트")
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
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors[0].objectName").exists())
                //.andExpect(jsonPath("errors[0].field").exists())
                .andExpect(jsonPath("errors[0].defaultMessage").exists())
                .andExpect(jsonPath("errors[0].code").exists())
                //.andExpect(jsonPath("errors[0].rejectedValue").exists())
        ;
    }
}
