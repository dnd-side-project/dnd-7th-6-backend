package com.hot6.phopa.api.domain.photobooth.model.mapper;

import com.hot6.phopa.api.domain.photobooth.model.dto.PhotoBoothApiDTO.PhotoBoothApiResponse;
import com.hot6.phopa.core.domain.photobooth.model.entity.PhotoBoothEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class PhotoBoothApiMapper {

    @Mapping(source = "photoBoothEntity.id", target = "id")
    @Mapping(source = "photoBoothEntity.name", target = "name")
    @Mapping(source = "photoBoothEntity.jibunAddress", target = "jibunAddress")
    @Mapping(source = "photoBoothEntity.roadAddress", target = "roadAddress")
    @Mapping(source = "photoBoothEntity.latitude", target = "latitude")
    @Mapping(source = "photoBoothEntity.longitude", target = "longitude")
    @Mapping(target = "imageUrl", expression = "java(com.hot6.phopa.core.common.utils.S3UrlUtil.convertToS3Url(photoBoothEntity.getReviewImage()))")
    public abstract PhotoBoothApiResponse toDto(PhotoBoothEntity photoBoothEntity);

    public abstract List<PhotoBoothApiResponse> toDtoList(List<PhotoBoothEntity> photoBoothEntityList);
}
