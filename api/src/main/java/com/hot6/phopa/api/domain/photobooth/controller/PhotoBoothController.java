package com.hot6.phopa.api.domain.photobooth.controller;

import com.hot6.phopa.api.domain.photobooth.model.dto.PhotoBoothApiDTO.PhotoBoothApiResponse;
import com.hot6.phopa.api.domain.photobooth.model.dto.PhotoBoothApiDTO.PhotoBoothFormResponse;
import com.hot6.phopa.api.domain.photobooth.model.dto.PhotoBoothApiDTO.PhotoBoothWithTagResponse;
import com.hot6.phopa.api.domain.photobooth.service.PhotoBoothApiService;
import com.hot6.phopa.core.common.model.type.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
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
            @RequestParam Status status,
            @RequestParam(required = false) Set<Long> tagIdSet,
            @RequestParam @Positive int pageSize,
            @RequestParam @Positive int pageNumber
            ){
        return photoBoothService.getPhotoBoothNearByUserGeo(latitude, longitude, distance, status, tagIdSet, pageSize, pageNumber);
    }

    @GetMapping("/{photoBoothId}")
    public PhotoBoothWithTagResponse getPhotoBooth(
            @PathVariable @Positive Long photoBoothId
    ){
        return photoBoothService.getPhotoBooth(photoBoothId);
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

    @PostMapping("/{photoBoothId}/like/{userId}")
    public void like(
            @PathVariable Long photoBoothId,
            @PathVariable Long userId
    ){
        photoBoothService.like(photoBoothId, userId);
    }

    @GetMapping("/form")
    public PhotoBoothFormResponse getFormData(){
        return photoBoothService.getFormData();
    }
}
