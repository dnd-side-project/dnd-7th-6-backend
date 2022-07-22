package com.hot6.phopa.core.domain.review.repository;

import com.hot6.phopa.core.domain.review.model.entity.ReviewLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewLikeRepository extends JpaRepository<ReviewLikeEntity, Long> {
}