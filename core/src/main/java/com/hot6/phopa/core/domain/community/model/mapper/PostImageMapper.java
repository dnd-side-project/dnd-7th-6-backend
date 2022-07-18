package com.hot6.phopa.core.domain.community.model.mapper;

import com.hot6.phopa.core.domain.community.model.dto.PostImageDTO;
import com.hot6.phopa.core.domain.community.model.entity.PostImageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring")
public abstract class PostImageMapper {
    @Mapping(target = "imageUrl", expression = "java(com.hot6.phopa.core.common.utils.S3UrlUtil.convertToS3Url(postImageEntity.getImageUrl()))")
    public abstract PostImageDTO toDto(PostImageEntity postImageEntity);

    public abstract Set<PostImageDTO> toDtoSet(Set<PostImageEntity> reviewImageEntitySet);
}
