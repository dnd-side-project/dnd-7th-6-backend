package com.hot6.phopa.api.domain.user.controller;

import com.hot6.phopa.api.domain.user.model.dto.UserApiDTO.UserLikeResponse;
import com.hot6.phopa.api.domain.user.model.dto.UserApiDTO.UserListResponse;
import com.hot6.phopa.api.domain.user.service.UserApiService;
import com.hot6.phopa.core.domain.user.model.dto.UserDTO;
import com.hot6.phopa.core.security.config.PrincipleDetail;
import com.hot6.phopa.core.security.jwt.JwtToken;
import com.hot6.phopa.core.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Positive;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/user", produces = "application/json")
@RequiredArgsConstructor
public class UserController {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserApiService userApiService;
    @GetMapping("/like")
    public UserLikeResponse getLikeResponse(
    ) {
        return userApiService.getLikeResponse();
    }

    @GetMapping("/list")
    public UserListResponse getUserListResponse(
    ) {
        return userApiService.getUserListResponse();
    }

    @GetMapping("/token/expired")
    public String auth() {
        throw new RuntimeException();
    }

    @GetMapping("/token/refresh")
    public String refreshAuth(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader("Refresh");

        if (token != null && jwtTokenProvider.verifyToken(token)) {
            String email = jwtTokenProvider.getUid(token);
            JwtToken newToken = jwtTokenProvider.generateToken(email);

            response.addHeader("Auth", newToken.getToken());
            response.addHeader("Refresh", newToken.getRefreshToken());
            response.setContentType("application/json;charset=UTF-8");

            return "HAPPY NEW TOKEN";
        }

        throw new RuntimeException();
    }
}
