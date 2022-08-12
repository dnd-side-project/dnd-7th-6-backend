package com.hot6.phopa.api.domain.community.model.dto;

import com.hot6.phopa.api.domain.user.model.dto.UserApiDTO;
import com.hot6.phopa.api.domain.user.model.dto.UserApiDTO.UserApiResponse;
import com.hot6.phopa.core.common.exception.ApplicationErrorException;
import com.hot6.phopa.core.common.exception.ApplicationErrorType;
import com.hot6.phopa.core.domain.community.model.dto.PostDTO;
import com.hot6.phopa.core.domain.tag.enumeration.TagType;
import com.hot6.phopa.core.domain.tag.model.dto.TagDTO;
import com.hot6.phopa.core.domain.user.model.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class PostApiDTO {
    @Getter
    @Setter
    @AllArgsConstructor
    public static class PostApiResponse extends PostDTO {
        Set<PostTagApiResponse> postTagSet;
        Set<PostImageResponse> postImageSet;
        UserApiResponse user;
        boolean isLike;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PostCreateRequest {
        private String title;
        private String content;
        private List<Long> tagIdList;

        public void validCheck() {
            Optional.ofNullable(this.getTitle()).orElseThrow(() -> new ApplicationErrorException(ApplicationErrorType.INVALID_REQUEST));
            Optional.ofNullable(this.getContent()).orElseThrow(() -> new ApplicationErrorException(ApplicationErrorType.INVALID_REQUEST));
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PostUpdateRequest {
        private String title;
        private String content;
        private List<Long> tagIdList;
        private List<Long> deleteImageIdList;
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

    @Getter
    @Setter
    @AllArgsConstructor(staticName = "of")
    public static class PostFilterForm {
        List<TagDTO> brandTagList;
        Map<TagType, List<TagDTO>> personalTagList;
        Map<TagType, List<TagDTO>> conceptTagList;
        List<TagDTO> frameTagList;
    }
    @Getter
    @Setter
    @AllArgsConstructor(staticName = "of")
    public static class PostForm {
        Map<TagType, List<TagDTO>> postFormTagList;
    }
}


