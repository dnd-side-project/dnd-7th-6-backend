package com.hot6.phopa.core.domain.tag.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagDTO {
    protected Long id;
    protected String tag;
    protected Integer reviewCount;
    protected Integer postCount;
}
