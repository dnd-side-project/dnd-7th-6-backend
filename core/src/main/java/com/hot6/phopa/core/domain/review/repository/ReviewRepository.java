package com.hot6.phopa.core.domain.review.repository;

import com.hot6.phopa.core.common.model.type.Status;
import com.hot6.phopa.core.domain.review.model.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long>, ReviewCustomRepository {

    List<ReviewEntity> findAllByUserIdAndStatus(Long userId, Status status);
}