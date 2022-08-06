package com.hot6.phopa.api.domain.tag.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class TagApiDTO {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor(staticName = "of")
    public static class TagKeywordResponse {
        private Long id;
        private String title;
    }
}
