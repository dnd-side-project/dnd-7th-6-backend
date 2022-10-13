package com.hot6.phopa.core.domain.community.repository;

import com.hot6.phopa.core.domain.community.model.entity.PostReportCountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostReportCountRepository extends JpaRepository<PostReportCountEntity, Long> {
    PostReportCountEntity findOneByPostId(Long postId);
}
