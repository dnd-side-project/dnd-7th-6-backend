package com.hot6.phopa.core.domain.photobooth.repository;

import com.hot6.phopa.core.domain.photobooth.model.entity.PhotoBoothEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoBoothRepository extends JpaRepository<PhotoBoothEntity, Long> {
}
