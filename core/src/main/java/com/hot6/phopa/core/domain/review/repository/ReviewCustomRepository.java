package com.hot6.phopa.core.domain.review.repository;

import com.hot6.phopa.core.common.model.dto.PageableParam;
import com.hot6.phopa.core.common.model.type.Status;
import com.hot6.phopa.core.domain.review.model.entity.ReviewEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewCustomRepository {
    Page<ReviewEntity> findByPhotoBoothId(long photoBoothId, PageableParam pageable);

    ReviewEntity findByIdAndStatus(Long reviewId, Status status);
}