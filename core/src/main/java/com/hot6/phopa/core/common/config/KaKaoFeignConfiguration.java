package com.hot6.phopa.core.common.config;

import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;

@RequiredArgsConstructor
public class KaKaoFeignConfiguration {

    @Value("${kakao.map.api-key}")
    private String apiKey;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("Content-Type", MediaType.APPLICATION_JSON_VALUE);
            requestTemplate.header("Authorization", apiKey);
        };
    }
}