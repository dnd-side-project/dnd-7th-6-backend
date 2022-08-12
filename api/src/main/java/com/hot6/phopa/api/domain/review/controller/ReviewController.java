package com.hot6.phopa.api.domain.review.controller;

import com.hot6.phopa.api.domain.community.model.dto.PostApiDTO;
import com.hot6.phopa.api.domain.review.model.dto.ReviewApiDTO;
import com.hot6.phopa.api.domain.review.model.dto.ReviewApiDTO.ReviewApiResponse;
import com.hot6.phopa.api.domain.review.model.dto.ReviewApiDTO.ReviewCreateRequest;
import com.hot6.phopa.api.domain.review.model.dto.ReviewApiDTO.ReviewFormResponse;
import com.hot6.phopa.api.domain.review.model.dto.ReviewApiDTO.ReviewUpdateRequest;
import com.hot6.phopa.api.domain.review.service.ReviewApiService;
import com.hot6.phopa.core.common.model.dto.PageableParam;
import com.hot6.phopa.core.common.model.dto.PageableResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Positive;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/review", produces = "application/json")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewApiService reviewService;

    @GetMapping
    public PageableResponse<ReviewApiResponse> getPhotoBoothReview(
            @RequestParam @Positive long photoBoothId,
            PageableParam pageable
    ) {
        return reviewService.getReview(photoBoothId, pageable);
    }


    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ReviewApiResponse createPhotoBoothReview(
            @RequestPart ReviewCreateRequest reviewCreateRequest,
            @RequestPart List<MultipartFile> reviewImageList
    ) {
        return reviewService.createReview(reviewCreateRequest, reviewImageList);
    }

    @PostMapping("/{reviewId}/like")
    public void like(
           @PathVariable Long reviewId
    ){
        reviewService.like(reviewId);
    }

    @GetMapping("/form")
    public ReviewFormResponse getFormData(){
        return reviewService.getFormData();
    }

    @DeleteMapping("/{reviewId}")
    public void inactivePost(
            @PathVariable @Positive Long reviewId
    ) {
        reviewService.inactiveReview(reviewId);
    }

    @GetMapping("/{reviewId}")
    public ReviewApiResponse getReview(
            @PathVariable @Positive Long reviewId
    ){
        return reviewService.getReview(reviewId);
    }

    @PatchMapping("/{reviewId}")
    public ReviewApiResponse modifyPost(
            @PathVariable @Positive Long reviewId,
            @RequestPart ReviewUpdateRequest reviewUpdateRequest,
            @RequestPart(required = false) List<MultipartFile> reviewImageList
    ) {
        return reviewService.modifyReview(reviewId, reviewUpdateRequest, reviewImageList);
    }

}
