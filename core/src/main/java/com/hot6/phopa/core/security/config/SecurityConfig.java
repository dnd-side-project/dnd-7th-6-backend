package com.hot6.phopa.core.security.config;

import com.hot6.phopa.core.domain.user.type.UserRole;
import com.hot6.phopa.core.security.jwt.JwtAuthenticationFilter;
import com.hot6.phopa.core.security.jwt.JwtTokenProvider;
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
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                // rest api이므로 기본설정 안함. 기본설정은 비인증 시 로그인 폼 화면으로 리다이렉트 된다.
                .csrf().disable()
                // rest api 이므로 csrf 보안이 필요 없음. disable
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // jwt token으로 생성하므로 세션은 필요 없으므로 생성 안함
                .and()
                .authorizeRequests() // 다음 리퀘스트에 대한 사용권한 체크
                // 가입 및 인증 주소는 누구나 접근 가능
                .antMatchers("/api/v1/user/**").hasAnyRole(UserRole.USER.getAuthority())
                // user 관련 api는 회원만 접근 가능
                .antMatchers(HttpMethod.POST, "/**").hasAnyRole(UserRole.USER.getAuthority())
                // 나머지 POST 메소드는 회원만 접근 가능
                .anyRequest().permitAll()
                // 그 외 나머지 요청은 모두 접근 가능
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                // jwt token 필터를 id/password 인증 필터 전에 넣는다.
                .oauth2Login();
    }
}


