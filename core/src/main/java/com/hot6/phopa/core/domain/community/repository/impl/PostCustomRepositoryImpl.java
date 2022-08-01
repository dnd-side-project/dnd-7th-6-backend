package com.hot6.phopa.core.domain.community.repository.impl;

import com.hot6.phopa.core.domain.community.model.entity.PostEntity;
import com.hot6.phopa.core.domain.community.model.entity.QPostLikeEntity;
import com.hot6.phopa.core.domain.community.repository.PostCustomRepository;
import com.hot6.phopa.core.domain.user.model.entity.QUserEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.hot6.phopa.core.domain.community.model.entity.QPostEntity.postEntity;
import static com.hot6.phopa.core.domain.community.model.entity.QPostImageEntity.postImageEntity;
import static com.hot6.phopa.core.domain.community.model.entity.QPostLikeEntity.postLikeEntity;
import static com.hot6.phopa.core.domain.community.model.entity.QPostTagEntity.postTagEntity;
import static com.hot6.phopa.core.domain.tag.model.entity.QTagEntity.*;
import static com.hot6.phopa.core.domain.user.model.entity.QUserEntity.userEntity;

@Repository
public class PostCustomRepositoryImpl extends QuerydslRepositorySupport implements PostCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public PostCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(PostEntity.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }


    @Override
    public List<PostEntity> findAll() {
        return from(postEntity)
                .leftJoin(postEntity.postImageSet, postImageEntity).fetchJoin()
                .leftJoin(postEntity.postTagSet, postTagEntity).fetchJoin()
                .leftJoin(postTagEntity.tag, tagEntity).fetchJoin()
                .distinct()
                .fetch();
    }

    @Override
    public List<PostEntity> findAllByUserLike(Long userId) {
        return from(postEntity)
                .join(postEntity.postLikeSet, postLikeEntity).fetchJoin()
                .join(postLikeEntity.user, userEntity).fetchJoin()
                .where(userEntity.id.eq(userId))
                .fetch();
    }
}
