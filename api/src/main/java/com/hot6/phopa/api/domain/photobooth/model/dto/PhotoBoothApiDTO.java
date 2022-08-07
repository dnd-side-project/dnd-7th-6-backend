package com.hot6.phopa.api.domain.photobooth.model.dto;

import com.hot6.phopa.api.domain.review.model.dto.ReviewApiDTO;
import com.hot6.phopa.api.domain.review.model.dto.ReviewApiDTO.ReviewApiResponse;
import com.hot6.phopa.core.domain.photobooth.model.dto.PhotoBoothDTO;
import com.hot6.phopa.core.domain.review.model.dto.ReviewDTO;
import com.hot6.phopa.core.domain.tag.enumeration.TagType;
import com.hot6.phopa.core.domain.tag.model.dto.TagDTO;
import com.hot6.phopa.core.domain.user.model.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mapstruct.Named;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class PhotoBoothApiDTO {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class PhotoBoothApiResponse extends PhotoBoothDTO{
    }
    @AllArgsConstructor(staticName = "of")
    @Getter
    @Setter
    public static class PhotoBoothWithTagResponse{
        PhotoBoothApiResponse photoBooth;
        ReviewApiResponse firstReview;
        List<TagDTO> tagSet;
        boolean isLike;
    }
    @Getter
    @Setter
    @AllArgsConstructor(staticName = "of")
    public static class PhotoBoothFormResponse {
        List<TagDTO> brandTagList;
        List<TagDTO> tagList;
    }
}
