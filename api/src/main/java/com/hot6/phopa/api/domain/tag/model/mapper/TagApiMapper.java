package com.hot6.phopa.api.domain.tag.model.mapper;

import com.hot6.phopa.api.domain.tag.model.dto.TagApiDTO.TagKeywordResponse;
import com.hot6.phopa.core.domain.tag.model.entity.TagEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class TagApiMapper {
    @Mapping(target = "id", source = "tagEntity.id")
    @Mapping(target = "title", source = "tagEntity.title")
    public abstract TagKeywordResponse toDto(TagEntity tagEntity);
    public abstract List<TagKeywordResponse> toDtoList(List<TagEntity> tagEntityList);
}
