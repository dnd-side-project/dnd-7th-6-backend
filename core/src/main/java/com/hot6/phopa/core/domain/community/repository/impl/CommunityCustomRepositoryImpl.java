package com.hot6.phopa.core.domain.community.repository.impl;

import com.hot6.phopa.core.domain.community.model.entity.PostEntity;
import com.hot6.phopa.core.domain.community.repository.CommnunityCustomRepository;
import com.hot6.phopa.core.domain.tag.model.entity.QTagEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.hot6.phopa.core.domain.community.model.entity.QPostEntity.postEntity;
import static com.hot6.phopa.core.domain.community.model.entity.QPostImageEntity.postImageEntity;
import static com.hot6.phopa.core.domain.community.model.entity.QPostTagEntity.postTagEntity;
import static com.hot6.phopa.core.domain.tag.model.entity.QTagEntity.*;

@Repository
public class CommunityCustomRepositoryImpl extends QuerydslRepositorySupport implements CommnunityCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public CommunityCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(PostEntity.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }


    @Override
    public List<PostEntity> findAll() {
        return from(postEntity)
                .leftJoin(postEntity.postImageEntitySet, postImageEntity).fetchJoin()
                .leftJoin(postEntity.postTagEntitySet, postTagEntity).fetchJoin()
                .leftJoin(postTagEntity.tag, tagEntity).fetchJoin()
                .distinct()
                .fetch();
    }
}
