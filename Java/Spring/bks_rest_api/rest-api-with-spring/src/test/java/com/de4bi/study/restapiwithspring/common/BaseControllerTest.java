package com.de4bi.study.restapiwithspring.common;

import com.de4bi.study.restapiwithspring.events.EventValidator;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import lombok.RequiredArgsConstructor;

@ExtendWith(SpringExtension.class) /* @org.junit.RunWith(SpringRunner.class) : junit4 */
@SpringBootTest // @SpringBootApplication을 찾아서 모든 Bean의 등록을 수행해줌
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(RestDocConfiguration.class)
@Disabled
public class BaseControllerTest {
 
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected ModelMapper modelMapper;
}
