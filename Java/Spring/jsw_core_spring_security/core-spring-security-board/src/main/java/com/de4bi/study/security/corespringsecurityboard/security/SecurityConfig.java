package com.de4bi.study.security.corespringsecurityboard.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    private final UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /* [메모리 방식의 사용자 인증]
        // String password = passwordEncoder().encode("1111");

        // auth.inMemoryAuthentication().withUser("user").password(password).roles(UserRoles.USER.name());
        // auth.inMemoryAuthentication().withUser("manager").password(password).roles(UserRoles.MANAGER.name());
        // auth.inMemoryAuthentication().withUser("admin").password(password).roles(UserRoles.ADMIN.name());
        */

        // [DB를 연동한 사용자 인증]
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/", "/users").permitAll()
            .antMatchers("/mypage").hasRole(UserRoles.USER.name())
            .antMatchers("/messages").hasRole(UserRoles.MANAGER.name())
            .antMatchers("/config").hasRole(UserRoles.ADMIN.name())
            .anyRequest().authenticated()
        ;

        http
            .formLogin()
        ;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 정적 자원들에 대해 보안 필터를 무시 (StaticResourceLocation.class)
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
}
