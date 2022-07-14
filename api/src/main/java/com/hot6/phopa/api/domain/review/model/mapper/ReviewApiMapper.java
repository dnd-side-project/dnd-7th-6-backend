package com.hot6.phopa.api.domain.review.model.mapper;

import com.hot6.phopa.api.domain.review.model.dto.ReviewApiDTO.ReviewApiResponse;
import com.hot6.phopa.core.domain.review.model.entity.ReviewEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ReviewApiMapper {

//    @Mapping(target = "reviewList", source = "reviewEntity.reviewList")
    public abstract ReviewApiResponse toDto(ReviewEntity reviewEntity);

    public abstract List<ReviewApiResponse> toDtoList(List<ReviewEntity> reviewEntityList);
}
