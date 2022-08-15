package com.hot6.phopa.api.domain.tag.model.mapper;

import com.hot6.phopa.api.domain.tag.model.dto.TagApiDTO.TagApiResponse;
import com.hot6.phopa.core.domain.tag.model.entity.TagEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class TagApiMapper {
    @Mapping(target = "tagIconImageUrl", expression = "java(com.hot6.phopa.core.common.utils.S3UrlUtil.convertToS3Url(tagEntity.getTagIconImageUrl()))")
    public abstract TagApiResponse toDto(TagEntity tagEntity);
    public abstract List<TagApiResponse> toDtoList(List<TagEntity> tagEntityList);
}
