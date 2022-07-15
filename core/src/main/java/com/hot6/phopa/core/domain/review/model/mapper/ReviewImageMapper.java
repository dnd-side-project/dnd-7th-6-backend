package com.hot6.phopa.core.domain.review.model.mapper;

import com.hot6.phopa.core.domain.review.model.dto.ReviewImageDTO;
import com.hot6.phopa.core.domain.review.model.entity.ReviewImageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring")
public abstract class ReviewImageMapper {
    @Mapping(target = "imageUrl", expression = "java(com.hot6.phopa.core.common.utils.S3UrlUtil.convertToS3Url(reviewImageEntity.getImageUrl()))")
    public abstract ReviewImageDTO toDto(ReviewImageEntity reviewImageEntity);

    public abstract Set<ReviewImageDTO> toDtoSet(Set<ReviewImageEntity> reviewImageEntitySet);
}
