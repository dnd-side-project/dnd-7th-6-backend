package com.hot6.phopa.core.domain.community.service;

import com.hot6.phopa.core.common.exception.ApplicationErrorType;
import com.hot6.phopa.core.common.exception.SilentApplicationErrorException;
import com.hot6.phopa.core.common.model.dto.PageableParam;
import com.hot6.phopa.core.domain.community.model.entity.PostEntity;
import com.hot6.phopa.core.domain.community.model.entity.PostLikeEntity;
import com.hot6.phopa.core.domain.community.repository.PostLikeRepository;
import com.hot6.phopa.core.domain.community.repository.PostRepository;
import com.hot6.phopa.core.domain.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final PostLikeRepository postLikeRepository;

    @Transactional(readOnly = true)
    public List<PostEntity> getAllPost() {
        return postRepository.findAll();
    }

    public PostEntity createPost(PostEntity postEntity) {
        return postRepository.save(postEntity);
    }

    @Transactional(readOnly = true)
    public PostEntity getPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new SilentApplicationErrorException(ApplicationErrorType.COULDNT_FIND_ANY_DATA));
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

    public Page<PostEntity> getPostByTagIdSet(Set<Long> tagIdSet, PageableParam pageable) {
        return postRepository.getPostByTagIdSet(tagIdSet, pageable);
    }

    public Page<PostEntity> getPostByTagIdSetOrderByLikeCountDesc(Set<Long> tagIdSet, PageableParam pageable) {
        return postRepository.getPostByTagIdSetOrderByLikeCountDesc(tagIdSet, pageable);
    }

    public Page<PostEntity> getPostByTagIdSetOrderByCreatedAtDesc(Set<Long> tagIdSet, PageableParam pageable) {
        return postRepository.getPostByTagIdSetOrderByCreatedAtDesc(tagIdSet, pageable);
    }

    public List<PostEntity> findAllByUserId(Long userId) {
        return postRepository.getAllByUserId(userId);
    }
}
