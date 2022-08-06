package com.hot6.phopa.core.domain.tag.repository;

import com.hot6.phopa.core.common.model.dto.PageableParam;
import com.hot6.phopa.core.domain.tag.enumeration.TagType;
import com.hot6.phopa.core.domain.tag.model.entity.TagEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TagCustomRepository {
    List<TagEntity> findByPhotoBoothId(Long photoBoothId);

    Page<TagEntity> getTagByKeyword(String keyword, TagType tagType, PageableParam pageable);
}
