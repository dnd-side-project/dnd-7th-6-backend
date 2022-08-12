package com.hot6.phopa.core.domain.community.repository;

import com.hot6.phopa.core.common.model.type.Status;
import com.hot6.phopa.core.domain.community.model.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long>, PostCustomRepository {
    List<PostEntity> getAllByUserIdAndStatus(Long userId, Status status);
}
