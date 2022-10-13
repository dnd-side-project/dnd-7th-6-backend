package com.hot6.phopa.core.domain.community.service;

import com.hot6.phopa.core.common.exception.ApplicationErrorType;
import com.hot6.phopa.core.common.exception.SilentApplicationErrorException;
import com.hot6.phopa.core.common.model.dto.PageableParam;
import com.hot6.phopa.core.common.model.type.Status;
import com.hot6.phopa.core.domain.community.enumeration.OrderType;
import com.hot6.phopa.core.domain.community.model.entity.PostEntity;
import com.hot6.phopa.core.domain.community.model.entity.PostImageEntity;
import com.hot6.phopa.core.domain.community.model.entity.PostLikeEntity;
import com.hot6.phopa.core.domain.community.model.entity.PostReportCountEntity;
import com.hot6.phopa.core.domain.community.model.entity.UserReportPostEntity;
import com.hot6.phopa.core.domain.community.repository.PostImageRepository;
import com.hot6.phopa.core.domain.community.repository.PostLikeRepository;
import com.hot6.phopa.core.domain.community.repository.PostReportCountRepository;
import com.hot6.phopa.core.domain.community.repository.PostRepository;
import com.hot6.phopa.core.domain.community.repository.UserReportPostRepository;
import com.hot6.phopa.core.domain.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final PostImageRepository postImageRepository;
    private final PostReportCountRepository postReportCountRepository;
    private final UserReportPostRepository userReportPostRepository;

    @Transactional(readOnly = true)
    public Page<PostEntity> getPosts(Long userId, Long photoBoothId, PageableParam pageable) {
        return postRepository.findPost(userId, photoBoothId, pageable);
    }

    public PostEntity createPost(PostEntity postEntity) {
        return postRepository.save(postEntity);
    }

    @Transactional(readOnly = true)
    public PostEntity getPostById(Long postId) {
        return postRepository.findByIdAndStatusAndAndIsPublic(postId, Status.ACTIVE, true).orElseThrow(() -> new SilentApplicationErrorException(ApplicationErrorType.COULDNT_FIND_ANY_DATA));
    }

    public PostLikeEntity createPostLikeEntity(PostLikeEntity postLikeEntity) {
        return postLikeRepository.save(postLikeEntity);
    }

    public void deletePostLikeEntity(PostLikeEntity postLikeEntity) {
        postLikeRepository.delete(postLikeEntity);
    }

    @Transactional(readOnly = true)
    public PostLikeEntity getPostLikeByPostIdAndUserId(Long postId, Long userId) {
        return postLikeRepository.findOneByPostIdAndUserId(postId, userId);
    }

    public List<PostEntity> findAllByUserLike(Long userId) {
        return postRepository.findAllByUserLike(userId);
    }

    public Page<PostEntity> getPostByTagIdSet(Set<Long> tagIdSet, OrderType order, PageableParam pageable) {
        return postRepository.getPostByTagIdSet(tagIdSet, order, pageable);
    }

    public List<PostEntity> findAllByUserId(Long userId) {
        return postRepository.getAllByUserIdAndStatus(userId, Status.ACTIVE);
    }

    public List<PostLikeEntity> getPostLikeByPostIdsAndUserId(List<Long> postIdList, Long userId) {
        return postLikeRepository.findAllByPostIdsAndUserId(postIdList, userId);
    }

    public List<PostLikeEntity> getPostLikeBydUserId(Long userId) {
        return postLikeRepository.findAllByUserId(userId);
    }

    public List<PostImageEntity> getPostImageByUserLike(Long userId) {
        return postImageRepository.findPostImageByUserLike(userId);
    }

    @Transactional(readOnly = true)
    public PostReportCountEntity getReportCountByPostId(Long postId) {
        return postReportCountRepository.findOneByPostId(postId);
    }

    @Transactional(readOnly = true)
    public UserReportPostEntity getReportUserByPostIdAndUserId(Long postId, Long userId) {
        return userReportPostRepository.findOneByPostIdAndUserId(postId, userId);
    }

    @Transactional(readOnly = true)
    public boolean isExistReportUser(Long postId, Long userId) {
        return userReportPostRepository.existsByPostIdAndUserId(postId, userId);
    }

    public PostReportCountEntity createPostReportCountEntity(PostReportCountEntity postReportCountEntity) {
        return postReportCountRepository.save(postReportCountEntity);
    }

    public UserReportPostEntity createUserReportPostEntity(UserReportPostEntity userReportPostEntity) {
        return userReportPostRepository.save(userReportPostEntity);
    }
}
