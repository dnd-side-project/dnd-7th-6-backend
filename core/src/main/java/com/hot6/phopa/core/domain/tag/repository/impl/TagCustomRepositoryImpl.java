package com.hot6.phopa.core.domain.tag.repository.impl;

import com.hot6.phopa.core.common.model.dto.PageableParam;
import com.hot6.phopa.core.domain.photobooth.model.entity.QPhotoBoothEntity;
import com.hot6.phopa.core.domain.review.model.entity.QReviewEntity;
import com.hot6.phopa.core.domain.review.model.entity.QReviewTagEntity;
import com.hot6.phopa.core.domain.tag.enumeration.TagType;
import com.hot6.phopa.core.domain.tag.model.entity.QTagEntity;
import com.hot6.phopa.core.domain.tag.model.entity.TagEntity;
import com.hot6.phopa.core.domain.tag.repository.TagCustomRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
    public List<TagEntity> findByPhotoBoothId(Long photoBoothId, List<TagType> tagTypeList) {
        return from(tagEntity)
                .join(tagEntity.reviewTagSet, reviewTagEntity).fetchJoin()
                .join(reviewTagEntity.review, reviewEntity).fetchJoin()
                .where(reviewTagEntity.photoBoothId.eq(photoBoothId))
                .where(buildPredicate(null, tagTypeList))
                .distinct()
                .fetch();
    }

    @Override
    public Page<TagEntity> getTagByKeyword(String keyword, TagType tagType, PageableParam pageable) {
        QueryResults result = jpaQueryFactory
                .selectFrom(tagEntity)
                .where(buildPredicate(tagType, null))
                .where(tagEntity.keyword.contains(keyword))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetchResults();
        return new PageImpl<>(result.getResults(), PageRequest.of(pageable.getPage(), pageable.getPageSize()), result.getTotal());
    }

    @Override
    public List<TagEntity> findAllByTagTypeList(List<TagType> tagTypeList, Boolean onlyKeyword){
        return from(tagEntity)
                .where(tagEntity.tagType.in(tagTypeList))
                .where(buildKeywordPredicate(onlyKeyword))
                .orderBy(tagEntity.reviewCount.desc())
                .fetch();
    }

    private Predicate buildKeywordPredicate(Boolean onlyKeyword) {
        BooleanBuilder builder = new BooleanBuilder();
        if(Boolean.TRUE.equals(onlyKeyword)){
            builder.and(tagEntity.tagType.eq(TagType.BRAND).or(tagEntity.title.eq(tagEntity.keyword).not()));
        }
        return builder.getValue();
    }


    private Predicate buildPredicate(TagType tagType, List<TagType> tagTypeList){
        BooleanBuilder builder = new BooleanBuilder();
        if(tagType != null){
            builder.and(tagEntity.tagType.eq(tagType));
        }
        if(CollectionUtils.isNotEmpty(tagTypeList)){
            builder.and(tagEntity.tagType.in(tagTypeList));
        }
        return builder.getValue();
    }
}
