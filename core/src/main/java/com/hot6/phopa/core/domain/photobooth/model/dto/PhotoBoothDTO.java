package com.hot6.phopa.core.domain.photobooth.model.dto;

import com.hot6.phopa.core.common.model.type.Status;
import com.hot6.phopa.core.domain.photobooth.model.entity.PhotoBoothEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PhotoBoothDTO {
    private Long id;

    private String name;

    private String jibunAddress;

    private String roadAddress;

    private Double latitude;

    private Double longitude;

    private Integer PhotoBooth;

    private Status status;
}
