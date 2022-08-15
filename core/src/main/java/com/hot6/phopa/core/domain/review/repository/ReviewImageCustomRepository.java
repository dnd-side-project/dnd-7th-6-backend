package com.hot6.phopa.core.domain.review.repository;

import com.hot6.phopa.core.common.model.dto.PageableParam;
import com.hot6.phopa.core.domain.review.model.entity.ReviewImageEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ReviewImageCustomRepository {
    List<ReviewImageEntity> findByPhotoBoothIdAndLimit(Long photoBoothId, int limitSize);

    Page<ReviewImageEntity> findAllByPhotoBoothId(Long photoBoothId, PageableParam pageable);
}
