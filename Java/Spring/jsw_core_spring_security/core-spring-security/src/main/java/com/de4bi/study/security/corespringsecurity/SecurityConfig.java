package com.de4bi.study.security.corespringsecurity;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    public static final String LOGIN_PAGE_URL = "/loginPage";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 인증 정책
        http
            .authorizeRequests()
            .anyRequest().authenticated()
        ;

        // 인가 정책
        http
            .formLogin()
                //.loginPage(LOGIN_PAGE_URL) // 사용자 정의 로그인 페이지
                .defaultSuccessUrl("/") // 로그인 성공 후 이동 페이지
                .failureUrl(LOGIN_PAGE_URL) // 로그인 실패 후 이동 페이지
                .usernameParameter("userId") // 아이디 파라미터명 설정
                .passwordParameter("passwd") // 패스워드 파라미터명 설정
                .loginProcessingUrl("/login_proc") // 로그인 Form Action Url
                /*.successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        log.info("authentiation: " + authentication.getName());
                        response.sendRedirect("/");
                    }
                }) // 로그인 성공 후 핸들러
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
                        log.info("exception: " + authException.getMessage());
                        response.sendRedirect(LOGIN_PAGE_URL);
                    }
                }) // 로그인 실패 후 핸들러*/
                .permitAll() // 인증받지 않아도 접근 가능하도록 허용
        ;
    }
}
