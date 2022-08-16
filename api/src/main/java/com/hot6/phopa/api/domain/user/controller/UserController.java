package com.hot6.phopa.api.domain.user.controller;

import com.hot6.phopa.api.domain.user.model.dto.UserApiDTO;
import com.hot6.phopa.api.domain.user.model.dto.UserApiDTO.*;
import com.hot6.phopa.api.domain.user.service.UserApiService;
import com.hot6.phopa.core.common.exception.ApplicationErrorType;
import com.hot6.phopa.core.common.exception.SilentApplicationErrorException;
import com.hot6.phopa.core.security.jwt.JwtToken;
import com.hot6.phopa.core.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/user", produces = "application/json")
@RequiredArgsConstructor
public class UserController {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserApiService userApiService;

    @GetMapping
    public UserApiResponse getUserDto(){
        return userApiService.getUserDto();
    }
    
    @GetMapping("/like/image")
    public List<UserLikeImageResponse> getLikeImageResponse(
    ) {
        return userApiService.getLikeImageResponse();
    }

    @GetMapping("/like/photo-booth")
    public List<UserLikePhotoBoothResponse> getLikePhotoBoothResponse(
    ) {
        return userApiService.getLikePhotoBoothResponse();
    }

    @GetMapping("/list")
    public UserListResponse getUserListResponse(
    ) {
        return userApiService.getUserListResponse();
    }

    @PatchMapping("/name")
    public UserApiResponse updateName(
            @RequestBody UserNameUpdateRequest userNameUpdateRequest
            ) {
        return userApiService.updateName(userNameUpdateRequest);
    }

    @GetMapping("/name-valid")
    public ResponseEntity vaildeName(
            @RequestParam String name
    ) {
        userApiService.vaildName(name);
        return ResponseEntity.ok("pass valid check");
    }


    @GetMapping("/token/refresh")
    public String refreshAuth(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader("refresh-token");

        if (token != null && jwtTokenProvider.verifyToken(token)) {
            String email = jwtTokenProvider.getUid(token);
            JwtToken newToken = jwtTokenProvider.generateToken(email);

            response.addHeader("access-token", newToken.getToken());
            response.addHeader("refresh-token", newToken.getRefreshToken());
            response.setContentType("application/json;charset=UTF-8");

            return "HAPPY NEW TOKEN";
        }
        throw new SilentApplicationErrorException(ApplicationErrorType.EXPIRED_REFRESH_TOKEN);
    }

    @DeleteMapping
    public void inactiveUser(){
        userApiService.inactiveUser();
    }

    @GetMapping("/login")
    public JwtToken login(
            UserLoginRequest userLoginRequest
    ){
        return userApiService.login(userLoginRequest);
    }
}
