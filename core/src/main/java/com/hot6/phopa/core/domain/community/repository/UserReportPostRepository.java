package com.hot6.phopa.core.domain.community.repository;

import com.hot6.phopa.core.domain.community.model.entity.UserReportPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserReportPostRepository extends JpaRepository<UserReportPostEntity, Long> {
    UserReportPostEntity findOneByPostIdAndUserId(Long postId, Long userId);
    boolean existsByPostIdAndUserId(Long postId, Long userId);
}
