package com.hot6.phopa.core.security.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hot6.phopa.core.common.exception.ApplicationErrorType;
import com.hot6.phopa.core.common.exception.SilentApplicationErrorException;
import com.hot6.phopa.core.domain.user.model.dto.UserDTO;
import com.hot6.phopa.core.domain.user.model.entity.UserEntity;
import com.hot6.phopa.core.domain.user.model.mapper.UserMapper;
import com.hot6.phopa.core.domain.user.service.UserService;
import com.hot6.phopa.core.domain.user.type.UserRole;
import com.hot6.phopa.core.domain.user.type.UserStatus;
import com.hot6.phopa.core.security.config.PrincipleDetail;
import com.hot6.phopa.core.security.jwt.JwtToken;
import com.hot6.phopa.core.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class ConfigSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final ObjectMapper objectMapper;
    private final UserMapper userMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        PrincipleDetail principleDetail = (PrincipleDetail) authentication.getPrincipal();
        UserEntity user = principleDetail.getUser();
        // 최초 로그인이라면 회원가입 처리를 한다.
        UserEntity userEntity = userService.getUser(user.getEmail());
        if (userEntity == null) {
            userEntity = userService.createUser(user);
            userEntity.updateName("photalks_user_" + userEntity.getId());
        } else if (UserStatus.INACTIVE.equals(userEntity.getStatus())) {
            throw new SilentApplicationErrorException(ApplicationErrorType.INACTIVE_USER, "탈퇴한 회원입니다.");
        }
        Authentication auth = getAuthentication(userMapper.toDto(userEntity));
        SecurityContextHolder.getContext().setAuthentication(auth);

        JwtToken token = jwtTokenProvider.generateToken(user.getEmail());

        writeTokenResponse(response, token);
    }

    private void writeTokenResponse(HttpServletResponse response, JwtToken token)
            throws IOException {
        response.setContentType("text/html;charset=UTF-8");

        response.addHeader("Auth", token.getToken());
        response.addHeader("Refresh", token.getRefreshToken());
        response.setContentType("application/json;charset=UTF-8");

        var writer = response.getWriter();
        writer.println(objectMapper.writeValueAsString(token));
        writer.flush();
    }

    private Authentication getAuthentication(UserDTO user) {
        return new UsernamePasswordAuthenticationToken(user, "",
                Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
