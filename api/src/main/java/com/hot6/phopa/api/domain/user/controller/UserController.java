package com.hot6.phopa.api.domain.user.controller;

import com.hot6.phopa.api.domain.user.model.dto.UserApiDTO.UserLikeResponse;
import com.hot6.phopa.api.domain.user.model.dto.UserApiDTO.UserListResponse;
import com.hot6.phopa.api.domain.user.service.UserApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/user", produces = "application/json")
@RequiredArgsConstructor
public class UserController {
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
}
