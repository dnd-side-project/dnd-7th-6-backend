package com.hot6.phopa.core.domain.community.repository.impl;

import com.hot6.phopa.core.common.model.dto.PageableParam;
import com.hot6.phopa.core.common.model.type.Status;
import com.hot6.phopa.core.domain.community.enumeration.OrderType;
import com.hot6.phopa.core.domain.community.model.entity.PostEntity;
import com.hot6.phopa.core.domain.community.model.entity.QPostLikeEntity;
import com.hot6.phopa.core.domain.community.model.entity.QPostTagEntity;
import com.hot6.phopa.core.domain.community.repository.PostCustomRepository;
import com.hot6.phopa.core.domain.tag.model.entity.QTagEntity;
import com.hot6.phopa.core.domain.tag.model.entity.TagEntity;
import com.hot6.phopa.core.domain.user.model.entity.QUserEntity;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

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
                .where(postEntity.status.eq(Status.ACTIVE))
                .distinct()
                .fetch();
    }

    @Override
    public List<PostEntity> findAllByUserLike(Long userId) {
        return from(postEntity)
                .join(postEntity.postLikeSet, postLikeEntity).fetchJoin()
                .join(postLikeEntity.user, userEntity).fetchJoin()
                .where(userEntity.id.eq(userId).and(postEntity.status.eq(Status.ACTIVE)))
                .fetch();
    }


    @Override
    public Page<PostEntity> getPostByTagIdSet(Set<Long> tagIdSet, OrderType order, PageableParam pageable) {
        QueryResults result = jpaQueryFactory.selectFrom(postEntity)
                .leftJoin(postEntity.postLikeSet, postLikeEntity).fetchJoin()
                .leftJoin(postEntity.postTagSet, postTagEntity).fetchJoin()
                .leftJoin(postTagEntity.tag, tagEntity).fetchJoin()
                .leftJoin(postLikeEntity.user, userEntity).fetchJoin()
                .where(tagEntity.id.in(tagIdSet).and(postEntity.status.eq(Status.ACTIVE)))
                .orderBy(getOrderByField(order))
                .offset(pageable.getPage())
                .limit(pageable.getPageSize())
                .distinct()
                .fetchResults();
        return new PageImpl<>(result.getResults(), PageRequest.of(pageable.getPage(), pageable.getPageSize()), result.getTotal());
    }

    private OrderSpecifier<?> getOrderByField(OrderType type) {
        PathBuilder expression = new PathBuilder(PostEntity.class, "post");
        if (OrderType.popular.equals(type)) {
            return new OrderSpecifier<>(Order.DESC, expression.get("likeCount"));
        }
        if(OrderType.latest.equals(type)) {
            return new OrderSpecifier<>(Order.DESC, expression.get("createdAt"));
        }
        return new OrderSpecifier<>(Order.ASC, expression.get("id"));
    }
}
