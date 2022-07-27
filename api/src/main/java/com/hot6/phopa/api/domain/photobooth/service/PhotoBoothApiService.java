package com.hot6.phopa.api.domain.photobooth.service;

import com.hot6.phopa.api.domain.photobooth.model.dto.PhotoBoothApiDTO;
import com.hot6.phopa.api.domain.photobooth.model.dto.PhotoBoothApiDTO.PhotoBoothApiResponse;
import com.hot6.phopa.api.domain.photobooth.model.dto.PhotoBoothApiDTO.PhotoBoothFormResponse;
import com.hot6.phopa.api.domain.photobooth.model.mapper.PhotoBoothApiMapper;
import com.hot6.phopa.core.common.model.type.Status;
import com.hot6.phopa.core.domain.photobooth.model.entity.PhotoBoothEntity;
import com.hot6.phopa.core.domain.photobooth.model.entity.PhotoBoothLikeEntity;
import com.hot6.phopa.core.domain.photobooth.service.PhotoBoothService;
import com.hot6.phopa.core.domain.review.model.entity.ReviewEntity;
import com.hot6.phopa.core.domain.review.model.entity.ReviewLikeEntity;
import com.hot6.phopa.core.domain.tag.model.entity.TagEntity;
import com.hot6.phopa.core.domain.tag.model.mapper.TagMapper;
import com.hot6.phopa.core.domain.tag.service.TagService;
import com.hot6.phopa.core.domain.user.model.entity.UserEntity;
import com.hot6.phopa.core.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class PhotoBoothApiService {

    private final PhotoBoothService photoBoothService;
    private final PhotoBoothApiMapper photoBoothMapper;
    private final TagService tagService;
    private final TagMapper tagMapper;

    private final UserService userService;
    public List<PhotoBoothApiResponse> getPhotoBoothNearByUserGeo(Double latitude, Double longitude, Double distance, Status status, Set<Long> tagIdSet) {
        return photoBoothMapper.toDtoList(photoBoothService.getPhotoBoothNearByUserGeo(latitude, longitude, distance, status, tagIdSet));
    }

    public List<PhotoBoothApiResponse> kakaoMapTest(String keyword, Double latitude, Double longitude, Double distance) {
        TagEntity tagEntity = tagService.getTagOrCreate(keyword);
        List<PhotoBoothEntity> photoBoothEntityList = photoBoothService.kakaoMapTest(keyword, latitude, longitude, distance, tagEntity);
        return photoBoothMapper.toDtoList(photoBoothEntityList);
    }

    public void like(Long photoBoothId, Long userId) {
        PhotoBoothEntity photoBoothEntity = photoBoothService.getPhotoBoothById(photoBoothId);
        UserEntity userEntity = userService.findById(userId);
        PhotoBoothLikeEntity photoBoothLikeEntity = photoBoothService.getPhotoBoothLikeByPhotoBoothIdAndUserId(photoBoothId, userId);
        if (photoBoothLikeEntity != null){
            photoBoothService.deletePhotoBoothLikeEntity(photoBoothLikeEntity);
            photoBoothEntity.updateLikeCount(-1);
        } else {
            photoBoothLikeEntity = PhotoBoothLikeEntity.builder()
                    .photoBooth(photoBoothEntity)
                    .user(userEntity)
                    .build();
            photoBoothService.createReviewLikeEntity(photoBoothLikeEntity);
            photoBoothEntity.updateLikeCount(1);
        }
    }

    public PhotoBoothFormResponse getFormData() {
        List<TagEntity> tagEntityList = tagService.getTagListIsPhotoBooth();
        return PhotoBoothFormResponse.of(tagMapper.toDtoList(tagEntityList));
    }
}
