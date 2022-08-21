package com.hot6.phopa.core.domain.review.service;

import com.hot6.phopa.core.common.exception.ApplicationErrorType;
import com.hot6.phopa.core.common.exception.SilentApplicationErrorException;
import com.hot6.phopa.core.common.model.dto.PageableParam;
import com.hot6.phopa.core.common.model.type.Status;
import com.hot6.phopa.core.domain.review.model.entity.ReviewEntity;
import com.hot6.phopa.core.domain.review.model.entity.ReviewImageEntity;
import com.hot6.phopa.core.domain.review.model.entity.ReviewImageLikeEntity;
import com.hot6.phopa.core.domain.review.repository.ReviewImageLikeRepository;
import com.hot6.phopa.core.domain.review.repository.ReviewImageRepository;
import com.hot6.phopa.core.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    private final ReviewImageLikeRepository reviewImageLikeRepository;

    private final ReviewImageRepository reviewImageRepository;

    @Transactional(readOnly = true)
    public Page<ReviewEntity> getReview(long photoBoothId, PageableParam pageable) {
        return reviewRepository.findByPhotoBoothId(photoBoothId, pageable);
    }

    public ReviewEntity createReview(ReviewEntity reviewEntity) {
        return reviewRepository.save(reviewEntity);
    }

    @Transactional(readOnly = true)
    public ReviewEntity getReviewById(Long reviewId) {
        return Optional.ofNullable(reviewRepository.findByIdAndStatus(reviewId, Status.ACTIVE)).orElseThrow(() -> new SilentApplicationErrorException(ApplicationErrorType.COULDNT_FIND_ANY_DATA));
    }

    public ReviewImageLikeEntity createReviewImageLikeEntity(ReviewImageLikeEntity reviewImageLikeEntity) {
        return reviewImageLikeRepository.save(reviewImageLikeEntity);
    }

    public void deleteReviewImageLike(ReviewImageLikeEntity reviewImageLikeEntity) {
        reviewImageLikeRepository.delete(reviewImageLikeEntity);
    }


    @Transactional(readOnly = true)
    public List<ReviewImageLikeEntity> getReviewImageLikeByReviewIdsAndUserId(List<Long> reviewIdList, Long userId) {
        return reviewImageLikeRepository.findAllByReviewImageIdsAndUserId(reviewIdList, userId);
    }
    @Transactional(readOnly = true)

    public List<ReviewEntity> findAllByUserId(Long userId) {
        return reviewRepository.findAllByUserIdAndStatus(userId, Status.ACTIVE);
    }
    @Transactional(readOnly = true)
    public List<ReviewImageEntity> getReviewImageByPhotoBoothId(Long photoBoothId, int limitSize) {
        return reviewImageRepository.findByPhotoBoothIdAndLimit(photoBoothId, limitSize);
    }
    @Transactional(readOnly = true)
    public ReviewImageEntity getReviewImageById(Long reviewImageId) {
        return reviewImageRepository.findById(reviewImageId).orElseThrow(() -> new SilentApplicationErrorException(ApplicationErrorType.COULDNT_FIND_ANY_DATA));
    }

    @Transactional(readOnly = true)
    public ReviewImageLikeEntity getReviewImageLikeByReviewImageIdAndUserId(Long reviewImageId, Long userId) {
        return reviewImageLikeRepository.findOneByReviewImageIdAndUserId(reviewImageId, userId);
    }

    @Transactional(readOnly = true)
    public List<ReviewImageLikeEntity> getReviewImageLikeByUserId(Long userId) {
        return reviewImageLikeRepository.findAllByUserId(userId);
    }
    @Transactional(readOnly = true)
    public Page<ReviewImageEntity> getReviewImageByPhotoBoothId(Long photoBoothId, PageableParam pageable) {
        return reviewImageRepository.findAllByPhotoBoothId(photoBoothId, pageable);
    }
    @Transactional(readOnly = true)
    public List<ReviewImageEntity> getReviewImageByUserId(Long userId) {
        return reviewImageRepository.findAllByUserLike(userId);
    }
}
