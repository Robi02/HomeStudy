// package com.de4bi.study.security.corespringsecurity;

// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
// import org.springframework.security.web.savedrequest.RequestCache;
// import org.springframework.security.web.savedrequest.SavedRequest;

// /**
//  * 
//  * ServletFilter는 Sevlet Container의 영역이므로, SpringBean의 Injection을 사용할 수 없다.
//  * 
//  * 따라서, ServletFilter에 FilterProxy를 생성하여 요청을 Bean에 위임하는 방식으로 스프링 시큐리티가 설계되어 있다.
//  * 
//  * DelegationFilterPorxy -> SpringBean -> ServletFilter
//  *                              |
//  *                      (FilterChainProxy - springSecurityFilterChain이라는 이름으로 생성되는 필터 빈
//  *                                  DelegationFilterProxy로부터 요청을 위임받고 실제 보안처리를 수행한다.)
//  * 
//  * FilterChainProxy는 등록된 각 필터들을 순서대로 처리하고 모든 필터에 대한 처리가 완료되면 서블릿에 접근할 수 있게 된다.
//  * 
//  * [ FilterChainProxy ]
//  * WebAsyncManagerIntegrationFilter
//  * SecurityContextPersistenceFilter
//  * HeaderWriterFilter
//  * CsrfFilter
//  * LogoutFilter
//  * UsernamePasswordAuthenticationFilter
//  * DefaultLoginPageGenerationgFilter
//  * DefaultLogoutPageGenerationgFilter
//  * ConcurrentSessionFilter
//  * RequestCacheAwareFilter
//  * SecurityContextHolderAwareRequetFilter
//  * AnonymouseAuthenticationFilter
//  * SessionmanagementFilter
//  * ExceptionTranslationFilter
//  * FilterSecurityInterceptor
//  * ... (그 외 사용자 정의 필터 선언 가능)
//  * 
//  * [ 여러 설정파일의 경우 각각 생성되어 FilterChainProxy에 등록 ]
//  * WebSecurityConfigurerAdapdor(1) -> SecurityFilterChain -> | FilterChainProxy |
//  * WebSecurityConfigurerAdapdor(2) -> SecurityFilterChain -> |                  |
//  */
// @Configuration
// @EnableWebSecurity
// public class SecurityConfigExample2 extends WebSecurityConfigurerAdapter {
    
//     @Override
//     protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//         auth.inMemoryAuthentication().withUser("user").password("{noop}1111").roles("USER");
//         auth.inMemoryAuthentication().withUser("sys").password("{noop}1111").roles("SYS");
//         auth.inMemoryAuthentication().withUser("admin").password("{noop}1111").roles("ADMIN", "SYS", "USER"); // Role hierarchy를 적용하면 ADMIN만 사용 가능
//     }

//     @Override
//     protected void configure(HttpSecurity http) throws Exception {
//         // 인가
//         http
//             .authorizeRequests()
//                 .antMatchers("/login").permitAll()
//                 .antMatchers("/user").hasRole("USER")
//                 .antMatchers("/admin/pay").hasRole("ADMIN")
//                 .antMatchers("/admin/**").access("hasRole('ADMIN') or hasRole('SYS')")
//                 .anyRequest().authenticated()
//         ;

//         // 로그인
//         http
//             .formLogin()
//             .successHandler((request, response, authentication) -> {
//                 RequestCache requestCache = new HttpSessionRequestCache();
//                 SavedRequest savedRequest = requestCache.getRequest(request, response); // 세션에서 저장된 정보 획득
//                 String redirectUrl = savedRequest.getRedirectUrl();
//                 response.sendRedirect(redirectUrl);
//             }) // AuthenticationSuccessHandler.onAuthenticationSuccess(...)
//         ;

//         // 예외 처리
//         http
//             .exceptionHandling()
//                 .authenticationEntryPoint((request, response, authException) -> { // AuthenticationEntryPoint().commence(...);
//                     response.sendRedirect("/login");
//                 }) // 인증실패 시 처리
//                 .accessDeniedHandler((request, response, accessDeniedException) -> { // AccessDeniedHandler().handle(...)
//                     response.sendRedirect("/denied");
//                 }) // 인가실패 시 처리
//         ;

//         // CSRF (Cross Site Request Forgery)
//         http
//             .csrf(); // .disable(); // 기본은 활성상태
//     }
// }
