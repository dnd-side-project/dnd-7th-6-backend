package com.hot6.phopa.core.domain.review.repository;

import com.hot6.phopa.core.domain.review.model.entity.ReviewImageEntity;

import java.util.List;

public interface ReviewImageCustomRepository {
    List<ReviewImageEntity> findByPhotoBoothIdAndLimit(Long photoBoothId, int limitSize);
}
