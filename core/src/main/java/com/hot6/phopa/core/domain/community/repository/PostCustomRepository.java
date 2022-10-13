package com.hot6.phopa.core.domain.community.repository;

import com.hot6.phopa.core.common.model.dto.PageableParam;
import com.hot6.phopa.core.common.model.type.Status;
import com.hot6.phopa.core.domain.community.enumeration.OrderType;
import com.hot6.phopa.core.domain.community.model.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PostCustomRepository {
    List<PostEntity> findAll();

    List<PostEntity> findAllByUserLike(Long userId);

    Page<PostEntity> getPostByTagIdSet(Set<Long> tagIdSet, OrderType order, PageableParam pageable);

    Page<PostEntity> findPost(Long userId, Long photoBoothId, PageableParam pageable);
}
