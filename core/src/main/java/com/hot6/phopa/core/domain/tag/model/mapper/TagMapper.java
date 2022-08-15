package com.hot6.phopa.core.domain.tag.model.mapper;

import com.hot6.phopa.core.common.mapper.GenericMapper;
import com.hot6.phopa.core.domain.tag.model.dto.TagDTO;
import com.hot6.phopa.core.domain.tag.model.entity.TagEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class TagMapper{
    @Mapping(target = "tagIconImageUrl", expression = "java(com.hot6.phopa.core.common.utils.S3UrlUtil.convertToS3Url(tagEntity.getTagIconImageUrl()))")
    public abstract TagDTO toDto(TagEntity tagEntity);

    public abstract List<TagDTO> toDtoList(List<TagEntity> tagEntityList);
}
