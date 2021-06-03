package com.de4bi.study.security.corespringsecurity;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import lombok.extern.slf4j.Slf4j;

/*
    [Reqeust] 
    |
    [UsernamePasswordAuthenticationFilter] <------------------------------------------------+
    |                                                                                       |
    + 요청 정보 매핑 확인                                                                   |
    |                                                                                       |
    [AndPathRequestMatcher(login)] - NO -> <chain.doFilter>                                 |
    |                                                                                       |
    + YES                                                                                   |
    |                                                                                       |
    [Authentication(Username + Password)]                                                   |
    |                                                                                       |
    + 인증                                                                                  |
    |                                                                                       |
    [AuthenticationManager] - 위임 -> [AuthenticationProvider] - 인증 실패 -> [AutehnticationException]
    |                                 |
    + <- 인증 성공 -------------------+
    |
    [Authentication(User + Authorities)]
    |
    [Save into SecurityContext]
    |
    [SuccessHandler]
*/

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

        // 로그아웃
        http
            .logout()
                .logoutUrl("/logout") // 로그아웃 처리 URL (원칙적으로는 POST방식으로만 로그아웃 가능)
                .logoutSuccessUrl("/login") // 로그아웃 성공 후 이동페이지
                .addLogoutHandler(new LogoutHandler() {
                    @Override
                    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
                        HttpSession session = request.getSession();
                        session.invalidate();
                    }
                }) // 로그아웃 핸들러
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(
                        HttpServletRequest request, HttpServletResponse response, Authentication authentication
                        ) throws IOException, ServletException {
                            response.sendRedirect("/login");
                        }
                    }) // 로그아웃 성공 후 핸들러
                .deleteCookies("JSESSIONID", "remember-me") // 로그아웃 후 쿠키삭제
        ;

        // remember-me (로그인 유지)
        http
            .rememberMe()
                .rememberMeParameter("remember") // 기본 파라미터명은 "remember-me"
                .tokenValiditySeconds(3600) // default: 14일 (3600 -> 1시간)
                .alwaysRemember(true) // remember-me 기능이 활성화되지 않아도 항상 실행 (권장: false)
                /*.userDetailsService(new UserDetailsService() {
                    @Override
                    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                        // TODO Auto-generated method stub
                        return null;
                    }
                }); // 사용자 정의 핸들러 */
        ;
    }
}
