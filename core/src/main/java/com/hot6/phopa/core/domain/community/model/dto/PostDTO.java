package com.hot6.phopa.core.domain.community.model.dto;

import com.hot6.phopa.core.common.model.type.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDTO {
    protected Long id;
    protected String title;
    protected String content;
    protected Integer likeCount;
    protected Status status;
}
