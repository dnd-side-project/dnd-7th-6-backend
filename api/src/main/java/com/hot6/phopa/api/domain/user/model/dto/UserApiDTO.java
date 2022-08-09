package com.hot6.phopa.api.domain.user.model.dto;

import com.hot6.phopa.api.domain.community.model.dto.PostApiDTO;
import com.hot6.phopa.api.domain.review.model.dto.ReviewApiDTO;
import com.hot6.phopa.api.domain.review.model.dto.ReviewApiDTO.ReviewApiResponse;
import com.hot6.phopa.core.domain.community.model.dto.PostDTO;
import com.hot6.phopa.core.domain.photobooth.model.dto.PhotoBoothDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class UserApiDTO {
    @Getter
    @Setter
    @AllArgsConstructor(staticName = "of")
    public static class UserLikeResponse {
        List<PhotoBoothDTO> photoBoothList;
        List<PostDTO> postList;
    }

    @Getter
    @Setter
    @AllArgsConstructor(staticName = "of")
    public static class UserListResponse {
        List<ReviewApiResponse> reviewList;
        List<PostApiDTO.PostApiResponse> postList;
    }
}
