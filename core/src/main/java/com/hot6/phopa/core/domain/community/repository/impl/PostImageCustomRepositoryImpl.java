package com.hot6.phopa.core.domain.community.repository.impl;

import com.hot6.phopa.core.common.model.type.Status;
import com.hot6.phopa.core.domain.community.model.entity.*;
import com.hot6.phopa.core.domain.community.repository.PostImageCustomRepository;
import com.hot6.phopa.core.domain.user.model.entity.QUserEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static com.hot6.phopa.core.domain.community.model.entity.QPostEntity.postEntity;
import static com.hot6.phopa.core.domain.community.model.entity.QPostImageEntity.postImageEntity;
import static com.hot6.phopa.core.domain.community.model.entity.QPostLikeEntity.postLikeEntity;
import static com.hot6.phopa.core.domain.user.model.entity.QUserEntity.userEntity;

public class PostImageCustomRepositoryImpl extends QuerydslRepositorySupport implements PostImageCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public PostImageCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(PostImageEntity.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<PostImageEntity> findPostImageByUserLike(Long userId) {
        return from(postImageEntity)
                .join(postImageEntity.post, postEntity).fetchJoin()
                .join(postEntity.postLikeSet, postLikeEntity).fetchJoin()
                .join(postLikeEntity.user, userEntity).fetchJoin()
                .where(userEntity.id.eq(userId).and(postEntity.status.eq(Status.ACTIVE)))
                .distinct()
                .fetch();
    }
}
