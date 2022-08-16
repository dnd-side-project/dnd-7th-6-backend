package com.hot6.phopa.core.domain.community.repository;

import com.hot6.phopa.core.domain.community.model.entity.PostLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLikeEntity, Long> {
    PostLikeEntity findOneByPostIdAndUserId(Long postId, Long userId);
    @Query("SELECT postLike FROM PostLikeEntity postLike where postLike.user.id = :userId and postLike.post.id in :postIdList and postLike.post.status = 'ACTIVE'")
    List<PostLikeEntity> findAllByPostIdsAndUserId(@Param("postIdList") List<Long> postIdList, @Param("userId") Long userId);

    @Query("SELECT postLike FROM PostLikeEntity postLike where postLike.user.id = :userId and postLike.post.status = 'ACTIVE'")
    List<PostLikeEntity> findAllByUserId(@Param("userId") Long userId);
}
