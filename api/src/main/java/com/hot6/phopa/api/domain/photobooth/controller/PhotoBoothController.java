package com.hot6.phopa.api.domain.photobooth.controller;

import com.hot6.phopa.api.domain.photobooth.model.dto.PhotoBoothApiDTO;
import com.hot6.phopa.api.domain.photobooth.model.dto.PhotoBoothApiDTO.PhotoBoothApiResponse;
import com.hot6.phopa.api.domain.photobooth.model.dto.PhotoBoothApiDTO.PhotoBoothDetailResponse;
import com.hot6.phopa.api.domain.photobooth.model.dto.PhotoBoothApiDTO.PhotoBoothFilterFormResponse;
import com.hot6.phopa.api.domain.photobooth.model.dto.PhotoBoothApiDTO.PhotoBoothWithTagResponse;
import com.hot6.phopa.api.domain.photobooth.service.PhotoBoothApiService;
import com.hot6.phopa.core.common.model.dto.PageableParam;
import com.hot6.phopa.core.common.model.dto.PageableResponse;
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
    public PageableResponse<PhotoBoothWithTagResponse> getPhotoBoothNearByUserGeo(
            @RequestParam Double latitude,
            @RequestParam Double longitude,
            @RequestParam Double distance,
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) Set<Long> tagIdSet,
            PageableParam pageable
            ){
        return photoBoothService.getPhotoBoothNearByUserGeo(latitude, longitude, distance, status, tagIdSet, pageable);
    }

    @GetMapping("/{photoBoothId}")
    public PhotoBoothDetailResponse getPhotoBooth(
            @PathVariable @Positive Long photoBoothId,
            @RequestParam(required = false) Double latitude,
            @RequestParam(required = false) Double longitude
    ){
        return photoBoothService.getPhotoBooth(photoBoothId, latitude, longitude);
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

    @PostMapping("/{photoBoothId}/like")
    public void like(
            @PathVariable Long photoBoothId
    ){
        photoBoothService.like(photoBoothId);
    }

    @GetMapping("/filter")
    public PhotoBoothFilterFormResponse getFilterData(){
        return photoBoothService.getFilterData();
    }
}
