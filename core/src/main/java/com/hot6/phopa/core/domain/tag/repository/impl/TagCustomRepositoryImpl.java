package com.hot6.phopa.core.domain.tag.repository.impl;

import com.hot6.phopa.core.domain.photobooth.model.entity.QPhotoBoothEntity;
import com.hot6.phopa.core.domain.review.model.entity.QReviewEntity;
import com.hot6.phopa.core.domain.review.model.entity.QReviewTagEntity;
import com.hot6.phopa.core.domain.tag.model.entity.QTagEntity;
import com.hot6.phopa.core.domain.tag.model.entity.TagEntity;
import com.hot6.phopa.core.domain.tag.repository.TagCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static com.hot6.phopa.core.domain.photobooth.model.entity.QPhotoBoothEntity.photoBoothEntity;
import static com.hot6.phopa.core.domain.review.model.entity.QReviewEntity.reviewEntity;
import static com.hot6.phopa.core.domain.review.model.entity.QReviewTagEntity.reviewTagEntity;
import static com.hot6.phopa.core.domain.tag.model.entity.QTagEntity.tagEntity;

public class TagCustomRepositoryImpl extends QuerydslRepositorySupport implements TagCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public TagCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(TagEntity.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }
    @Override
    public List<TagEntity> findByPhotoBoothId(Long photoBoothId) {
        return from(tagEntity)
                .join(tagEntity.reviewTagSet, reviewTagEntity).fetchJoin()
                .join(reviewTagEntity.review, reviewEntity).fetchJoin()
                .join(reviewEntity.photoBooth, photoBoothEntity).fetchJoin()
                .where(photoBoothEntity.id.eq(photoBoothId))
                .distinct()
                .fetch();
    }
}
