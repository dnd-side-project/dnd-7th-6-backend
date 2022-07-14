package com.hot6.phopa.core.domain.review.model.mapper;

import com.hot6.phopa.core.common.mapper.GenericMapper;
import com.hot6.phopa.core.domain.review.model.dto.ReviewTagDTO;
import com.hot6.phopa.core.domain.review.model.entity.ReviewTagEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ReviewTagMapper extends GenericMapper<ReviewTagDTO, ReviewTagEntity> {
}
