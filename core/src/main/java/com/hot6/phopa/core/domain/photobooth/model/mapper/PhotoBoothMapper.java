package com.hot6.phopa.core.domain.photobooth.model.mapper;

import com.hot6.phopa.core.common.mapper.GenericMapper;
import com.hot6.phopa.core.domain.photobooth.model.dto.PhotoBoothDTO;
import com.hot6.phopa.core.domain.photobooth.model.entity.PhotoBoothEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class PhotoBoothMapper extends GenericMapper<PhotoBoothDTO, PhotoBoothEntity> {
}
