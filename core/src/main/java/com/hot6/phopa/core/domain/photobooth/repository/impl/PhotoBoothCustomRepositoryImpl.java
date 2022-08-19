package com.hot6.phopa.core.domain.photobooth.repository.impl;

import com.hot6.phopa.core.common.model.dto.PageableParam;
import com.hot6.phopa.core.common.model.type.Status;
import com.hot6.phopa.core.common.utils.PointUtil;
import com.hot6.phopa.core.domain.photobooth.model.entity.PhotoBoothEntity;
import com.hot6.phopa.core.domain.photobooth.repository.PhotoBoothCustomRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

import static com.hot6.phopa.core.domain.photobooth.model.entity.QPhotoBoothEntity.photoBoothEntity;
import static com.hot6.phopa.core.domain.photobooth.model.entity.QPhotoBoothLikeEntity.photoBoothLikeEntity;
import static com.hot6.phopa.core.domain.review.model.entity.QReviewEntity.reviewEntity;
import static com.hot6.phopa.core.domain.review.model.entity.QReviewTagEntity.reviewTagEntity;
import static com.hot6.phopa.core.domain.tag.model.entity.QTagEntity.tagEntity;
import static com.hot6.phopa.core.domain.user.model.entity.QUserEntity.userEntity;

@Repository
public class PhotoBoothCustomRepositoryImpl extends QuerydslRepositorySupport implements PhotoBoothCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public PhotoBoothCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(PhotoBoothEntity.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }


    public List<PhotoBoothEntity> findByPhotoBoothId(Long photoBoothId) {
        return from(photoBoothEntity)
                .where(photoBoothEntity.id.eq(photoBoothId).and(photoBoothEntity.status.eq(Status.ACTIVE)))
                .fetch();
    }

    @Override
    public List<PhotoBoothEntity> findByPointSet(Set<PointUtil> crawlingPointSet) {
        return from(photoBoothEntity)
                .where(buildPointPredicate(crawlingPointSet))
                .where(photoBoothEntity.status.eq(Status.ACTIVE))
                .fetch();
    }

    private Predicate buildPointPredicate(Set<PointUtil> crawlingPointSet) {
        BooleanBuilder builder = new BooleanBuilder();
        for (PointUtil point : crawlingPointSet) {
            builder.or(photoBoothEntity.longitude.eq(point.getLongitude()).and(photoBoothEntity.latitude.eq(point.getLatitude())));
        }
        return builder.getValue();
    }

    @Override
    public List<PhotoBoothEntity> findAllByUserLike(Long userId) {
        return from(photoBoothEntity)
                .join(photoBoothEntity.photoBoothLikeSet, photoBoothLikeEntity).fetchJoin()
                .join(photoBoothLikeEntity.user, userEntity).fetchJoin()
                .where(userEntity.id.eq(userId).and(photoBoothEntity.status.eq(Status.ACTIVE)))
                .fetch();
    }

    @Override
    public PhotoBoothEntity findByIdWithTag(Long photoBoothId) {
        return from(photoBoothEntity)
                .leftJoin(photoBoothEntity.tag, tagEntity).fetchJoin()
                .leftJoin(photoBoothEntity.reviewSet, reviewEntity).fetchJoin()
                .leftJoin(reviewEntity.reviewTagSet, reviewTagEntity).fetchJoin()
                .where(photoBoothEntity.id.eq(photoBoothId).and(photoBoothEntity.status.eq(Status.ACTIVE)))
                .fetchOne();
    }

    @Override
    public Page<PhotoBoothEntity> findByPhotoBoothIdAndColumn(List<Long> photoBoothIdList, Status status, Set<Long> tagIdSet, PageableParam pageable) {
        List<PhotoBoothEntity> result = jpaQueryFactory.selectFrom(photoBoothEntity)
                .leftJoin(photoBoothEntity.reviewSet, reviewEntity)
                .leftJoin(reviewEntity.reviewTagSet, reviewTagEntity)
                .leftJoin(reviewTagEntity.tag, tagEntity)
                .where(photoBoothEntity.id.in(photoBoothIdList).and(photoBoothEntity.status.eq(Status.ACTIVE)))
                .where(buildPredicate(status, tagIdSet))
                .orderBy(orderByFieldList(photoBoothIdList))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .distinct()
                .fetch();
        long totalCount = from(photoBoothEntity)
                .leftJoin(photoBoothEntity.reviewSet, reviewEntity).fetchJoin()
                .leftJoin(reviewEntity.reviewTagSet, reviewTagEntity).fetchJoin()
                .leftJoin(reviewTagEntity.tag, tagEntity).fetchJoin()
                .where(photoBoothEntity.id.in(photoBoothIdList).and(photoBoothEntity.status.eq(Status.ACTIVE)))
                .where(buildPredicate(status, tagIdSet))
                .distinct()
                .fetch()
                .size();
        return new PageImpl<>(result, PageRequest.of(pageable.getPage(), pageable.getPageSize()), totalCount);
    }

    @Override
    public Integer getCountByPhotoBoothIdAndColumn(List<Long> photoBoothIdList, Status status, Set<Long> tagIdSet) {
        return from(photoBoothEntity)
                .leftJoin(photoBoothEntity.reviewSet, reviewEntity).fetchJoin()
                .leftJoin(reviewEntity.reviewTagSet, reviewTagEntity).fetchJoin()
                .leftJoin(reviewTagEntity.tag, tagEntity).fetchJoin()
                .where(photoBoothEntity.id.in(photoBoothIdList).and(photoBoothEntity.status.eq(Status.ACTIVE)))
                .where(buildPredicate(status, tagIdSet))
                .distinct()
                .fetch()
                .size();
    }

    private Predicate buildPredicate(Status status, Set<Long> tagIdSet) {
        BooleanBuilder builder = new BooleanBuilder();
        if (status != null) {
            builder.and(photoBoothEntity.status.eq(status));
        }
        if (CollectionUtils.isNotEmpty(tagIdSet)) {
            builder.and(photoBoothEntity.tag.id.in(tagIdSet).or(tagEntity.id.in(tagIdSet)));
        }
        return builder.getValue();
    }

    private OrderSpecifier<?> orderByFieldList(List<Long> photoBoothIdList) {
        return Expressions.stringTemplate("FIELD({0}, {1})", photoBoothEntity.id, photoBoothIdList)
                .asc();
    }
}
