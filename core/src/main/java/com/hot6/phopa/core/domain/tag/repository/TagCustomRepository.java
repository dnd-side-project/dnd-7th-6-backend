package com.hot6.phopa.core.domain.tag.repository;

import com.hot6.phopa.core.domain.tag.model.entity.TagEntity;

import java.util.List;

public interface TagCustomRepository {
    List<TagEntity> findByPhotoBoothId(Long photoBoothId);
}
