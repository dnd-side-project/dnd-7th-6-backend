package com.hot6.phopa.core.domain.photobooth.service;

import com.hot6.phopa.core.common.exception.ApplicationErrorType;
import com.hot6.phopa.core.common.exception.SilentApplicationErrorException;
import com.hot6.phopa.core.common.model.dto.PageableParam;
import com.hot6.phopa.core.common.model.type.Status;
import com.hot6.phopa.core.common.utils.PointUtil;
import com.hot6.phopa.core.domain.map.service.KakaoMapService;
import com.hot6.phopa.core.domain.photobooth.dto.PhotoBoothNativeQueryDTO;
import com.hot6.phopa.core.domain.photobooth.model.dto.PhotoBoothWithDistanceDTO;
import com.hot6.phopa.core.domain.photobooth.model.entity.PhotoBoothEntity;
import com.hot6.phopa.core.domain.photobooth.model.entity.PhotoBoothLikeEntity;
import com.hot6.phopa.core.domain.photobooth.repository.PhotoBoothLikeRepository;
import com.hot6.phopa.core.domain.photobooth.repository.PhotoBoothRepository;
import com.hot6.phopa.core.domain.tag.model.entity.TagEntity;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PhotoBoothService {

    private final EntityManager em;

    private final PhotoBoothRepository photoBoothRepository;

    private final KakaoMapService kakaoMapService;

    private final PhotoBoothLikeRepository photoBoothLikeRepository;

    @Transactional(readOnly = true)
    //    distance 1 = 1m
    public PhotoBoothWithDistanceDTO getPhotoBoothNearByUserGeo(Double latitude, Double longitude, Double distance, Status status, Set<Long> tagIdSet, PageableParam pageable) {
        List<PhotoBoothNativeQueryDTO> photoBoothNativeQueryDTOList = photoBoothRepository.findIdsByGeo(latitude, longitude, distance / 100);
        Map<Long, Double> photoBoothIdDistanceMap = photoBoothNativeQueryDTOList.stream().collect(Collectors.toMap(PhotoBoothNativeQueryDTO::getId, p -> (p.getDistance() * 100)));
        List<Long> photoBoothIdList = photoBoothNativeQueryDTOList.stream().sorted(Comparator.comparingDouble(PhotoBoothNativeQueryDTO::getDistance)).map(PhotoBoothNativeQueryDTO::getId).collect(Collectors.toList());
        Page<PhotoBoothEntity> photoBoothEntityPage = photoBoothRepository.findByPhotoBoothIdAndColumn(photoBoothIdList, status, tagIdSet, pageable);
        return PhotoBoothWithDistanceDTO.of(photoBoothIdDistanceMap, photoBoothEntityPage);
    }

    @Transactional(readOnly = true)
    //    distance 1 = 1m
    public Integer getPhotoBoothNearByUserGeoCount(Double latitude, Double longitude, Double distance, Status status, Set<Long> tagIdSet) {
        List<PhotoBoothNativeQueryDTO> photoBoothNativeQueryDTOList = photoBoothRepository.findIdsByGeo(latitude, longitude, distance / 100);
        List<Long> photoBoothIdList = photoBoothNativeQueryDTOList.stream().map(PhotoBoothNativeQueryDTO::getId).collect(Collectors.toList());
        return photoBoothRepository.getCountByPhotoBoothIdAndColumn(photoBoothIdList, status, tagIdSet);
    }

    @Transactional(readOnly = true)
    public PhotoBoothEntity getPhotoBooth(Long photoBoothId) {
        return photoBoothRepository.findById(photoBoothId).orElseThrow(() -> new SilentApplicationErrorException(ApplicationErrorType.COULDNT_FIND_ANY_DATA));
    }

    public List<PhotoBoothEntity> kakaoMapTest(String keyword, Double latitude, Double longitude, Double distance, TagEntity tagEntity) {
        List<PhotoBoothEntity> photoBoothEntityList = kakaoMapService.crawlingPhotoBoothData(keyword, latitude, longitude, distance);
        Set<PointUtil> crawlingPointSet = photoBoothEntityList.stream().map(photoBoothEntity -> PointUtil.of(photoBoothEntity.getLatitude(), photoBoothEntity.getLongitude())).collect(Collectors.toSet());
        Map<Double, Double> alreadyPointMap = photoBoothRepository.findByPointSet(crawlingPointSet).stream().collect(Collectors.toMap(PhotoBoothEntity::getLatitude, PhotoBoothEntity::getLongitude));
        List<PhotoBoothEntity> savePhotoBoothEntityList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(photoBoothEntityList)) {
            for (PhotoBoothEntity photoBoothEntity : photoBoothEntityList) {
                if (alreadyPointMap.get(photoBoothEntity.getLatitude()) == null || (alreadyPointMap.get(photoBoothEntity.getLatitude()).doubleValue() == photoBoothEntity.getLongitude()) == false) {
                    photoBoothEntity.setTag(tagEntity);
                    savePhotoBoothEntityList.add(photoBoothEntity);
                }
            }
            tagEntity.updatePhotoBoothCount(photoBoothEntityList.size());
        }
        return photoBoothRepository.saveAll(savePhotoBoothEntityList);
    }

    @Transactional(readOnly = true)
    public PhotoBoothEntity getPhotoBoothById(Long photoBoothId) {
        return photoBoothRepository.findById(photoBoothId).orElseThrow(() -> new SilentApplicationErrorException(ApplicationErrorType.COULDNT_FIND_ANY_DATA));
    }

    public PhotoBoothLikeEntity createPhotoBoothLikeEntity(PhotoBoothLikeEntity photoBoothLikeEntity) {
        return photoBoothLikeRepository.save(photoBoothLikeEntity);
    }

    public void deletePhotoBoothLikeEntity(PhotoBoothLikeEntity photoBoothLikeEntity) {
        photoBoothLikeRepository.delete(photoBoothLikeEntity);
    }

    @Transactional(readOnly = true)
    public PhotoBoothLikeEntity getPhotoBoothLikeByPhotoBoothIdAndUserId(Long photoBoothId, Long userId) {
        return photoBoothLikeRepository.findOneByPhotoBoothIdAndUserId(photoBoothId, userId);
    }

    @Transactional(readOnly = true)
    public List<PhotoBoothLikeEntity> getPhotoBoothLikeByPhotoBoothIdListAndUserId(List<Long> photoBoothIdList, Long userId) {
        return photoBoothLikeRepository.findAllByPhotoBoothIdListAndUserId(photoBoothIdList, userId);
    }
    @Transactional(readOnly = true)
    public List<PhotoBoothEntity> findAllByUserLike(Long userId) {
        return photoBoothRepository.findAllByUserLike(userId);
    }
    @Transactional(readOnly = true)
    public List<PhotoBoothLikeEntity> findPhotoBoothLikeByUserId(Long userId) {
        return photoBoothLikeRepository.findAllByUserId(userId);
    }
}
