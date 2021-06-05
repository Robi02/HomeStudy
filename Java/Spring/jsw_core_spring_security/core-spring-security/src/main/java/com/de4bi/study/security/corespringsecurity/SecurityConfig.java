package com.de4bi.study.security.corespringsecurity;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user").password("{noop}1111").roles("USER");
        auth.inMemoryAuthentication().withUser("sys").password("{noop}1111").roles("SYS");
        auth.inMemoryAuthentication().withUser("admin").password("{noop}1111").roles("ADMIN", "SYS", "USER"); // Role hierarchy를 적용하면 ADMIN만 사용 가능
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 인가
        http
            .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/user").hasRole("USER")
                .antMatchers("/admin/pay").hasRole("ADMIN")
                .antMatchers("/admin/**").access("hasRole('ADMIN') or hasRole('SYS')")
                .anyRequest().authenticated()
        ;

        // 로그인
        http
            .formLogin()
            .successHandler((request, response, authentication) -> {
                RequestCache requestCache = new HttpSessionRequestCache();
                SavedRequest savedRequest = requestCache.getRequest(request, response); // 세션에서 저장된 정보 획득
                String redirectUrl = savedRequest.getRedirectUrl();
                response.sendRedirect(redirectUrl);
            }) // AuthenticationSuccessHandler.onAuthenticationSuccess(...)
        ;

        // 예외 처리
        http
            .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> { // AuthenticationEntryPoint().commence(...);
                    response.sendRedirect("/login");
                }) // 인증실패 시 처리
                .accessDeniedHandler((request, response, accessDeniedException) -> { // AccessDeniedHandler().handle(...)
                    response.sendRedirect("/denied");
                }) // 인가실패 시 처리
        ;

        // CSRF (Cross Site Request Forgery)
        http
            .csrf(); // .disable(); // 기본은 활성상태
    }
}
