package com.de4bi.study.restapiwithspring.configs;

import com.de4bi.study.restapiwithspring.accounts.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    AccountService accountService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(accountService)
            .passwordEncoder(passwordEncoder);
    }

    // 1. WebSecurity를 사용
    @Override
    public void configure(WebSecurity web) throws Exception { 
        web.ignoring().mvcMatchers("/docs/index.html"); // 인덱스 페이지는 시큐리티에서 제외
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations()); // 스프링 부트의 기본 static 위치의 모든 경로는 시큐리티에서 제외
    }

    // // 2. HttpSecurity를 사용
    // @Override
    // protected void configure(HttpSecurity http) throws Exception {
    //     http
    //         .anonymous()
    //             .and()
    //         .formLogin()
    //             .and()
    //         .authorizeRequests()
    //             .mvcMatchers(HttpMethod.GET, "/api/**").anonymous()
    //             .anyRequest().authenticated();
    // }

        
}
