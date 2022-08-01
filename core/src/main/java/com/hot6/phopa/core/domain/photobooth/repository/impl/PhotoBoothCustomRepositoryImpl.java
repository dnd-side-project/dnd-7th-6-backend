package com.hot6.phopa.core.domain.photobooth.repository.impl;

import com.hot6.phopa.core.domain.photobooth.model.entity.PhotoBoothEntity;
import com.hot6.phopa.core.domain.photobooth.repository.PhotoBoothCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

import static com.hot6.phopa.core.domain.photobooth.model.entity.QPhotoBoothEntity.photoBoothEntity;
import static com.hot6.phopa.core.domain.photobooth.model.entity.QPhotoBoothLikeEntity.photoBoothLikeEntity;
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
                .where(photoBoothEntity.id.eq(photoBoothId))
                .fetch();
    }

    @Override
    public List<PhotoBoothEntity> findByPointSet(Set<Point> crawlingPointSet) {
        return from(photoBoothEntity)
                .where(photoBoothEntity.point.in(crawlingPointSet))
                .fetch();
    }

    @Override
    public List<PhotoBoothEntity> findAllByUserLike(Long userId) {
        return from(photoBoothEntity)
                .join(photoBoothEntity.photoBoothLikeSet, photoBoothLikeEntity).fetchJoin()
                .join(photoBoothLikeEntity.user, userEntity).fetchJoin()
                .where(userEntity.id.eq(userId))
                .fetch();
    }
}
