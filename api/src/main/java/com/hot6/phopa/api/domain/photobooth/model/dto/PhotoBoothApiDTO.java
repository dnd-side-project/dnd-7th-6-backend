package com.hot6.phopa.api.domain.photobooth.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hot6.phopa.api.domain.review.model.dto.ReviewApiDTO.ReviewApiResponse;
import com.hot6.phopa.core.domain.photobooth.model.dto.PhotoBoothDTO;
import com.hot6.phopa.core.domain.tag.enumeration.TagType;
import com.hot6.phopa.core.domain.tag.model.dto.TagDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

public class PhotoBoothApiDTO {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PhotoBoothApiResponse extends PhotoBoothDTO{
        String imageUrl;
    }
    @AllArgsConstructor(staticName = "of")
    @NoArgsConstructor
    @Getter
    @Setter
    public static class PhotoBoothWithTagResponse{
        PhotoBoothApiResponse photoBooth;
        List<TagDTO> tagSet;
        boolean isLike;
        Double distance;
    }
    @AllArgsConstructor(staticName = "of")
    @NoArgsConstructor
    @Getter
    @Setter
    public static class PhotoBoothDetailResponse{
        PhotoBoothApiResponse photoBooth;
        boolean isLike;
        Double distance;
        List<String> reviewImageList;
        Map<TagType, List<PhotoBoothTagResponse>> tagSummary;
    }
    @Getter
    @Setter
    @AllArgsConstructor(staticName = "of")
    public static class PhotoBoothFilterFormResponse {
        List<TagDTO> brandTagList;
        List<TagDTO> tagList;
    }

    @Getter
    @Setter
    @AllArgsConstructor(staticName = "of")
    @NoArgsConstructor
    public static class PhotoBoothTagResponse{
        TagDTO tag;
        Integer reviewCount;
    }
}
