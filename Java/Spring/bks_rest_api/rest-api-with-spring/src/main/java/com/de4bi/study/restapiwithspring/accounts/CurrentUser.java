package com.de4bi.study.restapiwithspring.accounts;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@AuthenticationPrincipal(expression = "account")
public @interface CurrentUser {
    // SecurityContextHolder.getContext().getAuthentication().getPrincipal()에서 account 게터의 반환 결과를 주입
    // AccountService의 loadUserByUsername()에서 반환하는 결과 타입의 expression="account"게터)
}
