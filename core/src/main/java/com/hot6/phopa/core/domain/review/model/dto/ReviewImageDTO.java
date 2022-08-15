package com.hot6.phopa.core.domain.review.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewImageDTO {
    private Long id;

    private String imageUrl;

    private Integer likeCount;

    private Integer imageOrder;
}
