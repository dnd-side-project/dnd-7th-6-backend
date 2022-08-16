package com.hot6.phopa.core.domain.photobooth.repository.impl;

import com.hot6.phopa.core.domain.photobooth.model.entity.PhotoBoothLikeEntity;
import com.hot6.phopa.core.domain.photobooth.model.entity.QPhotoBoothEntity;
import com.hot6.phopa.core.domain.photobooth.model.entity.QPhotoBoothLikeEntity;
import com.hot6.phopa.core.domain.photobooth.repository.PhotoBoothLikeCustomRepository;
import com.hot6.phopa.core.domain.user.model.entity.QUserEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Map;

import static com.hot6.phopa.core.domain.photobooth.model.entity.QPhotoBoothEntity.photoBoothEntity;
import static com.hot6.phopa.core.domain.photobooth.model.entity.QPhotoBoothLikeEntity.photoBoothLikeEntity;
import static com.hot6.phopa.core.domain.user.model.entity.QUserEntity.userEntity;

public class PhotoBoothLikeCustomRepositoryImpl extends QuerydslRepositorySupport implements PhotoBoothLikeCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public PhotoBoothLikeCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(PhotoBoothLikeEntity.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<PhotoBoothLikeEntity> findAllByPhotoBoothIdListAndUserId(List<Long> photoBoothIdList, Long userId) {
        return from(photoBoothLikeEntity)
                .join(photoBoothLikeEntity.photoBooth, photoBoothEntity).fetchJoin()
                .join(photoBoothLikeEntity.user, userEntity).fetchJoin()
                .where(photoBoothEntity.id.in(photoBoothIdList).and(userEntity.id.eq(userId)))
                .fetch();
    }

    @Override
    public List<PhotoBoothLikeEntity> findAllByUserId(Long userId) {
        return from(photoBoothLikeEntity)
                .join(photoBoothLikeEntity.photoBooth, photoBoothEntity).fetchJoin()
                .join(photoBoothLikeEntity.user, userEntity).fetchJoin()
                .where(userEntity.id.eq(userId))
                .fetch();
    }
}
