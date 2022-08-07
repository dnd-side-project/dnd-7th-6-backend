package com.hot6.phopa.core.domain.tag.model.dto;

import com.hot6.phopa.core.domain.tag.enumeration.TagType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TagDTO {
    protected Long id;
    protected String title;
    protected String keyword;
    protected Integer photoBoothCount;
    protected Integer reviewCount;
    protected Integer postCount;
    protected TagType tagType;
}
