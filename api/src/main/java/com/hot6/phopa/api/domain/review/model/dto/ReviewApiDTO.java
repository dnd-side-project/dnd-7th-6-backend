package com.hot6.phopa.api.domain.review.model.dto;

import com.hot6.phopa.api.domain.review.model.dto.ReviewTagApiDTO.ReviewTagApiResponse;
import com.hot6.phopa.core.domain.review.model.dto.ReviewDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class ReviewApiDTO {
    @Getter
    @Setter
    @AllArgsConstructor
    public static class ReviewApiResponse extends ReviewDTO {
        List<ReviewTagApiResponse> reviewTagList;
    }
}
