package com.hot6.phopa.core.domain.photobooth.model.mapper;

import com.hot6.phopa.core.common.mapper.GenericMapper;
import com.hot6.phopa.core.domain.photobooth.model.dto.PhotoBoothDTO;
import com.hot6.phopa.core.domain.photobooth.model.entity.PhotoBoothEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class PhotoBoothMapper extends GenericMapper<PhotoBoothDTO, PhotoBoothEntity> {
    @Mapping(expression = "java(photoBoothEntity.getPoint().getX())", target = "latitude")
    @Mapping(expression = "java(photoBoothEntity.getPoint().getY())", target = "longitude")
    public abstract PhotoBoothDTO toDto(PhotoBoothEntity photoBoothEntity);
}
