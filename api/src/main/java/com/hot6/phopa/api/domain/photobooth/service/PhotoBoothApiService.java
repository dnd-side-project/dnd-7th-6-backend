package com.hot6.phopa.api.domain.photobooth.service;

import com.hot6.phopa.api.domain.photobooth.model.dto.PhotoBoothApiDTO;
import com.hot6.phopa.api.domain.photobooth.model.dto.PhotoBoothApiDTO.PhotoBoothApiResponse;
import com.hot6.phopa.api.domain.photobooth.model.mapper.PhotoBoothApiMapper;
import com.hot6.phopa.core.domain.photobooth.service.PhotoBoothService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PhotoBoothApiService {

    private final PhotoBoothService photoBoothService;

    private final PhotoBoothApiMapper photoBoothMapper;
    public List<PhotoBoothApiResponse> getPhotoBoothNearByUserGeo(Double latitude, Double longitude, Double distance, Set<Long> tagIdSet) {
        return photoBoothMapper.toDtoList(photoBoothService.getPhotoBoothNearByUserGeo(latitude, longitude, distance, tagIdSet));
    }
}
