package com.hot6.phopa.api.domain.community.service;

import com.hot6.phopa.api.domain.community.model.dto.PostApiDTO;
import com.hot6.phopa.api.domain.community.model.dto.PostApiDTO.*;
import com.hot6.phopa.api.domain.community.model.mapper.PostApiMapper;
import com.hot6.phopa.api.domain.review.model.dto.ReviewApiDTO;
import com.hot6.phopa.core.common.exception.ApplicationErrorException;
import com.hot6.phopa.core.common.exception.ApplicationErrorType;
import com.hot6.phopa.core.common.exception.SilentApplicationErrorException;
import com.hot6.phopa.core.common.model.dto.PageableParam;
import com.hot6.phopa.core.common.model.dto.PageableResponse;
import com.hot6.phopa.core.common.model.type.Status;
import com.hot6.phopa.core.common.service.S3UploadService;
import com.hot6.phopa.core.domain.community.enumeration.OrderType;
import com.hot6.phopa.core.domain.community.model.entity.PostEntity;
import com.hot6.phopa.core.domain.community.model.entity.PostImageEntity;
import com.hot6.phopa.core.domain.community.model.entity.PostLikeEntity;
import com.hot6.phopa.core.domain.community.model.entity.PostTagEntity;
import com.hot6.phopa.core.domain.community.service.PostService;
import com.hot6.phopa.core.domain.tag.enumeration.TagType;
import com.hot6.phopa.core.domain.tag.model.dto.TagDTO;
import com.hot6.phopa.core.domain.tag.model.entity.TagEntity;
import com.hot6.phopa.core.domain.tag.model.mapper.TagMapper;
import com.hot6.phopa.core.domain.tag.service.TagService;
import com.hot6.phopa.core.domain.user.model.dto.UserDTO;
import com.hot6.phopa.core.domain.user.model.entity.UserEntity;
import com.hot6.phopa.core.domain.user.service.UserService;
import com.hot6.phopa.core.security.config.PrincipleDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    private final TagMapper tagMapper;

    @Value("${cloud.aws.s3.upload.path.review}")
    private String reviewPath;

    @Transactional(readOnly = true)
    public PageableResponse<PostApiResponse> getPosts(Long userId, Long photoBoothId, PageableParam pageable) {
        Page<PostEntity> postEntityPage = postService.getPosts(userId, photoBoothId, pageable);
        List<PostApiResponse> postApiResponseList = postApiMapper.toDtoList(postEntityPage.getContent());
        UserDTO userDTO = PrincipleDetail.get();
        if(userDTO.getId() != null){
            postApiResponseList = setUserLike(postApiResponseList, userDTO.getId());
        }
        return PageableResponse.makeResponse(postEntityPage, postApiResponseList);
    }

    public PostApiResponse createPost(PostCreateRequest postCreateRequest, List<MultipartFile> postImageList) {
        UserDTO userDTO = PrincipleDetail.get();
        UserEntity userEntity = userDTO.getId() != null ? userService.findById(userDTO.getId()) : null;
        postCreateRequest.validCheck();
        fileInvalidCheck(postImageList);
        List<TagEntity> newTagList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(postCreateRequest.getNewTagList())){
            newTagList = tagService.createCustomTagList(postCreateRequest.getNewTagList());
        }
        PostEntity postEntity = PostEntity.builder()
                .title(postCreateRequest.getTitle())
                .content(postCreateRequest.getContent())
                .likeCount(0)
                .status(Status.ACTIVE)
                .isPublic(postCreateRequest.getIsPublic())
                .user(userEntity)
                .build();
        if (CollectionUtils.isNotEmpty(postCreateRequest.getTagIdList())) {
            Set<PostTagEntity> postTagEntitySet = new HashSet<>();
            List<TagEntity> tagEntityList = tagService.getTagList(postCreateRequest.getTagIdList());
            tagEntityList.addAll(newTagList);
            for (TagEntity tagEntity : tagEntityList) {
                postTagEntitySet.add(
                        PostTagEntity.builder()
                                .post(postEntity)
                                .tag(tagEntity)
                                .build()
                );
                tagEntity.updatePostCount(1);
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
                                    .imageOrder(index).
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
                if (Arrays.asList("jpg", "jpeg", "png", "gif", "JPG", "JPEG", "PNG", "GIF").contains(FilenameUtils.getExtension(file.getOriginalFilename())) == false) {
                    throw new ApplicationErrorException(ApplicationErrorType.INVALID_REQUEST);
                }
            }
        }
    }

    public void like(Long postId) {
        UserDTO userDTO = PrincipleDetail.get();
        UserEntity userEntity = userDTO.getId() != null ? userService.findById(userDTO.getId()) : null;
        PostEntity postEntity = postService.getPostById(postId);
        PostLikeEntity postLikeEntity = postService.getPostLikeByPostIdAndUserId(postId, userEntity.getId());
        if (postLikeEntity != null) {
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
        PostApiResponse postApiResponse = postApiMapper.toDto(postService.getPostById(postId));
        UserDTO userDTO = PrincipleDetail.get();
        boolean isLike = false;
        if(userDTO.getId() != null){
            isLike = postService.getPostLikeByPostIdAndUserId(postApiResponse.getId(), userDTO.getId()) != null;
        }
        postApiResponse.setLike(isLike);
        return postApiResponse;
    }


    public PageableResponse<PostApiResponse> getPostsByTagIdSet(
            Set<Long> tagIdSet,
            OrderType order,
            PageableParam pageable
    ) {
        Page<PostEntity> postEntityPage = postService.getPostByTagIdSet(tagIdSet, order, pageable);
        List<PostApiResponse> postApiResponseList = postApiMapper.toDtoList(postEntityPage.getContent());
        UserDTO userDTO = PrincipleDetail.get();
        if(userDTO.getId() != null){
            postApiResponseList = setUserLike(postApiResponseList, userDTO.getId());
        }
        return PageableResponse.makeResponse(postEntityPage, postApiResponseList);
    }

    public PostFilterForm getFilterFormData() {
        List<TagDTO> tagDTOList = tagMapper.toDtoList(tagService.getTagListByTagTypeList(TagType.POST_TAG_LIST, null));
        List<TagDTO> brandTagList = new ArrayList<>();
        Map<TagType, List<TagDTO>> personalTagList = new HashMap<>();
        Map<TagType, List<TagDTO>> conceptTagList = new HashMap<>();
        List<TagDTO> frameTagList = new ArrayList<>();
        Map<TagType, List<TagDTO>> tagTypeListMap = tagDTOList.stream().collect(Collectors.groupingBy(TagDTO::getTagType));
        for (Map.Entry<TagType, List<TagDTO>> entry : tagTypeListMap.entrySet()) {
            if (TagType.BRAND.equals(entry.getKey())) {
                brandTagList = entry.getValue();
            } else if (TagType.FRAME.equals(entry.getKey())) {
                frameTagList = entry.getValue();
            } else if (TagType.PERSONAL_TAG_LIST.contains(entry.getKey())) {
                personalTagList.put(entry.getKey(), entry.getValue());
            } else if (TagType.CONCEPT_TAG_LIST.contains(entry.getKey())) {
                conceptTagList.put(entry.getKey(), entry.getValue());
            }
        }
        return PostFilterForm.of(brandTagList, personalTagList, conceptTagList, frameTagList);
    }

    public PostForm getFormData() {
        List<TagDTO> tagDTOList = tagMapper.toDtoList(tagService.getTagListByTagTypeList(TagType.POST_TAG_LIST, null));
        Map<TagType, List<TagDTO>> tagTypeListMap = tagDTOList.stream().collect(Collectors.groupingBy(TagDTO::getTagType));
        return PostForm.of(tagTypeListMap);
    }

    public void inactivePost(Long postId) {
        UserDTO userDTO = PrincipleDetail.get();
        UserEntity userEntity = userDTO.getId() != null ? userService.findById(userDTO.getId()) : null;
        if (userEntity == null) {
            throw new SilentApplicationErrorException(ApplicationErrorType.COULDNT_FIND_ANY_DATA);
        }
        PostEntity postEntity = postService.getPostById(postId);
        if (postEntity.getUser().getId() != userEntity.getId()) {
            throw new SilentApplicationErrorException(ApplicationErrorType.DIFF_USER);
        }
        postEntity.updateStatus(Status.INACTIVE);

    }

    public PostApiResponse modifyPost(Long postId, PostUpdateRequest postUpdateRequest, List<MultipartFile> postImageList) {
        fileInvalidCheck(postImageList);
        UserDTO userDTO = PrincipleDetail.get();
        UserEntity userEntity = userDTO.getId() != null ? userService.findById(userDTO.getId()) : null;
        if (userEntity == null) {
            throw new SilentApplicationErrorException(ApplicationErrorType.COULDNT_FIND_ANY_DATA);
        }
        PostEntity postEntity = postService.getPostById(postId);
        if (postEntity.getUser().getId() != userEntity.getId()) {
            throw new SilentApplicationErrorException(ApplicationErrorType.DIFF_USER);
        }
        if (CollectionUtils.isNotEmpty(postUpdateRequest.getTagIdList())) {
            updateTagList(postEntity, postUpdateRequest.getTagIdList());
        }
        //이미지 수정되었을 경우, 이전 이미지 지움.
        if (CollectionUtils.isNotEmpty(postUpdateRequest.getDeleteImageIdList())) {
            postEntity.deleteImage(postUpdateRequest.getDeleteImageIdList());
        }
        //수정된 이미지가 있을 경우, 새로 생성.
        if (CollectionUtils.isNotEmpty(postImageList)) {
            updateImageList(postEntity, postImageList);
        }
        Optional.ofNullable(postUpdateRequest.getTitle()).ifPresent(title -> postEntity.updateTitle(title));
        Optional.ofNullable(postUpdateRequest.getContent()).ifPresent(content -> postEntity.updateContent(content));
        return postApiMapper.toDto(postEntity);
    }

    private void updateImageList(PostEntity postEntity, List<MultipartFile> postImageList) {
        int index = 1;
        try {
            for (MultipartFile reviewImage : postImageList) {
                String imageUrl = s3UploadService.uploadFiles(reviewImage, reviewPath);
                postEntity.getPostImageSet().add(
                        PostImageEntity.builder()
                                .post(postEntity)
                                .imageUrl(imageUrl)
                                .imageOrder(index).
                                build()
                );
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private void updateTagList(PostEntity postEntity, List<Long> tagIdList) {
        Map<Long, PostTagEntity> tagIdPostTagMap = postEntity.getPostTagSet().stream().collect(Collectors.toMap(postTag -> postTag.getTag().getId(), Function.identity()));
        // postEntity에 없는 tagIdList
        List<Long> newTagIdList = tagIdList.stream().filter(tagId -> tagIdPostTagMap.containsKey(tagId) == false).collect(Collectors.toList());
        // postEntity에 있지만, request에 없는 tag인 경우 제거
        Set<PostTagEntity> deletePostTagSet = postEntity.getPostTagSet().stream().filter(postTag -> tagIdList.contains(postTag.getTag().getId()) == false).collect(Collectors.toSet());
        for (PostTagEntity postTagEntity : deletePostTagSet) {
            postTagEntity.getTag().updatePostCount(-1);
            postEntity.getPostTagSet().remove(postTagEntity);
        }
        List<TagEntity> tagEntityList = tagService.getTagList(newTagIdList);
        for (TagEntity tagEntity : tagEntityList) {
            postEntity.getPostTagSet().add(
                    PostTagEntity.builder()
                            .post(postEntity)
                            .tag(tagEntity)
                            .build()
            );
            tagEntity.updatePostCount(1);
        }
    }
    private List<PostApiResponse> setUserLike(List<PostApiResponse> postApiResponseList, Long userId){
        List<Long> postIdList = postApiResponseList
                .stream()
                .map(PostApiResponse::getId)
                .collect(Collectors.toList());

        List<Long> userLikePostIdList = postService.getPostLikeByPostIdsAndUserId(postIdList, userId)
                .stream()
                .map(postLike -> postLike.getPost().getId())
                .collect(Collectors.toList());

        postApiResponseList.forEach(post -> post.setLike(userLikePostIdList.contains(post.getId())));
        return postApiResponseList;
    }
}
