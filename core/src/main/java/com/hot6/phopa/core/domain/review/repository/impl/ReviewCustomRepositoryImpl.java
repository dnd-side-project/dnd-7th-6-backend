package com.hot6.phopa.core.domain.review.repository.impl;

import com.hot6.phopa.core.domain.photobooth.model.entity.QPhotoBoothEntity;
import com.hot6.phopa.core.domain.review.model.entity.QReviewEntity;
import com.hot6.phopa.core.domain.review.model.entity.QReviewTagEntity;
import com.hot6.phopa.core.domain.review.model.entity.QTagEntity;
import com.hot6.phopa.core.domain.review.model.entity.ReviewEntity;
import com.hot6.phopa.core.domain.review.repository.ReviewCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.hot6.phopa.core.domain.photobooth.model.entity.QPhotoBoothEntity.photoBoothEntity;
import static com.hot6.phopa.core.domain.review.model.entity.QReviewEntity.reviewEntity;
import static com.hot6.phopa.core.domain.review.model.entity.QReviewTagEntity.reviewTagEntity;
import static com.hot6.phopa.core.domain.review.model.entity.QTagEntity.tagEntity;

@Repository
public class ReviewCustomRepositoryImpl extends QuerydslRepositorySupport implements ReviewCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public ReviewCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(ReviewEntity.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<ReviewEntity> findByPhotoBoothId(long photoBoothId) {
        return from(reviewEntity)
                .join(reviewEntity.photoBooth, photoBoothEntity).fetchJoin()
                .join(reviewEntity.reviewTagList, reviewTagEntity).fetchJoin()
                .join(reviewTagEntity.tag, tagEntity).fetchJoin()
                .where(photoBoothEntity.id.eq(photoBoothId))
                .fetch();
    }
}