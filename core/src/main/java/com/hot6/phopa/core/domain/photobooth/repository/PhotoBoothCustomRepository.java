package com.hot6.phopa.core.domain.photobooth.repository;

import com.hot6.phopa.core.domain.photobooth.model.entity.PhotoBoothEntity;
import org.locationtech.jts.geom.Point;

import java.util.List;
import java.util.Set;

public interface PhotoBoothCustomRepository {
    List<PhotoBoothEntity> findByPointSet(Set<Point> crawlingPointSet);

    List<PhotoBoothEntity> findAllByUserLike(Long userId);

    PhotoBoothEntity findByIdWithTag(Long photoBoothId);
}
