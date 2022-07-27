package com.hot6.phopa.api.domain.community.model.mapper;

import com.hot6.phopa.api.domain.community.model.dto.PostApiDTO.PostApiResponse;
import com.hot6.phopa.api.domain.community.model.dto.PostApiDTO.PostImageResponse;
import com.hot6.phopa.api.domain.community.model.dto.PostApiDTO.PostTagApiResponse;
import com.hot6.phopa.core.domain.community.model.entity.PostEntity;
import com.hot6.phopa.core.domain.community.model.entity.PostImageEntity;
import com.hot6.phopa.core.domain.community.model.entity.PostTagEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public abstract class PostApiMapper {

    public abstract PostApiResponse toDto(PostEntity postEntity);

    public abstract List<PostApiResponse> toDtoList(List<PostEntity> postEntityList);

    public abstract PostTagApiResponse toDto(PostTagEntity postagEntity);

    public abstract Set<PostTagApiResponse> toTagEntityDtoSet(Set<PostTagEntity> postTagEntityList);

    @Mapping(target = "imageUrl", expression = "java(com.hot6.phopa.core.common.utils.S3UrlUtil.convertToS3Url(postImageEntity.getImageUrl()))")
    public abstract PostImageResponse toDto(PostImageEntity postImageEntity);

    public abstract Set<PostImageResponse> toImageEntityDtoSet(Set<PostImageEntity> postImageEntitySet);
}
