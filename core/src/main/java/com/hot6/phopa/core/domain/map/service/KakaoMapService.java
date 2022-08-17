package com.hot6.phopa.core.domain.map.service;

import com.hot6.phopa.core.common.model.type.Status;
import com.hot6.phopa.core.common.utils.GeometryUtil;
import com.hot6.phopa.core.domain.map.client.KakaoFeignClient;
import com.hot6.phopa.core.domain.map.model.dto.KakaoMapResponse.KeyWordSearchResponse;
import com.hot6.phopa.core.domain.photobooth.model.entity.PhotoBoothEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KakaoMapService {
    private final KakaoFeignClient kakaoFeignClient;

    public List<PhotoBoothEntity> crawlingPhotoBoothData(String keyword, Double latitude, Double longitude, Double distance) {
        List<PhotoBoothEntity> photoBoothEntityList = new ArrayList<>();
        int page = 1;
        while(true){
            KeyWordSearchResponse kakaoSearchResponse;
            //meter로 변환위해 distance * 1000
            if(latitude != null && longitude != null && distance != null){
                kakaoSearchResponse = kakaoFeignClient.keywordSearch(keyword, String.valueOf(longitude), String.valueOf(latitude), (int)(distance * 1000), page++);

            } else{
                kakaoSearchResponse = kakaoFeignClient.keywordSearch(keyword, page++);
            }
            photoBoothEntityList.addAll(convertToPhotoBoothEntity(kakaoSearchResponse));
            if(kakaoSearchResponse.getMeta().getIs_end()){
                break;
            }
        }
        return photoBoothEntityList;
    }

    private List<PhotoBoothEntity> convertToPhotoBoothEntity(KeyWordSearchResponse keyWordSearchResponse){
        List<PhotoBoothEntity> photoBoothEntityList = new ArrayList<>();
        for(KeyWordSearchResponse.Document document : keyWordSearchResponse.getDocuments()){
            photoBoothEntityList.add(
                    PhotoBoothEntity.builder()
                            .name(document.getPlace_name())
                            .jibunAddress(document.getAddress_name())
                            .roadAddress(document.getRoad_address_name())
                            .latitude(Double.valueOf(document.getY()))
                            .longitude(Double.valueOf(document.getX()))
                            .likeCount(0)
                            .reviewCount(0)
                            .reviewImageCount(0)
                            .starScore(0.0F)
                            .totalStarScore(0.0F)
                            .status(Status.ACTIVE)
                            .build()
            );
        }
        return photoBoothEntityList;
    }
}
