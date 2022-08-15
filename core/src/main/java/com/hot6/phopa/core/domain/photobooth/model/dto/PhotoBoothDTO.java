package com.hot6.phopa.core.domain.photobooth.model.dto;

import com.hot6.phopa.core.common.model.type.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Setter
@Getter
public class PhotoBoothDTO {
    protected Long id;

    protected String name;

    protected String jibunAddress;

    protected String roadAddress;

    protected Double latitude;

    protected Double longitude;

    protected Integer likeCount;

    protected Integer reviewCount;

    protected Float startScore;

    protected Status status;
}
