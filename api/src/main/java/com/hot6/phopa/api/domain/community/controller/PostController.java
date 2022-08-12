package com.hot6.phopa.api.domain.community.controller;

import com.hot6.phopa.api.domain.community.model.dto.PostApiDTO;
import com.hot6.phopa.api.domain.community.model.dto.PostApiDTO.PostApiResponse;
import com.hot6.phopa.api.domain.community.model.dto.PostApiDTO.PostCreateRequest;
import com.hot6.phopa.api.domain.community.model.dto.PostApiDTO.PostFilterForm;
import com.hot6.phopa.api.domain.community.model.dto.PostApiDTO.PostForm;
import com.hot6.phopa.api.domain.community.service.PostApiService;
import com.hot6.phopa.core.common.model.dto.PageableParam;
import com.hot6.phopa.core.common.model.dto.PageableResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/post", produces = "application/json")
@RequiredArgsConstructor
public class PostController {

    private final PostApiService postService;

    @GetMapping
    public List<PostApiResponse> getPosts(
    ) {
        return postService.getPosts();
    }

    @GetMapping("/{postId}")
    public PostApiResponse getPost(
            @PathVariable @Positive Long postId
    ) {
        return postService.getPost(postId);
    }


    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PostApiResponse createPost(
            @RequestPart PostCreateRequest postCreateRequest,
            @RequestPart List<MultipartFile> postImageList
    ) {
        return postService.createPost(postCreateRequest, postImageList);
    }

    @PostMapping("/{postId}/like")
    public void like(
            @PathVariable Long postId
    ) {
        postService.like(postId);
    }

    @GetMapping("/recommendation")
    public PageableResponse<PostApiResponse> getPostsByTag(
            @RequestParam("tagIdSet") Set<Long> tagIdSet,
            PageableParam pageable
    ) {
        return postService.getPostsByTagIdSet(tagIdSet, pageable);
    }

    @GetMapping("/filter")
    public PostFilterForm getFilterForm() {
        return postService.getFilterFormData();
    }

    @GetMapping("/form")
    public PostForm getFormData() {
        return postService.getFormData();
    }
}
