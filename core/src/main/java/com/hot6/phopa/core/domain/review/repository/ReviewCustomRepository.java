package com.hot6.phopa.core.domain.review.repository;

import com.hot6.phopa.core.domain.review.model.entity.ReviewEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewCustomRepository {
    Page<ReviewEntity> findByPhotoBoothId(long photoBoothId, int pageSize, int pageNumber);
}