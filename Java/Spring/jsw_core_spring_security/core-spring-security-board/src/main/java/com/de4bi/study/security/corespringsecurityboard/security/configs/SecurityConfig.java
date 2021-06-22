package com.de4bi.study.security.corespringsecurityboard.security.configs;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.de4bi.study.security.corespringsecurityboard.security.UserRoles;
import com.de4bi.study.security.corespringsecurityboard.security.filter.AjaxLoginProcessingFilter;
import com.de4bi.study.security.corespringsecurityboard.security.handler.CustomAccessDeniedHandler;
import com.de4bi.study.security.corespringsecurityboard.security.handler.CustomAuthenticationFailureHandler;
import com.de4bi.study.security.corespringsecurityboard.security.handler.CustomAuthenticationSuccessHandler;
import com.de4bi.study.security.corespringsecurityboard.security.metadatasource.UrlFilterInvocationSecurityMetadataSource;
import com.de4bi.study.security.corespringsecurityboard.security.provider.CustomAuthenticationProvider;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    private final UserDetailsService userDetailsService;
    private final AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new CustomAuthenticationProvider(userDetailsService, passwordEncoder());
    }

    @Bean
    public CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean
    public CustomAuthenticationFailureHandler customAuthenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        CustomAccessDeniedHandler handler = new CustomAccessDeniedHandler();
        handler.setErrorPage("/denied");
        return handler;
    }

    @Bean
    public FilterSecurityInterceptor customFilterSecurityInterceptor() throws Exception {
        FilterSecurityInterceptor filterSecurityInterceptor = new FilterSecurityInterceptor();
        filterSecurityInterceptor.setSecurityMetadataSource(urlFilterInvocationSecurityMetadataSource());
        filterSecurityInterceptor.setAccessDecisionManager(affirmativeBased());
        filterSecurityInterceptor.setAuthenticationManager(authenticationManagerBean());
        return filterSecurityInterceptor;
    }

    private UrlFilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource() {
        return new UrlFilterInvocationSecurityMetadataSource();
    }

    private AccessDecisionManager affirmativeBased() {
        AffirmativeBased affirmativeBased = new AffirmativeBased(getAccessDecistionVoters());
        return affirmativeBased;
    }

    private List<AccessDecisionVoter<?>> getAccessDecistionVoters() {
        return Arrays.asList(new RoleVoter());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /* [1-1. 메모리 방식의 사용자 인증]
        // String password = passwordEncoder().encode("1111");

        // auth.inMemoryAuthentication().withUser("user").password(password).roles(UserRoles.USER.name());
        // auth.inMemoryAuthentication().withUser("manager").password(password).roles(UserRoles.MANAGER.name());
        // auth.inMemoryAuthentication().withUser("admin").password(password).roles(UserRoles.ADMIN.name());
        */

        // [1-2. DB를 연동한 사용자 인증 (내부에서 accountRepository를 사용)]
        // auth.userDetailsService(userDetailsService);

        // [1-3. 사용자 정의 인증 Provider 등록 (내부에서 userDetailsService, passwordEncoder를 사용)]
        // (주의: 1, 2, 3 방식을 동시에 사용하면, Provider가 여러개가 등록되게 되고 그 중 하나만 통과하면 인증이 성공하는 것으로 보인다!)
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // [2-1] HttpSecurity로 인가처리
        // http
        //     .authorizeRequests()
        //     .antMatchers("/", "/users", "/user/login/**", "/login*").permitAll()
        //     .antMatchers("/mypage").hasAuthority(UserRoles.USER.name())
        //     .antMatchers("/messages").hasAuthority(UserRoles.MANAGER.name())
        //     .antMatchers("/config").hasAuthority(UserRoles.ADMIN.name())
        //     .anyRequest().authenticated()
        // ;

        http
            .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .loginProcessingUrl("/login_proc")
                .authenticationDetailsSource(authenticationDetailsSource)
                .successHandler(customAuthenticationSuccessHandler())
                .failureHandler(customAuthenticationFailureHandler())
                //.permitAll()
        ;

        http
            .exceptionHandling().accessDeniedHandler(accessDeniedHandler())
        ;

        // [2] FilterSecurityInterceptor + FilterInvocationSecurityMetadataSocurce로 인가처리
        http
            .addFilterBefore(customFilterSecurityInterceptor(), FilterSecurityInterceptor.class)
        ;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 정적 자원들에 대해 보안 필터를 무시 (StaticResourceLocation.class)
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
}
