package com.hot6.phopa.core.domain.review.repository;

import com.hot6.phopa.core.domain.review.model.entity.ReviewTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewTagRepository extends JpaRepository<ReviewTagEntity, Long> {
}