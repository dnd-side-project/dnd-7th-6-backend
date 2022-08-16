package com.hot6.phopa.api.domain.user.model.dto;

import com.hot6.phopa.api.domain.community.model.dto.PostApiDTO;
import com.hot6.phopa.api.domain.review.model.dto.ReviewApiDTO.ReviewApiResponse;
import com.hot6.phopa.api.domain.user.model.enumeration.UserLikeType;
import com.hot6.phopa.core.domain.photobooth.model.dto.PhotoBoothDTO;
import com.hot6.phopa.core.domain.photobooth.model.entity.PhotoBoothEntity;
import com.hot6.phopa.core.domain.user.type.UserProvider;
import com.hot6.phopa.core.domain.user.type.UserRole;
import com.hot6.phopa.core.domain.user.type.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public class UserApiDTO {
    @Getter
    @Setter
    @AllArgsConstructor(staticName = "of")
    public static class UserLikeResponse {
        List<PhotoBoothDTO> photoBoothList;
        List<PostApiDTO.PostApiResponse> postList;
    }

    @Getter
    @Setter
    @AllArgsConstructor(staticName = "of")
    public static class UserLikeImageResponse {
        Long id;
        UserLikeType type;
        String imageUrl;
        LocalDateTime createdAt;
        boolean isLike;
    }

    @Getter
    @Setter
    @AllArgsConstructor(staticName = "of")
    @NoArgsConstructor
    public static class UserLikePhotoBoothResponse extends PhotoBoothDTO{
        String imageUrl;
        LocalDateTime createdAt;
        boolean isLike;
    }

    @Getter
    @Setter
    @AllArgsConstructor(staticName = "of")
    public static class UserListResponse {
        List<ReviewApiResponse> reviewList;
        List<PostApiDTO.PostApiResponse> postList;
    }

    @Getter
    @Setter
    @AllArgsConstructor(staticName = "of")
    @NoArgsConstructor
    public static class UserNameUpdateRequest {
        @NotNull
        String name;
    }

    @Getter
    @Setter
    @AllArgsConstructor(staticName = "of")
    @NoArgsConstructor
    public static class UserApiResponse {
        private Long id;
        private String email;
        private String name;
        private UserStatus status;
        private UserRole userRole;
        private UserProvider provider;
        private String providerId;
    }

    @Getter
    @Setter
    @AllArgsConstructor(staticName = "of")
    @NoArgsConstructor
    public static class UserLoginRequest {
        private String email;
        private UserProvider provider;
        private String providerId;
    }
}
