package com.hot6.phopa.core.domain.photobooth.repository;

import com.hot6.phopa.core.domain.photobooth.model.entity.PhotoBoothLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoBoothLikeRepository extends JpaRepository<PhotoBoothLikeEntity, Long>, PhotoBoothLikeCustomRepository{
    PhotoBoothLikeEntity findOneByPhotoBoothIdAndUserId(Long photoBoothId, Long userId);
}