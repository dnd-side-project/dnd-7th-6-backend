package com.hot6.phopa.api.domain.review.model.dto;

import com.hot6.phopa.core.domain.review.model.dto.TagDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class ReviewTagApiDTO {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ReviewTagApiResponse extends ReviewTagApiDTO{
        private TagDTO tag;
    }
}
