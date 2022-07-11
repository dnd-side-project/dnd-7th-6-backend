package com.hot6.phopa.api.domain.photobooth.service;

import com.hot6.phopa.core.domain.photobooth.model.dto.PhotoBoothDTO;
import com.hot6.phopa.core.domain.photobooth.model.mapper.PhotoBoothMapper;
import com.hot6.phopa.core.domain.photobooth.service.PhotoBoothService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PhotoBoothApiService {

    private final PhotoBoothService photoBoothService;

    private final PhotoBoothMapper photoBoothMapper;
    public List<PhotoBoothDTO> getPhotoBoothNearByUserGeo(Double latitude, Double longitude, Double distance) {
        return photoBoothMapper.toDtoList(photoBoothService.getPhotoBoothNearByUserGeo(latitude, longitude, distance));
    }
}
