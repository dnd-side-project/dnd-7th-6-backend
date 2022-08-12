package com.hot6.phopa.core.security.config;

import com.hot6.phopa.core.domain.user.type.UserRole;
import com.hot6.phopa.core.security.jwt.JwtAuthenticationFilter;
import com.hot6.phopa.core.security.jwt.JwtTokenProvider;
import com.hot6.phopa.core.security.service.ConfigSuccessHandler;
import com.hot6.phopa.core.security.service.PrincipalOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity //spring security filter가 filterChain에 등록
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final PrincipalOauth2UserService principalOauth2UserService;

    private final ConfigSuccessHandler configSuccessHandler;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/v1/user/token/**").permitAll()
                // 가입 및 인증 주소는 누구나 접근 가능
                .antMatchers("/api/v1/user/**").hasAnyRole(UserRole.USER.getAuthority())
                // user 관련 api는 회원만 접근 가능
                .antMatchers(HttpMethod.POST, "/**").hasAnyRole(UserRole.USER.getAuthority())
                // 나머지 POST 메소드는 회원만 접근 가능
                .anyRequest().permitAll()
                // 그 외 나머지 요청은 모두 접근 가능
                .and()
                .oauth2Login()
                .successHandler(configSuccessHandler).loginPage("/api/v1/user/token/expired")
                .userInfoEndpoint().userService(principalOauth2UserService);
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}


