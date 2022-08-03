package com.hot6.phopa.core.domain.community.repository;

import com.hot6.phopa.core.domain.community.model.entity.PostEntity;
import com.hot6.phopa.core.domain.tag.model.entity.TagEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PostCustomRepository {
    List<PostEntity> findAll();

    List<PostEntity> findAllByUserLike(Long userId);

    Page<PostEntity> getPostByTagIdSet(Set<Long> tagIdSet, int pageSize, int pageNumber);
}
