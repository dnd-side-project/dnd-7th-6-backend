package com.hot6.phopa.core.domain.review.model.dto;

import com.hot6.phopa.core.common.model.type.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReviewDTO {
    protected Long id;
    protected String title;
    protected String content;
    protected Float starScore;
    protected Status status;
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;
}
