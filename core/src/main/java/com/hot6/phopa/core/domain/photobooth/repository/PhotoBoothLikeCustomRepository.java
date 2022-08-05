package com.hot6.phopa.core.domain.photobooth.repository;

import com.hot6.phopa.core.domain.photobooth.model.entity.PhotoBoothLikeEntity;

import java.util.List;
import java.util.Map;

public interface PhotoBoothLikeCustomRepository {
    List<PhotoBoothLikeEntity> findAllByPhotoBoothIdListAndUserId(List<Long> photoBoothIdList, Long userId);
}
