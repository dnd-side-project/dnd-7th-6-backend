package com.hot6.phopa.api.domain.user.model.mapper;

import com.hot6.phopa.api.domain.user.model.dto.UserApiDTO;
import com.hot6.phopa.api.domain.user.model.dto.UserApiDTO.UserApiResponse;
import com.hot6.phopa.api.domain.user.model.dto.UserApiDTO.UserLikePhotoBoothResponse;
import com.hot6.phopa.core.domain.photobooth.model.entity.PhotoBoothEntity;
import com.hot6.phopa.core.domain.user.model.dto.UserDTO;
import com.hot6.phopa.core.domain.user.model.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class UserApiMapper {
    public abstract UserApiResponse toDto(UserEntity userEntity);
    public abstract UserApiResponse toApiResponse(UserDTO userDTO);

    public abstract UserLikePhotoBoothResponse toDto(PhotoBoothEntity photoBoothEntity);
}
