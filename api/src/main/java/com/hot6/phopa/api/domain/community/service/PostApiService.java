package com.hot6.phopa.api.domain.community.service;

import com.hot6.phopa.api.domain.community.model.dto.PostApiDTO.PostApiResponse;
import com.hot6.phopa.api.domain.community.model.dto.PostApiDTO.PostCreateRequest;
import com.hot6.phopa.api.domain.community.model.mapper.PostApiMapper;
import com.hot6.phopa.core.common.exception.ApplicationErrorException;
import com.hot6.phopa.core.common.exception.ApplicationErrorType;
import com.hot6.phopa.core.common.model.type.Status;
import com.hot6.phopa.core.common.service.S3UploadService;
import com.hot6.phopa.core.domain.community.model.entity.PostEntity;
import com.hot6.phopa.core.domain.community.model.entity.PostImageEntity;
import com.hot6.phopa.core.domain.community.model.entity.PostLikeEntity;
import com.hot6.phopa.core.domain.community.model.entity.PostTagEntity;
import com.hot6.phopa.core.domain.community.service.PostService;
import com.hot6.phopa.core.domain.tag.model.entity.TagEntity;
import com.hot6.phopa.core.domain.tag.service.TagService;
import com.hot6.phopa.core.domain.user.model.entity.UserEntity;
import com.hot6.phopa.core.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PostApiService {

    private final PostService postService;

    private final PostApiMapper postApiMapper;

    private final S3UploadService s3UploadService;

    private final UserService userService;

    private final TagService tagService;

    @Value("${cloud.aws.s3.upload.path.review}")
    private String reviewPath;

    @Transactional(readOnly = true)
    public List<PostApiResponse> getPosts() {
        List<PostEntity> postEntityList = postService.getAllPost();
        return postApiMapper.toDtoList(postEntityList);
    }

    public PostApiResponse createPost(PostCreateRequest postCreateRequest, List<MultipartFile> postImageList) {
        postCreateRequest.validCheck();
        fileInvalidCheck(postImageList);
        UserEntity userEntity = userService.findById(postCreateRequest.getUserId());
        PostEntity postEntity = PostEntity.builder()
                .title(postCreateRequest.getTitle())
                .content(postCreateRequest.getContent())
                .likeCount(0)
                .status(Status.ACTIVE)
                .user(userEntity)
                .build();
        if (CollectionUtils.isNotEmpty(postCreateRequest.getTagIdList())) {
            Set<PostTagEntity> postTagEntitySet = new HashSet<>();
            List<TagEntity> tagEntityList = tagService.getTagList(postCreateRequest.getTagIdList());
            for (TagEntity tagEntity : tagEntityList) {
                postTagEntitySet.add(
                        PostTagEntity.builder()
                                .post(postEntity)
                                .tag(tagEntity)
                                .build()
                );
            }
            postEntity.setPostTagSet(postTagEntitySet);
        }

        if (CollectionUtils.isNotEmpty(postImageList)) {
            Set<PostImageEntity> postImageEntitySet = new HashSet<>();
            int index = 1;
            try {
                for (MultipartFile reviewImage : postImageList) {
                    String imageUrl = s3UploadService.uploadFiles(reviewImage, reviewPath);
                    postImageEntitySet.add(
                            PostImageEntity.builder()
                                    .post(postEntity)
                                    .imageUrl(imageUrl)
                                    .imageOrder(index++).
                                    build()
                    );
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            postEntity.setPostImageSet(postImageEntitySet);
        }

        return postApiMapper.toDto(postService.createPost(postEntity));
    }

    public void fileInvalidCheck(List<MultipartFile> imageList) {
        if (CollectionUtils.isNotEmpty(imageList)) {
            for (MultipartFile file : imageList) {
                if (Arrays.asList("jpg", "jpeg", "png").contains(FilenameUtils.getExtension(file.getOriginalFilename())) == false) {
                    throw new ApplicationErrorException(ApplicationErrorType.INVALID_REQUEST);
                }
            }
        }
    }

    public void like(Long postId, Long userId) {
        PostEntity postEntity = postService.getPostById(postId);
        UserEntity userEntity = userService.findById(userId);
        PostLikeEntity postLikeEntity = postService.getPostLikeByPostIdAndUserId(postId, userId);
        if (postLikeEntity != null){
            postService.deletePostLikeEntity(postLikeEntity);
            postEntity.updateLikeCount(-1);
        } else {
            postLikeEntity = PostLikeEntity.builder()
                    .post(postEntity)
                    .user(userEntity)
                    .build();
            postService.createPostLikeEntity(postLikeEntity);
            postEntity.updateLikeCount(1);
        }
    }

    public PostApiResponse getPost(Long postId) {
        return postApiMapper.toDto(postService.getPostById(postId));
    }

    public List<PostApiResponse> getPostsByTag(Long tagId) {
        return postApiMapper.toDtoList(postService.getPostByTag(tagId));
    }
}
