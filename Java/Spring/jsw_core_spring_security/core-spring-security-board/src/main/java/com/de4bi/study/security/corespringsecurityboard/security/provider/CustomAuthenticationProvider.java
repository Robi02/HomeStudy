package com.de4bi.study.security.corespringsecurityboard.security.provider;

import com.de4bi.study.security.corespringsecurityboard.domain.Account;
import com.de4bi.study.security.corespringsecurityboard.security.common.FormWebAuthenticationDetails;
import com.de4bi.study.security.corespringsecurityboard.security.service.AccountContext;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
    
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 사용자 정의 파라미터 검사
        FormWebAuthenticationDetails details = (FormWebAuthenticationDetails) authentication.getDetails();

        if (details == null || !"secret".equals(details.getSecretKey())) {
            throw new InsufficientAuthenticationException("InsufficientAuthenticationException");
        }

        // 기본 계정 검사
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        
        AccountContext accountContext = (AccountContext) userDetailsService.loadUserByUsername(username);
        Account account = accountContext.getAccount();

        if (!passwordEncoder.matches(password, account.getPassword())) {
            throw new BadCredentialsException("BadCredentialsException");
        }

        // 인증 토큰 생성
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(account, null, accountContext.getAuthorities());
        
        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
