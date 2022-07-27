package com.hot6.phopa.core.domain.tag.repository;

import com.hot6.phopa.core.domain.tag.model.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<TagEntity, Long> {
    List<TagEntity> findAllByIsPhotoBoothIsTrue();

    TagEntity findOneByTagAndIsPhotoBoothIsTrue(String tag);
}