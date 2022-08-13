package com.hot6.phopa.api.domain.tag.model.dto;

import com.hot6.phopa.core.domain.tag.enumeration.TagType;
import com.hot6.phopa.core.domain.tag.model.dto.TagDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class TagApiDTO {
    @Getter
    @Setter
    @NoArgsConstructor
    public static class TagApiResponse extends TagDTO {
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor(staticName = "of")
    public static class TagCreateRequest {
        @NotNull
        private String title;
        private String keyword;
        @NotNull
        private TagType tagType;
    }
}
