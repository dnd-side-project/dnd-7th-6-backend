package com.hot6.phopa.api.domain.tag.service;

import com.hot6.phopa.api.domain.tag.model.dto.TagApiDTO;
import com.hot6.phopa.api.domain.tag.model.dto.TagApiDTO.TagApiResponse;
import com.hot6.phopa.api.domain.tag.model.dto.TagApiDTO.TagCreateRequest;
import com.hot6.phopa.api.domain.tag.model.mapper.TagApiMapper;
import com.hot6.phopa.core.common.model.dto.PageableParam;
import com.hot6.phopa.core.common.model.dto.PageableResponse;
import com.hot6.phopa.core.domain.tag.enumeration.TagType;
import com.hot6.phopa.core.domain.tag.model.dto.TagDTO;
import com.hot6.phopa.core.domain.tag.model.entity.TagEntity;
import com.hot6.phopa.core.domain.tag.model.mapper.TagMapper;
import com.hot6.phopa.core.domain.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagApiService {
    private final TagService tagService;
    private final TagApiMapper tagApiMapper;
    public PageableResponse<TagApiResponse> getTagByKeyword(String keyword, TagType tagType, PageableParam pageable) {
        Page<TagEntity> tagEntityPage = tagService.getTagByKeyword(keyword, tagType, pageable);
        return PageableResponse.makeResponse(tagEntityPage, tagApiMapper.toDtoList(tagEntityPage.getContent()));
    }

    public TagApiResponse createTag(TagCreateRequest tagCreateRequest) {
        TagEntity tagEntity = tagService.getTagOrCreate(tagCreateRequest.getKeyword(),tagCreateRequest.getTitle(), tagCreateRequest.getTagType());
        return tagApiMapper.toDto(tagEntity);
    }

    public List<TagApiResponse> createTagList(List<TagCreateRequest> tagCreateRequestList) {
        List<TagEntity> tagEntityList = tagCreateRequestList.stream().map(request -> convertToTagEntity(request)).collect(Collectors.toList());
        return tagApiMapper.toDtoList(tagService.createAll(tagEntityList));
    }

    private TagEntity convertToTagEntity(TagCreateRequest tagCreateRequest){
        return TagEntity.builder()
                .title(tagCreateRequest.getTitle())
                .keyword(Optional.ofNullable(tagCreateRequest.getKeyword()).orElse(tagCreateRequest.getTitle()))
                .tagType(tagCreateRequest.getTagType())
                .build();
    }

    public List<TagApiResponse> getForm(TagType tagType) {
        return tagApiMapper.toDtoList(tagService.getTagListByTagType(tagType));
    }
}
