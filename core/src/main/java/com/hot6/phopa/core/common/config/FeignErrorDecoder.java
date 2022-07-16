package com.hot6.phopa.core.common.config;

import com.hot6.phopa.core.common.exception.ExternalApiException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        return new ExternalApiException(String.format("Response : %d - %s", response.status(), methodKey));
    }
}
