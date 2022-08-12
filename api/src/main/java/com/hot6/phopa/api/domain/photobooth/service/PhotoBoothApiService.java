package com.hot6.phopa.api.domain.photobooth.service;

import com.hot6.phopa.api.domain.photobooth.model.dto.PhotoBoothApiDTO.PhotoBoothApiResponse;
import com.hot6.phopa.api.domain.photobooth.model.dto.PhotoBoothApiDTO.PhotoBoothFilterFormResponse;
import com.hot6.phopa.api.domain.photobooth.model.dto.PhotoBoothApiDTO.PhotoBoothWithTagResponse;
import com.hot6.phopa.api.domain.photobooth.model.mapper.PhotoBoothApiMapper;
import com.hot6.phopa.api.domain.review.model.dto.ReviewApiDTO.ReviewApiResponse;
import com.hot6.phopa.api.domain.review.model.mapper.ReviewApiMapper;
import com.hot6.phopa.core.common.model.dto.PageableParam;
import com.hot6.phopa.core.common.model.dto.PageableResponse;
import com.hot6.phopa.core.common.model.type.Status;
import com.hot6.phopa.core.common.utils.GeometryUtil;
import com.hot6.phopa.core.domain.photobooth.model.dto.PhotoBoothWithDistanceDTO;
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
import com.hot6.phopa.core.domain.user.model.dto.UserDTO;
import com.hot6.phopa.core.domain.user.model.entity.UserEntity;
import com.hot6.phopa.core.domain.user.service.UserService;
import com.hot6.phopa.core.security.config.PrincipleDetail;
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

    public PageableResponse<PhotoBoothWithTagResponse> getPhotoBoothNearByUserGeo(Double latitude, Double longitude, Double distance, Status status, Set<Long> tagIdSet, PageableParam pageable) {
        UserDTO userDto = PrincipleDetail.get();
        UserEntity userEntity = userService.findById(userDto.getId());
        PhotoBoothWithDistanceDTO photoBoothWithDistanceDTO = photoBoothService.getPhotoBoothNearByUserGeo(latitude, longitude, distance, status, tagIdSet, pageable);
        Page<PhotoBoothEntity> photoBoothEntityPage = photoBoothWithDistanceDTO.getPhotoBoothEntityPage();
        Map<Long, Double> photoBoothIdDistanceMap = photoBoothWithDistanceDTO.getPhotoBoothIdDistanceMap();
        List<Long> photoBoothIdList = photoBoothEntityPage.getContent().stream().map(PhotoBoothEntity::getId).collect(Collectors.toList());
        Map<Long, List<PhotoBoothLikeEntity>> userLikePhotoBoothIdMap = userEntity != null ? photoBoothService.getPhotoBoothLikeByPhotoBoothIdListAndUserId(photoBoothIdList, userEntity.getId()).stream().collect(Collectors.groupingBy(photoBoothLikeEntity -> photoBoothLikeEntity.getPhotoBooth().getId())) : new HashMap<>();
        List<PhotoBoothWithTagResponse> photoBoothWithTagResponseList = new ArrayList<>();
        for (PhotoBoothEntity photoBoothEntity : photoBoothEntityPage.getContent()) {
            List<TagEntity> tagEntityList = photoBoothEntity.getReviewSet().stream().flatMap(r -> r.getReviewTagSet().stream().map(ReviewTagEntity::getTag)).collect(Collectors.toList());
            boolean isLike = userLikePhotoBoothIdMap.containsKey(photoBoothEntity.getId());
            photoBoothWithTagResponseList.add(buildPhotoBoothWithTagResponse(photoBoothEntity, tagEntityList, isLike, photoBoothIdDistanceMap.get(photoBoothEntity.getId())));
        }
        return PageableResponse.makeResponse(photoBoothEntityPage, photoBoothWithTagResponseList);
    }

    public List<PhotoBoothApiResponse> kakaoMapTest(String keyword, Double latitude, Double longitude, Double distance) {
        TagEntity tagEntity = tagService.getTagOrCreate(keyword, TagType.BRAND);
        List<PhotoBoothEntity> photoBoothEntityList = photoBoothService.kakaoMapTest(keyword, latitude, longitude, distance, tagEntity);
        return photoBoothMapper.toDtoList(photoBoothEntityList);
    }

    public void like(Long photoBoothId) {
        UserDTO userDto = PrincipleDetail.get();
        UserEntity userEntity = userService.findById(userDto.getId());
        PhotoBoothEntity photoBoothEntity = photoBoothService.getPhotoBoothById(photoBoothId);
        PhotoBoothLikeEntity photoBoothLikeEntity = photoBoothService.getPhotoBoothLikeByPhotoBoothIdAndUserId(photoBoothId, userEntity.getId());
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

    public PhotoBoothFilterFormResponse getFilterData() {
        List<TagDTO> tagDTOList = tagMapper.toDtoList(tagService.getTagListByTagTypeList(TagType.PHOTO_BOOTH_FILTER_TAG_LIST, true));
        List<TagDTO> brandTagDTOList = tagDTOList.stream().filter(tag -> TagType.BRAND.equals(tag.getTagType())).collect(Collectors.toList());
        tagDTOList.removeAll(brandTagDTOList);
        return PhotoBoothFilterFormResponse.of(brandTagDTOList, tagDTOList);
    }

    public PhotoBoothWithTagResponse getPhotoBooth(Long photoBoothId, Double latitude, Double longitude) {
        UserDTO userDto = PrincipleDetail.get();
        UserEntity userEntity = userService.findById(userDto.getId());
        PhotoBoothEntity photoBoothEntity = photoBoothService.getPhotoBooth(photoBoothId);
        List<TagEntity> tagEntityList = tagService.getTagByPhotoBoothId(photoBoothId);
        boolean isLike = userEntity != null & photoBoothService.getPhotoBoothLikeByPhotoBoothIdAndUserId(photoBoothEntity.getId(), userEntity.getId()) == null;
        Double distance = latitude != null && longitude != null ? GeometryUtil.distance(photoBoothEntity.getLatitude(), photoBoothEntity.getLongitude(), latitude, longitude) : null;
        return buildPhotoBoothWithTagResponse(photoBoothEntity, tagEntityList, isLike, distance);
    }

    private PhotoBoothWithTagResponse buildPhotoBoothWithTagResponse(PhotoBoothEntity photoBoothEntity, List<TagEntity> tagList, boolean isLike, Double distance) {
        PhotoBoothApiResponse photoBooth = photoBoothMapper.toDto(photoBoothEntity);
        ReviewApiResponse review = CollectionUtils.isNotEmpty(photoBoothEntity.getReviewSet()) ? reviewApiMapper.toDto((ReviewEntity) photoBoothEntity.getReviewSet().toArray()[0]) : null;
        List<TagDTO> tagDTOList = CollectionUtils.isNotEmpty(tagList) ? tagMapper.toDtoList(tagList) : null;
        return PhotoBoothWithTagResponse.of(photoBooth, review, tagDTOList, isLike, distance);
    }
}
