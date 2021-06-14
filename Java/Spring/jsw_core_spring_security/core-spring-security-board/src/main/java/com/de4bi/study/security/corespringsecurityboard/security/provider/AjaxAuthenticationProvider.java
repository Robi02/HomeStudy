package com.de4bi.study.security.corespringsecurityboard.security.provider;

import com.de4bi.study.security.corespringsecurityboard.domain.Account;
import com.de4bi.study.security.corespringsecurityboard.security.common.FormWebAuthenticationDetails;
import com.de4bi.study.security.corespringsecurityboard.security.service.AccountContext;
import com.de4bi.study.security.corespringsecurityboard.security.token.AjaxAuthenticationToken;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AjaxAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 사용자 정의 파라미터 검사
        // String secretKey = ((FormWebAuthenticationDetails) authentication.getDetails()).getSecretKey();
        // if (secretKey == null || "secret".equals(secretKey) == false) {
        //     throw new IllegalArgumentException("Invalid Secret");
        // }

        // 기본 계정 검사
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        
        AccountContext accountContext = (AccountContext) userDetailsService.loadUserByUsername(username);
        Account account = accountContext.getAccount();

        if (!passwordEncoder.matches(password, account.getPassword())) {
            throw new BadCredentialsException("Invaild Password.");
        }

        // 인증 토큰 생성
        AjaxAuthenticationToken authenticationToken =
            new AjaxAuthenticationToken(account, null, accountContext.getAuthorities());
        
        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(AjaxAuthenticationToken.class);
    }
}