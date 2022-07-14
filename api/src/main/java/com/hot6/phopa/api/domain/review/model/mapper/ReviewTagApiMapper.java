package com.hot6.phopa.api.domain.review.model.mapper;

import com.hot6.phopa.api.domain.review.model.dto.ReviewTagApiDTO.ReviewTagApiResponse;
import com.hot6.phopa.core.domain.review.model.entity.ReviewTagEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ReviewTagApiMapper {

    public abstract ReviewTagApiResponse toDto(ReviewTagEntity reviewTagEntity);

    public abstract List<ReviewTagApiResponse> toDtoList(List<ReviewTagEntity> reviewTagEntityList);
}
