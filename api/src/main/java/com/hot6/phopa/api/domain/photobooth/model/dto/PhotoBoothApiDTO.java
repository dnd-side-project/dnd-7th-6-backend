package com.hot6.phopa.api.domain.photobooth.model.dto;

import com.hot6.phopa.core.domain.photobooth.model.dto.PhotoBoothDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class PhotoBoothApiDTO {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class PhotoBoothApiResponse extends PhotoBoothDTO {
    }
}
