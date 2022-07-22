package com.hot6.phopa.api.domain.review.model.mapper;

import com.hot6.phopa.api.domain.review.model.dto.PostApiDTO.PostApiResponse;
import com.hot6.phopa.api.domain.review.model.dto.PostApiDTO.PostImageResponse;
import com.hot6.phopa.api.domain.review.model.dto.PostApiDTO.PostTagApiResponse;
import com.hot6.phopa.api.domain.review.model.dto.ReviewApiDTO;
import com.hot6.phopa.api.domain.review.model.dto.ReviewApiDTO.ReviewApiResponse;
import com.hot6.phopa.api.domain.review.model.dto.ReviewApiDTO.ReviewImageResponse;
import com.hot6.phopa.api.domain.review.model.dto.ReviewApiDTO.ReviewTagApiResponse;
import com.hot6.phopa.core.domain.review.model.entity.ReviewEntity;
import com.hot6.phopa.core.domain.review.model.entity.ReviewImageEntity;
import com.hot6.phopa.core.domain.review.model.entity.ReviewTagEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public abstract class ReviewApiMapper {

    public abstract ReviewApiResponse toDto(ReviewEntity reviewEntity);

    public abstract List<ReviewApiResponse> toDtoList(List<ReviewEntity> reviewEntityList);

    public abstract ReviewTagApiResponse toDto(ReviewTagEntity reviewTagEntity);

    public abstract Set<ReviewTagApiResponse> toTagEntityDtoSet(Set<ReviewTagEntity> reviewTagEntityList);

    @Mapping(target = "imageUrl", expression = "java(com.hot6.phopa.core.common.utils.S3UrlUtil.convertToS3Url(reviewImageEntity.getImageUrl()))")
    public abstract ReviewImageResponse toDto(ReviewImageEntity reviewImageEntity);

    public abstract Set<ReviewImageResponse> toImageEntityDtoSet(Set<ReviewImageEntity> reviewImageEntitySet);
}
