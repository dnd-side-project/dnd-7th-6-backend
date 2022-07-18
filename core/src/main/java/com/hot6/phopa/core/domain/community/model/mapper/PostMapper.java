package com.hot6.phopa.core.domain.community.model.mapper;

import com.hot6.phopa.core.common.mapper.GenericMapper;
import com.hot6.phopa.core.domain.community.model.dto.PostDTO;
import com.hot6.phopa.core.domain.community.model.entity.PostEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class PostMapper extends GenericMapper<PostDTO, PostEntity> {
}
