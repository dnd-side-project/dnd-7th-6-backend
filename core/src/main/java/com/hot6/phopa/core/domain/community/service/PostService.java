package com.hot6.phopa.core.domain.community.service;

import com.hot6.phopa.core.domain.community.model.entity.PostEntity;
import com.hot6.phopa.core.domain.community.model.entity.PostTagEntity;
import com.hot6.phopa.core.domain.community.repository.PostRepository;
import com.hot6.phopa.core.domain.review.repository.TagRepository;
import com.hot6.phopa.core.domain.tag.model.entity.TagEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final TagRepository tagRepository;

    public List<PostEntity> getAllPost() {
        return postRepository.findAll();
    }

    public PostEntity createPost(PostEntity postEntity) {
        return postRepository.save(postEntity);
    }
}
