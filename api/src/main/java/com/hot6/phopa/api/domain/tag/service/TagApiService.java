package com.hot6.phopa.api.domain.tag.service;

import com.hot6.phopa.api.domain.tag.model.dto.TagApiDTO;
import com.hot6.phopa.api.domain.tag.model.mapper.TagApiMapper;
import com.hot6.phopa.core.common.model.dto.PageableParam;
import com.hot6.phopa.core.common.model.dto.PageableResponse;
import com.hot6.phopa.core.domain.tag.enumeration.TagType;
import com.hot6.phopa.core.domain.tag.model.entity.TagEntity;
import com.hot6.phopa.core.domain.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagApiService {
    private final TagService tagService;
    private final TagApiMapper tagApiMapper;
    public PageableResponse<TagApiDTO.TagKeywordResponse> getTagByKeyword(String keyword, TagType tagType, PageableParam pageable) {
        Page<TagEntity> tagEntityPage = tagService.getTagByKeyword(keyword, tagType, pageable);
        return PageableResponse.makeResponse(tagEntityPage, tagApiMapper.toDtoList(tagEntityPage.getContent()));
    }
}
