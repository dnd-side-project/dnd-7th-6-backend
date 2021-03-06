package com.hot6.phopa.api.domain.review.model.dto;

import com.hot6.phopa.core.common.exception.ApplicationErrorException;
import com.hot6.phopa.core.common.exception.ApplicationErrorType;
import com.hot6.phopa.core.domain.tag.model.dto.TagDTO;
import com.hot6.phopa.core.domain.user.model.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class PostApiDTO {
    @Getter
    @Setter
    @AllArgsConstructor
    public static class PostApiResponse extends PostApiDTO {
        Set<PostTagApiResponse> postTagSet;
        Set<PostImageResponse> postImageSet;
        UserDTO user;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PostCreateRequest {
        private String title;
        private String content;
        private List<Long> tagIdList;
        private Long userId;

        public void validCheck() {
            Optional.ofNullable(this.getTitle()).orElseThrow(() -> new ApplicationErrorException(ApplicationErrorType.INVALID_REQUEST));
            Optional.ofNullable(this.getContent()).orElseThrow(() -> new ApplicationErrorException(ApplicationErrorType.INVALID_REQUEST));
            Optional.ofNullable(this.getUserId()).orElseThrow(() -> new ApplicationErrorException(ApplicationErrorType.INVALID_REQUEST));
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class PostTagApiResponse {
        private TagDTO tag;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class PostImageResponse {
        private Long id;
        private String imageUrl;
        private Integer imageOrder;
    }
}


