package com.hot6.phopa.core.domain.review.repository;

import com.hot6.phopa.core.domain.review.model.entity.ReviewImageLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewImageLikeRepository extends JpaRepository<ReviewImageLikeEntity, Long> {
    ReviewImageLikeEntity findOneByReviewImageIdAndUserId(Long reviewImageId, Long userId);

    @Query("SELECT reviewImageLike FROM ReviewImageLikeEntity reviewImageLike where reviewImageLike.user.id = :userId and reviewImageLike.reviewImage.id in :reviewImageIdList")
    List<ReviewImageLikeEntity> findAllByReviewImageIdsAndUserId(@Param("reviewImageIdList") List<Long> reviewImageIdList, @Param("userId") Long userId);
}