package com.hot6.phopa.core.domain.community.repository;

import com.hot6.phopa.core.domain.community.model.entity.PostEntity;
import com.hot6.phopa.core.domain.tag.model.entity.TagEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostCustomRepository {
    List<PostEntity> findAll();

    List<PostEntity> findAllByUserLike(Long userId);

    List<PostEntity> getPostByTag(TagEntity tag);
}
