package com.hot6.phopa.api.domain.tag.model.mapper;

import com.hot6.phopa.api.domain.tag.model.dto.TagApiDTO.TagApiResponse;
import com.hot6.phopa.core.domain.tag.model.entity.TagEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class TagApiMapper {
    public abstract TagApiResponse toDto(TagEntity tagEntity);
    public abstract List<TagApiResponse> toDtoList(List<TagEntity> tagEntityList);
}
