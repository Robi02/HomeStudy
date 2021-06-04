// package com.de4bi.study.security.corespringsecurity;

// import java.io.IOException;

// import javax.servlet.ServletException;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
// import javax.servlet.http.HttpSession;

// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.AuthenticationException;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
// import org.springframework.security.web.authentication.AuthenticationFailureHandler;
// import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
// import org.springframework.security.web.authentication.logout.LogoutFilter;
// import org.springframework.security.web.authentication.logout.LogoutHandler;
// import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

// import lombok.extern.slf4j.Slf4j;

// /*
//     [Reqeust] 
//     |
//     [UsernamePasswordAuthenticationFilter] <------------------------------------------------+
//     |                                                                                       |
//     + 요청 정보 매핑 확인                                                                   |
//     |                                                                                       |
//     [AndPathRequestMatcher(login)] - NO -> <chain.doFilter>                                 |
//     |                                                                                       |
//     + YES                                                                                   |
//     |                                                                                       |
//     [Authentication(Username + Password)]                                                   |
//     |                                                                                       |
//     + 인증                                                                                  |
//     |                                                                                       |
//     [AuthenticationManager] - 위임 -> [AuthenticationProvider] - 인증 실패 -> [AutehnticationException]
//     |                                 |
//     + <- 인증 성공 -------------------+
//     |
//     [Authentication(User + Authorities)]
//     |
//     [Save into SecurityContext]
//     |
//     [SuccessHandler]
// */

// @Slf4j
// @Configuration
// @EnableWebSecurity
// public class SecurityConfigExample extends WebSecurityConfigurerAdapter {
    
//     public static final String LOGIN_PAGE_URL = "/loginPage";

//     @Override
//     protected void configure(HttpSecurity http) throws Exception {
//         // 인증 정책
//         http
//             .authorizeRequests()
//             .anyRequest().authenticated()
//         ;

//         // 로그인 정책
//         http
//             .formLogin()
//                 //.loginPage(LOGIN_PAGE_URL) // 사용자 정의 로그인 페이지
//                 .defaultSuccessUrl("/") // 로그인 성공 후 이동 페이지
//                 .failureUrl(LOGIN_PAGE_URL) // 로그인 실패 후 이동 페이지
//                 .usernameParameter("userId") // 아이디 파라미터명 설정
//                 .passwordParameter("passwd") // 패스워드 파라미터명 설정
//                 .loginProcessingUrl("/login_proc") // 로그인 Form Action Url
//                 /*.successHandler(new AuthenticationSuccessHandler() {
//                     @Override
//                     public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//                         log.info("authentiation: " + authentication.getName());
//                         response.sendRedirect("/");
//                     }
//                 }) // 로그인 성공 후 핸들러
//                 .failureHandler(new AuthenticationFailureHandler() {
//                     @Override
//                     public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
//                         log.info("exception: " + authException.getMessage());
//                         response.sendRedirect(LOGIN_PAGE_URL);
//                     }
//                 }) // 로그인 실패 후 핸들러*/
//                 .permitAll() // 인증받지 않아도 접근 가능하도록 허용
//         ;

//         // 로그아웃
//         http
//             .logout()
//                 .logoutUrl("/logout") // 로그아웃 처리 URL (원칙적으로는 POST방식으로만 로그아웃 가능)
//                 .logoutSuccessUrl("/login") // 로그아웃 성공 후 이동페이지
//                 .addLogoutHandler(new LogoutHandler() {
//                     @Override
//                     public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
//                         HttpSession session = request.getSession();
//                         session.invalidate();
//                     }
//                 }) // 로그아웃 핸들러
//                 .logoutSuccessHandler(new LogoutSuccessHandler() {
//                     @Override
//                     public void onLogoutSuccess(
//                         HttpServletRequest request, HttpServletResponse response, Authentication authentication
//                         ) throws IOException, ServletException {
//                             response.sendRedirect("/login");
//                         }
//                     }) // 로그아웃 성공 후 핸들러
//                 .deleteCookies("JSESSIONID", "remember-me") // 로그아웃 후 쿠키삭제
//         ;

//         // remember-me (로그인 유지)
//         http
//             .rememberMe()
//                 .rememberMeParameter("remember") // 기본 파라미터명은 "remember-me"
//                 .tokenValiditySeconds(3600) // default: 14일 (3600 -> 1시간)
//                 .alwaysRemember(true) // remember-me 기능이 활성화되지 않아도 항상 실행 (권장: false)
//                 /*.userDetailsService(new UserDetailsService() {
//                     @Override
//                     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//                         // TODO Auto-generated method stub
//                         return null;
//                     }
//                 }); // 사용자 정의 핸들러 */
//         ;

//         // 익명 사용자 관련
//         /* http
//             .anonymous();
//         */

//         // 세션 관련
//         http
//             .sessionManagement()
//                 .maximumSessions(-1) // 최대 허용가능 세션 수 (-1: 무제한)
//                 .maxSessionsPreventsLogin(true) // true: 동시 로그인 차단, false: 기존 세션 만료(default)
//                 .expiredUrl("/login") // 세션이 만료되면 이동할 페이지
//                 .and()
//                     .invalidSessionUrl("/login") // 비인가 세션이면 이동할 페이지
//                     .sessionFixation()
//                         // .none() // 기존 세션아이디 그대로 사용 (위험)
//                         // .migrateSession() // Servlet3.1 미만에서 기본값으로 사용 (changeSessionId와 동일한 기능?)
//                         .changeSessionId() // Servlet3.1이상에서 기본값, 속성값은 유지, 세션아이디를 응답시마다 변경시켜서 세션고정(SessionFixation) 보호 (default)
//                         // .newSession() // 세션 속성값도 새로 초기화하여 세션아이디 변경
//                 // .sessionCreationPolicy(SessionCreationPolicy.ALWAYS) // 스프링 시큐리티가 항상 세션 생성
//                 .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // 필요 시 세션 생성 (기본)
//                 // .sessionCreationPolicy(SessionCreationPolicy.NEVER) // 생성하지 않지만, 이미 존재하면 사용
//                 // .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션을 생성하지 않고, 존재해도 사용하지 않음
//         ;

//         // 인가 정책
//         http
//             .antMatcher("/**")
//             .authorizeRequests()
//                 .antMatchers("/shop/login", "shop/users/**").permitAll()
//                 .antMatchers("/shop/myapge").hasRole("USER")
//                 .antMatchers("/shop/admin/pay").access("hasRole('ADMIN')")
//                 .antMatchers("/shop/admin/**").access("hasRole('ADMIN') or hasRole('SYS')")
//                 .anyRequest().authenticated()
//         ; // 설정 시 구체적인 경로가 먼저 오고, 보다 큰 범위의 경로가 뒤에 위치하도록 해야 한다
//         /**
//          * authenticated() 인증된 사용자의 접근 허용
//          * fullyAuthenticated() 위와 동일하지만, remember-me 인증은 제외
//          * permitAll() 무조건 접근 허용 
//          * denyAll() 무조건 접근을 허용하지 않음
//          * anonymouse() 익명 사용자만 접근을 허용 (RoleAnonymouse허용, RoleUser비허용)
//          * rememberMe() 기억하기를 통해 인증된 사용자 접근 허용
//          * access(String) 주어진 SpEL(Spring Expression Language)표현식의 평가 결과가 true인 경우 허용
//          * has(Any)Role(String) 사용자가 주어진 역할이 있다면 접근 허용
//          * has(Any)Authority(String) 사용자가 주어진 권한이 있다면 접근 허용
//          * hasIpAddress(String) 주어진 IP로부터 요청이 온 경우 허용
//          */
//     }
// }
