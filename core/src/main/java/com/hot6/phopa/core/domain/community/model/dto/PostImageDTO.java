package com.hot6.phopa.core.domain.community.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostImageDTO {
    private Long id;

    private String imageUrl;

    private Integer imageOrder;
}
