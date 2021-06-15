package com.de4bi.study.security.corespringsecurityboard.security.configs;

import com.de4bi.study.security.corespringsecurityboard.security.filter.AjaxLoginProcessingFilter;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * AjaxSecurityConfig의 기능을 대체할 수 있는 클래스. (Custom DSL: Domain Specified Language.)
 */
public final class AjaxLoginConfigurer<H extends HttpSecurityBuilder<H>> extends
    AbstractAuthenticationFilterConfigurer<H, AjaxLoginConfigurer<H>, AjaxLoginProcessingFilter> {

    private AuthenticationSuccessHandler authenticationSuccessHandler;
    private AuthenticationFailureHandler authenticationFailureHandler;
    private AuthenticationManager authenticationManager;

    public AjaxLoginConfigurer() {
        super(new AjaxLoginProcessingFilter(), null);
    }

    @Override
    public void init(H http) throws Exception {
        super.init(http);
    }

    @Override
    public void configure(H http) throws Exception {

        if (authenticationManager == null) {
            authenticationManager = http.getSharedObject(AuthenticationManager.class);
        }

        final AjaxLoginProcessingFilter filter = getAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManager);
        filter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(authenticationFailureHandler);

        SessionAuthenticationStrategy strategy = http.getSharedObject(SessionAuthenticationStrategy.class);
        if (strategy != null) {
            filter.setSessionAuthenticationStrategy(strategy);
        }

        RememberMeServices rememberMeServices = http.getSharedObject(RememberMeServices.class);
        if (rememberMeServices != null) {
            filter.setRememberMeServices(rememberMeServices);
        }

        http.setSharedObject(AjaxLoginProcessingFilter.class, filter);
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected RequestMatcher createLoginProcessingUrlMatcher(String loginProcessingUrl) {
        return new AntPathRequestMatcher(loginProcessingUrl, HttpMethod.POST.name());
    }

    public AjaxLoginConfigurer<H> setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        return this;
    }

    public AjaxLoginConfigurer<H> successHandlerAjax(AuthenticationSuccessHandler authenticationSuccessHandler) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        return this;
    }

    public AjaxLoginConfigurer<H> failureHandlerAjax(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
        return this;
    }
}
