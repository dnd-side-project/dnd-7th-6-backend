package com.hot6.phopa.api.domain.review.model.dto;

import com.hot6.phopa.api.domain.user.model.dto.UserApiDTO.UserApiResponse;
import com.hot6.phopa.core.common.exception.ApplicationErrorException;
import com.hot6.phopa.core.common.exception.ApplicationErrorType;
import com.hot6.phopa.core.domain.photobooth.model.dto.PhotoBoothDTO;
import com.hot6.phopa.core.domain.review.model.dto.ReviewDTO;
import com.hot6.phopa.core.domain.tag.enumeration.TagType;
import com.hot6.phopa.core.domain.tag.model.dto.TagDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class ReviewApiDTO {
    @Getter
    @Setter
    @AllArgsConstructor
    public static class ReviewApiResponse extends ReviewDTO {
        Set<ReviewTagApiResponse> reviewTagSet;
        Set<ReviewImageResponse> reviewImageSet;
        PhotoBoothDTO photoBooth;
        UserApiResponse user;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReviewCreateRequest {
        private String title;
        private String content;
        private List<Long> tagIdList;
        private List<String> newTagKeywordList;
        private Long photoBoothId;
        private Float starScore;

        public void validCheck() {
            Optional.ofNullable(this.getTitle()).orElseThrow(() -> new ApplicationErrorException(ApplicationErrorType.INVALID_REQUEST));
            Optional.ofNullable(this.getContent()).orElseThrow(() -> new ApplicationErrorException(ApplicationErrorType.INVALID_REQUEST));
            Optional.ofNullable(this.getPhotoBoothId()).orElseThrow(() -> new ApplicationErrorException(ApplicationErrorType.INVALID_REQUEST));
            Optional.ofNullable(this.getStarScore()).orElseThrow(() -> new ApplicationErrorException(ApplicationErrorType.INVALID_REQUEST));
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReviewUpdateRequest {
        private String title;
        private String content;
        private List<Long> tagIdList;
        private List<String> newTagKeywordList;
        private Float starScore;
        private List<Long> deleteImageIdList;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ReviewTagApiResponse {
        private TagDTO tag;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ReviewImageResponse {
        private Long id;
        private String imageUrl;
        private Integer imageOrder;
        private boolean isLike;
    }
    @Getter
    @Setter
    @AllArgsConstructor(staticName = "of")
    public static class ReviewFormResponse {
        Map<TagType, List<TagDTO>> reviewTagMap;
    }
}


