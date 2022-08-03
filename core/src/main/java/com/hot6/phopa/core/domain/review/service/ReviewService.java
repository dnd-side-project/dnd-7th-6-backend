package com.hot6.phopa.core.domain.review.service;

import com.hot6.phopa.core.common.exception.ApplicationErrorType;
import com.hot6.phopa.core.common.exception.SilentApplicationErrorException;
import com.hot6.phopa.core.common.model.dto.PageableParam;
import com.hot6.phopa.core.domain.review.model.entity.ReviewEntity;
import com.hot6.phopa.core.domain.review.model.entity.ReviewLikeEntity;
import com.hot6.phopa.core.domain.review.repository.ReviewLikeRepository;
import com.hot6.phopa.core.domain.review.repository.ReviewRepository;
import com.hot6.phopa.core.domain.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    private final TagRepository tagRepository;

    private final ReviewLikeRepository reviewLikeRepository;

    @Transactional(readOnly = true)
    public Page<ReviewEntity> getReview(long photoBoothId, PageableParam pageable) {
        return reviewRepository.findByPhotoBoothId(photoBoothId, pageable);
    }

    public ReviewEntity createReview(ReviewEntity reviewEntity) {
        return reviewRepository.save(reviewEntity);
    }

    @Transactional(readOnly = true)
    public ReviewEntity getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(() -> new SilentApplicationErrorException(ApplicationErrorType.COULDNT_FIND_ANY_DATA));
    }

    public ReviewLikeEntity createReviewLikeEntity(ReviewLikeEntity reviewLikeEntity) {
        return reviewLikeRepository.save(reviewLikeEntity);
    }

    public void deleteReviewLike(ReviewLikeEntity reviewLikeEntity) {
        reviewLikeRepository.delete(reviewLikeEntity);
    }

    @Transactional(readOnly = true)
    public ReviewLikeEntity getReviewLikeByReviewIdAndUserId(Long reviewId, Long userId) {
        return reviewLikeRepository.findOneByReviewIdAndUserId(reviewId, userId);
    }
}
