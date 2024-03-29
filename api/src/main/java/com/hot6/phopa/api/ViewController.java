package com.hot6.phopa.api;

import com.hot6.phopa.core.common.model.entity.CacheKeyEntity;
import com.hot6.phopa.core.common.model.type.CacheType;
import com.hot6.phopa.core.security.config.PrincipleDetail;
import com.hot6.phopa.core.service.RedisCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("")
@Controller
@RequiredArgsConstructor
public class ViewController {
    private final RedisCacheService cacheService;
    @GetMapping("/api/v1/login")
    public String loginPage(){
        return "loginForm";
    }

    @GetMapping("/test/login")
    public @ResponseBody String testLogin(Authentication authentication,
                                          @AuthenticationPrincipal PrincipleDetail principleDetail){

        return "세션 정보 확인 : " + cacheService.get(CacheKeyEntity.valueKey(CacheType.User, principleDetail.getName()), () -> principleDetail.getUsername(), String.class);
    }
}
