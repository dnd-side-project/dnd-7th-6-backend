package com.hot6.phopa.api.domain.photobooth.controller;

import com.hot6.phopa.api.domain.photobooth.model.dto.PhotoBoothApiDTO.PhotoBoothApiResponse;
import com.hot6.phopa.api.domain.photobooth.service.PhotoBoothApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/photo-booth", produces = "application/json")
@RequiredArgsConstructor
public class PhotoBoothController {

    private final PhotoBoothApiService photoBoothService;

    @GetMapping("/near-by")
    public List<PhotoBoothApiResponse> getPhotoBoothNearByUserGeo(
            @RequestParam Double latitude,
            @RequestParam Double longitude,
            @RequestParam Double distance,
            @RequestParam(required = false) Set<Long> tagIdSet
            ){
        return photoBoothService.getPhotoBoothNearByUserGeo(latitude, longitude, distance, tagIdSet);
    }

    @GetMapping("/kakao-map-test")
    public List<PhotoBoothApiResponse> kakaoMapTest(
            @RequestParam String keyword,
            @RequestParam(required = false) Double latitude,
            @RequestParam(required = false) Double longitude,
            @RequestParam(required = false) Double distance
    ){
        return photoBoothService.kakaoMapTest(keyword, latitude, longitude, distance);
    }
}
