package com.hot6.phopa.core.domain.community.model.mapper;

import com.hot6.phopa.core.common.mapper.GenericMapper;
import com.hot6.phopa.core.domain.community.model.dto.PostTagDTO;
import com.hot6.phopa.core.domain.community.model.entity.PostTagEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class PostTagMapper extends GenericMapper<PostTagDTO, PostTagEntity> {
}
