package com.hot6.phopa.api.domain.review.controller;

import com.hot6.phopa.api.domain.photobooth.service.PhotoBoothApiService;
import com.hot6.phopa.api.domain.review.model.dto.ReviewApiDTO;
import com.hot6.phopa.api.domain.review.model.dto.ReviewApiDTO.ReviewApiResponse;
import com.hot6.phopa.api.domain.review.service.ReviewApiService;
import com.hot6.phopa.core.domain.photobooth.model.dto.PhotoBoothDTO;
import com.hot6.phopa.core.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/review", produces = "application/json")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewApiService reviewService;

    @GetMapping
    public List<ReviewApiResponse> getPhotoBoothReview(
            @RequestParam @Positive long photoBoothId
    ){
        return reviewService.getReview(photoBoothId);
    }
}
