package com.hot6.phopa.core.domain.review.repository.impl;

import com.hot6.phopa.core.common.model.dto.PageableParam;
import com.hot6.phopa.core.common.model.type.Status;
import com.hot6.phopa.core.domain.review.model.entity.ReviewEntity;
import com.hot6.phopa.core.domain.review.repository.ReviewCustomRepository;
import com.hot6.phopa.core.domain.tag.model.entity.QTagEntity;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import static com.hot6.phopa.core.domain.photobooth.model.entity.QPhotoBoothEntity.photoBoothEntity;
import static com.hot6.phopa.core.domain.review.model.entity.QReviewEntity.reviewEntity;
import static com.hot6.phopa.core.domain.review.model.entity.QReviewImageEntity.reviewImageEntity;
import static com.hot6.phopa.core.domain.review.model.entity.QReviewTagEntity.reviewTagEntity;

@Repository
public class ReviewCustomRepositoryImpl extends QuerydslRepositorySupport implements ReviewCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public ReviewCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(ReviewEntity.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Page<ReviewEntity> findByPhotoBoothId(long photoBoothId, PageableParam pageable) {
        QueryResults<ReviewEntity> result = jpaQueryFactory
                .selectFrom(reviewEntity)
                .join(reviewEntity.photoBooth, photoBoothEntity).fetchJoin()
                .leftJoin(reviewEntity.reviewImageSet, reviewImageEntity).fetchJoin()
                .leftJoin(reviewEntity.reviewTagSet, reviewTagEntity).fetchJoin()
                .leftJoin(reviewTagEntity.tag, QTagEntity.tagEntity).fetchJoin()
                .where(photoBoothEntity.id.eq(photoBoothId).and(photoBoothEntity.status.eq(Status.ACTIVE)).and(reviewEntity.status.eq(Status.ACTIVE)))
                .orderBy(reviewEntity.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .distinct().fetchResults();
        return new PageImpl<>(result.getResults(), PageRequest.of(pageable.getPage(), pageable.getPageSize()), result.getTotal());
    }

    @Override
    public ReviewEntity findByIdAndStatus(Long reviewId, Status status) {
        return from(reviewEntity)
                .join(reviewEntity.photoBooth, photoBoothEntity).fetchJoin()
                .where(reviewEntity.id.eq(reviewId).and(reviewEntity.status.eq(status)))
                .fetchFirst();
    }
}