package com.hot6.phopa.core.domain.map.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "example", url = "https")
public interface ExampleClient {
        @GetMapping("/status/")
        void status(@PathVariable int status);
}