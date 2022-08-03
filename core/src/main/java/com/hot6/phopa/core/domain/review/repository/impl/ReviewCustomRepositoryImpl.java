package com.hot6.phopa.core.domain.review.repository.impl;

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

import java.util.List;

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
    public Page<ReviewEntity> findByPhotoBoothId(long photoBoothId, int pageSize, int pageNumber) {
        QueryResults<ReviewEntity> result = jpaQueryFactory
                .selectFrom(reviewEntity)
                .join(reviewEntity.photoBooth, photoBoothEntity).fetchJoin()
                .leftJoin(reviewEntity.reviewImageSet, reviewImageEntity).fetchJoin()
                .leftJoin(reviewEntity.reviewTagSet, reviewTagEntity).fetchJoin()
                .leftJoin(reviewTagEntity.tag, QTagEntity.tagEntity).fetchJoin()
                .where(photoBoothEntity.id.eq(photoBoothId))
                .offset(pageNumber)
                .limit(pageSize)
                .distinct().fetchResults();
        return new PageImpl<>(result.getResults(), PageRequest.of(pageSize, pageNumber), result.getTotal()) ;
    }
}