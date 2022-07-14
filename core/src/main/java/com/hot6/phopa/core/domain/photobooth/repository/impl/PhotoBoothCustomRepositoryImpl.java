package com.hot6.phopa.core.domain.photobooth.repository.impl;

import com.hot6.phopa.core.domain.photobooth.model.entity.PhotoBoothEntity;
import com.hot6.phopa.core.domain.photobooth.repository.PhotoBoothCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.hot6.phopa.core.domain.photobooth.model.entity.QPhotoBoothEntity.photoBoothEntity;

@Repository
public class PhotoBoothCustomRepositoryImpl extends QuerydslRepositorySupport implements PhotoBoothCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public PhotoBoothCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(PhotoBoothEntity.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }


    public List<PhotoBoothEntity> findByPhotoBoothId(Long photoBoothId){
        return from(photoBoothEntity)
                .where(photoBoothEntity.id.eq(photoBoothId))
                .fetch();
    }
}
