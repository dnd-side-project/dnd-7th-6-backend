package com.hot6.phopa.api.domain.photobooth.service;

import com.hot6.phopa.api.domain.photobooth.model.dto.PhotoBoothApiDTO.PhotoBoothApiResponse;
import com.hot6.phopa.api.domain.photobooth.model.dto.PhotoBoothApiDTO.PhotoBoothFormResponse;
import com.hot6.phopa.api.domain.photobooth.model.dto.PhotoBoothApiDTO.PhotoBoothWithTagResponse;
import com.hot6.phopa.api.domain.photobooth.model.mapper.PhotoBoothApiMapper;
import com.hot6.phopa.api.domain.review.model.dto.ReviewApiDTO.ReviewApiResponse;
import com.hot6.phopa.api.domain.review.model.mapper.ReviewApiMapper;
import com.hot6.phopa.core.common.model.dto.PageableParam;
import com.hot6.phopa.core.common.model.dto.PageableResponse;
import com.hot6.phopa.core.common.model.type.Status;
import com.hot6.phopa.core.domain.photobooth.model.entity.PhotoBoothEntity;
import com.hot6.phopa.core.domain.photobooth.model.entity.PhotoBoothLikeEntity;
import com.hot6.phopa.core.domain.photobooth.service.PhotoBoothService;
import com.hot6.phopa.core.domain.review.model.entity.ReviewEntity;
import com.hot6.phopa.core.domain.review.model.entity.ReviewTagEntity;
import com.hot6.phopa.core.domain.tag.enumeration.TagType;
import com.hot6.phopa.core.domain.tag.model.dto.TagDTO;
import com.hot6.phopa.core.domain.tag.model.entity.TagEntity;
import com.hot6.phopa.core.domain.tag.model.mapper.TagMapper;
import com.hot6.phopa.core.domain.tag.service.TagService;
import com.hot6.phopa.core.domain.user.model.entity.UserEntity;
import com.hot6.phopa.core.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PhotoBoothApiService {

    private final PhotoBoothService photoBoothService;
    private final PhotoBoothApiMapper photoBoothMapper;
    private final TagService tagService;
    private final TagMapper tagMapper;

    private final ReviewApiMapper reviewApiMapper;
    private final UserService userService;

    public PageableResponse<PhotoBoothWithTagResponse> getPhotoBoothNearByUserGeo(Double latitude, Double longitude, Double distance, Status status, Set<Long> tagIdSet, Long userId, PageableParam pageable) {
        Page<PhotoBoothEntity> photoBoothEntityPage = photoBoothService.getPhotoBoothNearByUserGeo(latitude, longitude, distance, status, tagIdSet, userId, pageable);
        List<Long> photoBoothIdList = photoBoothEntityPage.getContent().stream().map(PhotoBoothEntity::getId).collect(Collectors.toList());
        Map<Long, List<PhotoBoothLikeEntity>> userLikePhotoBoothIdMap = userId != null ? photoBoothService.getPhotoBoothLikeByPhotoBoothIdListAndUserId(photoBoothIdList, userId).stream().collect(Collectors.groupingBy(photoBoothLikeEntity -> photoBoothLikeEntity.getPhotoBooth().getId())) : new HashMap<>();
        List<PhotoBoothWithTagResponse> photoBoothWithTagResponseList = new ArrayList<>();
        for (PhotoBoothEntity photoBoothEntity : photoBoothEntityPage.getContent()) {
            List<TagEntity> tagEntityList = photoBoothEntity.getReviewSet().stream().flatMap(r -> r.getReviewTagSet().stream().map(ReviewTagEntity::getTag)).collect(Collectors.toList());
            boolean isLike = userLikePhotoBoothIdMap.containsKey(photoBoothEntity.getId());
            photoBoothWithTagResponseList.add(buildPhotoBoothWithTagResponse(photoBoothEntity, tagEntityList, isLike));
        }
        return PageableResponse.makeResponse(photoBoothEntityPage, photoBoothWithTagResponseList);
    }

    public List<PhotoBoothApiResponse> kakaoMapTest(String keyword, Double latitude, Double longitude, Double distance) {
        TagEntity tagEntity = tagService.getTagOrCreate(keyword, TagType.BRAND);
        List<PhotoBoothEntity> photoBoothEntityList = photoBoothService.kakaoMapTest(keyword, latitude, longitude, distance, tagEntity);
        return photoBoothMapper.toDtoList(photoBoothEntityList);
    }

    public void like(Long photoBoothId, Long userId) {
        PhotoBoothEntity photoBoothEntity = photoBoothService.getPhotoBoothById(photoBoothId);
        UserEntity userEntity = userService.findById(userId);
        PhotoBoothLikeEntity photoBoothLikeEntity = photoBoothService.getPhotoBoothLikeByPhotoBoothIdAndUserId(photoBoothId, userId);
        if (photoBoothLikeEntity != null) {
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
        List<TagDTO> tagDTOList = tagMapper.toDtoList(tagService.getTagListByTagTypeList(TagType.REVIEW_FORM_TAG_LIST, true));
        List<TagDTO> brandTagDTOList = tagDTOList.stream().filter(tag -> TagType.BRAND.equals(tag.getTagType())).collect(Collectors.toList());
        tagDTOList.removeAll(brandTagDTOList);
        return PhotoBoothFormResponse.of(brandTagDTOList, tagDTOList);
    }

    public PhotoBoothWithTagResponse getPhotoBooth(Long photoBoothId, Long userId) {
        PhotoBoothEntity photoBoothEntity = photoBoothService.getPhotoBooth(photoBoothId);
        List<TagEntity> tagEntityList = tagService.getTagByPhotoBoothId(photoBoothId);
        boolean isLike = userId != null & photoBoothService.getPhotoBoothLikeByPhotoBoothIdAndUserId(photoBoothEntity.getId(), userId) == null;
        return buildPhotoBoothWithTagResponse(photoBoothEntity, tagEntityList, isLike);
    }

    private PhotoBoothWithTagResponse buildPhotoBoothWithTagResponse(PhotoBoothEntity photoBoothEntity, List<TagEntity> tagList, boolean isLike) {
        PhotoBoothApiResponse photoBooth = photoBoothMapper.toDto(photoBoothEntity);
        ReviewApiResponse review = CollectionUtils.isNotEmpty(photoBoothEntity.getReviewSet()) ? reviewApiMapper.toDto((ReviewEntity) photoBoothEntity.getReviewSet().toArray()[0]) : null;
        List<TagDTO> tagDTOList = CollectionUtils.isNotEmpty(tagList) ? tagMapper.toDtoList(tagList) : null;
        return PhotoBoothWithTagResponse.of(photoBooth, review, tagDTOList, isLike);
    }
}
