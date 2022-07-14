package com.hot6.phopa.core.domain.review.service;

import com.hot6.phopa.core.domain.review.model.entity.ReviewEntity;
import com.hot6.phopa.core.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    public List<ReviewEntity> getReview(long photoBoothId) {
        return reviewRepository.findByPhotoBoothId(photoBoothId);
    }
}
