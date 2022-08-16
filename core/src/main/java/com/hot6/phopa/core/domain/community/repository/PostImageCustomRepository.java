package com.hot6.phopa.core.domain.community.repository;

import com.hot6.phopa.core.domain.community.model.entity.PostImageEntity;
import com.hot6.phopa.core.domain.community.model.entity.PostLikeEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostImageCustomRepository {
    List<PostImageEntity> findPostImageByUserLike(Long userId);
}
