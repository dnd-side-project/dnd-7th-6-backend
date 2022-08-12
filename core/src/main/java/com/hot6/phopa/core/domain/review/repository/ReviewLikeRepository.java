package com.hot6.phopa.core.domain.review.repository;

import com.hot6.phopa.core.domain.review.model.entity.ReviewLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewLikeRepository extends JpaRepository<ReviewLikeEntity, Long> {
    ReviewLikeEntity findOneByReviewIdAndUserId(Long reviewId, Long userId);

    @Query("SELECT reviewLike FROM ReviewLikeEntity reviewLike where reviewLike.user.id = :userId and reviewLike.review.id in :reviewIdList")
    List<ReviewLikeEntity> findAllByReviewIdsAndUserId(@Param("reviewIdList") List<Long> reviewIdList, @Param("userId") Long userId);
}