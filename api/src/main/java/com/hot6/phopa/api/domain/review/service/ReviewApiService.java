package com.hot6.phopa.api.domain.review.service;

import com.hot6.phopa.api.domain.review.model.dto.ReviewApiDTO;
import com.hot6.phopa.api.domain.review.model.dto.ReviewApiDTO.ReviewApiResponse;
import com.hot6.phopa.api.domain.review.model.dto.ReviewApiDTO.ReviewCreateRequest;
import com.hot6.phopa.api.domain.review.model.dto.ReviewApiDTO.ReviewFormResponse;
import com.hot6.phopa.api.domain.review.model.mapper.ReviewApiMapper;
import com.hot6.phopa.core.common.exception.ApplicationErrorException;
import com.hot6.phopa.core.common.exception.ApplicationErrorType;
import com.hot6.phopa.core.common.model.dto.PageableParam;
import com.hot6.phopa.core.common.model.dto.PageableResponse;
import com.hot6.phopa.core.common.model.type.Status;
import com.hot6.phopa.core.common.service.S3UploadService;
import com.hot6.phopa.core.domain.photobooth.model.entity.PhotoBoothEntity;
import com.hot6.phopa.core.domain.photobooth.service.PhotoBoothService;
import com.hot6.phopa.core.domain.review.model.entity.ReviewEntity;
import com.hot6.phopa.core.domain.review.model.entity.ReviewImageEntity;
import com.hot6.phopa.core.domain.review.model.entity.ReviewLikeEntity;
import com.hot6.phopa.core.domain.review.model.entity.ReviewTagEntity;
import com.hot6.phopa.core.domain.review.service.ReviewService;
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
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ReviewApiService {

    private final ReviewService reviewService;

    private final PhotoBoothService photoBoothService;

    private final ReviewApiMapper reviewMapper;

    private final S3UploadService s3UploadService;

    private final UserService userService;

    private final TagService tagService;

    private final TagMapper tagMapper;

    @Value("${cloud.aws.s3.upload.path.review}")
    private String reviewPath;

    @Transactional(readOnly = true)
    public PageableResponse<ReviewApiResponse> getReview(long photoBoothId, PageableParam pageable) {
        Page<ReviewEntity> reviewEntityPage = reviewService.getReview(photoBoothId, pageable);
        List<ReviewApiResponse> reviewApiResponseList = reviewMapper.toDtoList(reviewEntityPage.getContent());
        return PageableResponse.makeResponse(reviewEntityPage, reviewApiResponseList);
    }

    public ReviewApiResponse createReview(ReviewCreateRequest reviewCreateRequest, List<MultipartFile> reviewImageList) {
        UserDTO userDTO = PrincipleDetail.get();
        UserEntity userEntity = userDTO.getId() != null ? userService.findById(userDTO.getId()) : null;
        reviewCreateRequest.validCheck();
        fileInvalidCheck(reviewImageList);
        PhotoBoothEntity photoBoothEntity = photoBoothService.getPhotoBooth(reviewCreateRequest.getPhotoBoothId());
        ReviewEntity reviewEntity = ReviewEntity.builder()
                .title(reviewCreateRequest.getTitle())
                .content(reviewCreateRequest.getContent())
                .likeCount(0)
                .status(Status.ACTIVE)
                .starScore(reviewCreateRequest.getStarScore())
                .user(userEntity)
                .photoBooth(photoBoothEntity)
                .build();
        if (CollectionUtils.isNotEmpty(reviewCreateRequest.getTagIdList())) {
            Set<ReviewTagEntity> reviewTagEntitySet = new HashSet<>();
            List<TagEntity> tagEntityList = tagService.getTagList(reviewCreateRequest.getTagIdList());
            for (TagEntity tagEntity : tagEntityList) {
                reviewTagEntitySet.add(
                        ReviewTagEntity.builder()
                                .review(reviewEntity)
                                .tag(tagEntity)
                                .build()
                );
                if (tagEntity.getReviewTagSet().stream().anyMatch(r -> r.getReview().getPhotoBooth().getId().equals(photoBoothEntity.getId())) == false) {
                    tagEntity.updatePhotoBoothCount(1);
                }
                tagEntity.updateReviewCount(1);
            }
            reviewEntity.setReviewTagSet(reviewTagEntitySet);
            photoBoothEntity.updateReviewCount(1);

            Integer reviewCount = photoBoothEntity.getReviewCount();
            photoBoothEntity.updateStarScore(((photoBoothEntity.getStarScore() * (reviewCount - 1)) + reviewCreateRequest.getStarScore()) / (reviewCount));
        }

        if (CollectionUtils.isNotEmpty(reviewImageList)) {
            Set<ReviewImageEntity> reviewImageEntitySet = new HashSet<>();
            int index = 1;
            try {
                for (MultipartFile reviewImage : reviewImageList) {
                    String imageUrl = s3UploadService.uploadFiles(reviewImage, reviewPath);
                    reviewImageEntitySet.add(
                            ReviewImageEntity.builder()
                                    .review(reviewEntity)
                                    .imageUrl(imageUrl)
                                    .imageOrder(index++).
                                    build()
                    );
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            reviewEntity.setReviewImageSet(reviewImageEntitySet);
        }

        return reviewMapper.toDto(reviewService.createReview(reviewEntity));
    }

    public void fileInvalidCheck(List<MultipartFile> imageList) {
        if (CollectionUtils.isNotEmpty(imageList)) {
            for (MultipartFile file : imageList) {
                if (Arrays.asList("jpg", "jpeg", "png", "JPG", "PNG", "JPEG", "gif").contains(FilenameUtils.getExtension(file.getOriginalFilename())) == false) {
                    throw new ApplicationErrorException(ApplicationErrorType.INVALID_REQUEST);
                }
            }
        }
    }

    public void like(Long reviewId) {
        UserDTO userDTO = PrincipleDetail.get();
        UserEntity userEntity = userDTO.getId() != null ? userService.findById(userDTO.getId()) : null;
        ReviewEntity reviewEntity = reviewService.getReviewById(reviewId);
        ReviewLikeEntity reviewLikeEntity = reviewService.getReviewLikeByReviewIdAndUserId(reviewId, userEntity.getId());
        if (reviewLikeEntity != null) {
            reviewService.deleteReviewLike(reviewLikeEntity);
            reviewEntity.updateLikeCount(-1);
        } else {
            reviewLikeEntity = ReviewLikeEntity.builder()
                    .review(reviewEntity)
                    .user(userEntity)
                    .build();
            reviewService.createReviewLikeEntity(reviewLikeEntity);
            reviewEntity.updateLikeCount(1);
        }
    }

    public ReviewFormResponse getFormData() {
        List<TagDTO> tagDTOList = tagMapper.toDtoList(tagService.getTagListByTagTypeList(TagType.REVIEW_FORM_TAG_LIST, null));
        Map<TagType, List<TagDTO>> reviewTagMap = tagDTOList.stream().collect(Collectors.groupingBy(TagDTO::getTagType));
        return ReviewFormResponse.of(reviewTagMap);
    }
}
