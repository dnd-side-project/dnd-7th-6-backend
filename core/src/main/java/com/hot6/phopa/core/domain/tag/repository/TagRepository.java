package com.hot6.phopa.core.domain.tag.repository;

import com.hot6.phopa.core.domain.tag.model.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<TagEntity, Long> {
}