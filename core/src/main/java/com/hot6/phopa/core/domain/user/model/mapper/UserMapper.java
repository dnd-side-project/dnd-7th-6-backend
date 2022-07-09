package com.hot6.phopa.core.domain.user.model.mapper;

import com.hot6.phopa.core.common.mapper.GenericMapper;
import com.hot6.phopa.core.domain.user.model.dto.UserDTO;
import com.hot6.phopa.core.domain.user.model.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class UserMapper extends GenericMapper<UserDTO, UserEntity> {

    @Override
    public abstract UserEntity toEntity(final UserDTO dto);
}
