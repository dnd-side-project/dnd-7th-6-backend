package com.hot6.phopa.core.domain.photobooth.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Setter
@Getter
public class PhotoBoothDTO {
    private Long id;
    private String name;
    private Double latitude;
    private Double longitude;
}
