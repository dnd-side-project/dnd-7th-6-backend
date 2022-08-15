package com.hot6.phopa.api.domain.review.model.mapper;

import com.hot6.phopa.api.domain.review.model.dto.ReviewApiDTO.ReviewApiResponse;
import com.hot6.phopa.api.domain.review.model.dto.ReviewApiDTO.ReviewImageResponse;
import com.hot6.phopa.api.domain.review.model.dto.ReviewApiDTO.ReviewTagApiResponse;
import com.hot6.phopa.core.domain.review.model.entity.ReviewEntity;
import com.hot6.phopa.core.domain.review.model.entity.ReviewImageEntity;
import com.hot6.phopa.core.domain.review.model.entity.ReviewTagEntity;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public abstract class ReviewApiMapper {

    @Named( value = "reviewApiResponse")
    public abstract ReviewApiResponse toDto(ReviewEntity reviewEntity);

    @IterableMapping(qualifiedByName = "reviewApiResponse")
    public abstract List<ReviewApiResponse> toDtoList(List<ReviewEntity> reviewEntityList);

    @Named( value = "reviewTagApiResponse")
    public abstract ReviewTagApiResponse toDto(ReviewTagEntity reviewTagEntity);

    @IterableMapping(qualifiedByName = "reviewTagApiResponse")
    public abstract Set<ReviewTagApiResponse> toTagEntityDtoSet(Set<ReviewTagEntity> reviewTagEntityList);

    @Named( value = "reviewImageResponse")
    @Mapping(target = "imageUrl", expression = "java(com.hot6.phopa.core.common.utils.S3UrlUtil.convertToS3Url(reviewImageEntity.getImageUrl()))")
    public abstract ReviewImageResponse toDto(ReviewImageEntity reviewImageEntity);

    @IterableMapping(qualifiedByName = "reviewImageResponse")
    public abstract Set<ReviewImageResponse> toImageEntityDtoSet(Set<ReviewImageEntity> reviewImageEntitySet);
    @IterableMapping(qualifiedByName = "reviewImageResponse")
    public abstract List<ReviewImageResponse> toImageEntityDto(List<ReviewImageEntity> reviewImageEntityList);
}
