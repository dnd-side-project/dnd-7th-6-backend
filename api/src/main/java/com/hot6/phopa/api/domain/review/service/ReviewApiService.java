package com.hot6.phopa.api.domain.review.service;

import com.hot6.phopa.api.domain.review.model.dto.ReviewApiDTO;
import com.hot6.phopa.api.domain.review.model.dto.ReviewApiDTO.ReviewApiResponse;
import com.hot6.phopa.api.domain.review.model.mapper.ReviewApiMapper;
import com.hot6.phopa.core.domain.review.model.entity.ReviewEntity;
import com.hot6.phopa.core.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewApiService {

    private final ReviewService reviewService;

    private final ReviewApiMapper reviewMapper;
    public List<ReviewApiResponse> getReview(long photoBoothId) {
        List<ReviewEntity> reviewEntityList = reviewService.getReview(photoBoothId);
        return reviewMapper.toDtoList(reviewEntityList);
    }
}
