package com.hot6.phopa.core.domain.photobooth.model.dto;

import com.hot6.phopa.core.domain.photobooth.model.entity.PhotoBoothEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.Map;

@Getter
@AllArgsConstructor(staticName = "of")
public class PhotoBoothWithDistanceDTO {
    Map<Long, Double> photoBoothIdDistanceMap;
    Page<PhotoBoothEntity> photoBoothEntityPage;
}
