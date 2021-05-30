package com.de4bi.study.restapiwithspring.configs;

import com.de4bi.study.restapiwithspring.accounts.Account;
import com.de4bi.study.restapiwithspring.accounts.AccountRepository;
import com.de4bi.study.restapiwithspring.accounts.AccountRole;
import com.de4bi.study.restapiwithspring.accounts.AccountService;
import com.de4bi.study.restapiwithspring.common.BaseControllerTest;
import com.de4bi.study.restapiwithspring.commons.AppProperties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class AuthServerConfigTest extends BaseControllerTest {
    
    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AppProperties appProperties;

    @BeforeEach
    public void setup() {
        accountRepository.deleteAll();
    }

    @Test
    @DisplayName("인증 토큰을 발급받는 테스트")
    public void getAuthToken() throws Exception {
        Account account = Account.builder()
            .email(appProperties.getUserUsername())
            .password(appProperties.getUserPassowrd())
            .roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
            .build();
            
        this.accountService.saveAccount(account);

        this.mockMvc.perform(post("/oauth/token")
                    .with(httpBasic(appProperties.getClientId(), appProperties.getClientSecret()))
                    .param("username", appProperties.getUserUsername())
                    .param("password", appProperties.getUserPassowrd())
                    .param("grant_type", "password")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("access_token").exists());
    }
}
