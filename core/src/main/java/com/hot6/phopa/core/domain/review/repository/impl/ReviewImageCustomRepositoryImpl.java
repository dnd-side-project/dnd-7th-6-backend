package com.hot6.phopa.core.domain.review.repository.impl;

import com.hot6.phopa.core.domain.photobooth.model.entity.QPhotoBoothEntity;
import com.hot6.phopa.core.domain.review.model.entity.QReviewEntity;
import com.hot6.phopa.core.domain.review.model.entity.QReviewImageEntity;
import com.hot6.phopa.core.domain.review.model.entity.ReviewImageEntity;
import com.hot6.phopa.core.domain.review.repository.ReviewImageCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.hot6.phopa.core.domain.photobooth.model.entity.QPhotoBoothEntity.photoBoothEntity;
import static com.hot6.phopa.core.domain.review.model.entity.QReviewEntity.reviewEntity;
import static com.hot6.phopa.core.domain.review.model.entity.QReviewImageEntity.reviewImageEntity;

@Repository
public class ReviewImageCustomRepositoryImpl extends QuerydslRepositorySupport implements ReviewImageCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;
    public ReviewImageCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(ReviewImageEntity.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<ReviewImageEntity> findByPhotoBoothIdAndLimit(Long photoBoothId, int limitSize) {
        return from(reviewImageEntity)
                .join(reviewImageEntity.review, reviewEntity).fetchJoin()
                .join(reviewEntity.photoBooth, photoBoothEntity).fetchJoin()
                .where(photoBoothEntity.id.eq(photoBoothId))
                .limit(limitSize)
                .fetch();
    }
}
