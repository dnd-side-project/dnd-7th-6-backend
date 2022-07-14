package com.hot6.phopa.core.domain.review.model.mapper;

import com.hot6.phopa.core.common.mapper.GenericMapper;
import com.hot6.phopa.core.domain.review.model.dto.ReviewDTO;
import com.hot6.phopa.core.domain.review.model.entity.ReviewEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ReviewMapper extends GenericMapper<ReviewDTO, ReviewEntity> {
}
