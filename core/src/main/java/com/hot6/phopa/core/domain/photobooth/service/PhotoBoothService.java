package com.hot6.phopa.core.domain.photobooth.service;

import com.hot6.phopa.core.common.enumeration.Direction;
import com.hot6.phopa.core.common.exception.ApplicationErrorType;
import com.hot6.phopa.core.common.exception.SilentApplicationErrorException;
import com.hot6.phopa.core.common.utils.GeometryUtil;
import com.hot6.phopa.core.common.utils.Location;
import com.hot6.phopa.core.domain.map.service.KakaoMapService;
import com.hot6.phopa.core.domain.photobooth.model.entity.PhotoBoothEntity;
import com.hot6.phopa.core.domain.photobooth.repository.PhotoBoothRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PhotoBoothService {

    private final EntityManager em;

    private final PhotoBoothRepository photoBoothRepository;

    private final KakaoMapService kakaoMapService;

    @Transactional(readOnly = true)
    //    distance 1 = 1km
    public List<PhotoBoothEntity> getPhotoBoothNearByUserGeo(Double latitude, Double longitude, Double distance, Set<Long> tagIdSet) {
        Location northEast = GeometryUtil
                .calculate(latitude, longitude, distance, Direction.NORTHEAST.getBearing());
        Location southWest = GeometryUtil
                .calculate(latitude, longitude, distance, Direction.SOUTHWEST.getBearing());

        double x1 = northEast.getLatitude();
        double y1 = northEast.getLongitude();
        double x2 = southWest.getLatitude();
        double y2 = southWest.getLongitude();

        String tagJoinStr = "";
        String tagWhereStr = "";

        if(CollectionUtils.isNotEmpty(tagIdSet)){
            tagJoinStr = "join review r on r.photo_booth_id = p.id " +
                    "join review_tag rt on rt.review_id = r.id " +
                    "join tag t on rt.tag_id = t.id ";
            tagWhereStr = "and t.id in (" + tagIdSet.stream().map(String::valueOf).collect(Collectors.joining(", ")) + ") ";
        }

        String pointFormat = String.format("'LINESTRING(%f %f, %f %f)')", x1, y1, x2, y2);
        Query query = em.createNativeQuery("SELECT distinct p.id as id, p.name as name, p.jibun_address as jibun_address, p.road_address as road_address, p.point as point, p.created_at as created_at, p.updated_at as updated_at "
                        + "FROM photo_booth AS p " + tagJoinStr
                        + "WHERE MBRContains(ST_LINESTRINGFROMTEXT(" + pointFormat + ", p.point) " + tagWhereStr, PhotoBoothEntity.class);

        List<PhotoBoothEntity> photoBoothEntityList = query.getResultList();
        return photoBoothEntityList;
    }

    public PhotoBoothEntity getPhotoBooth(Long photoBoothId) {
        return photoBoothRepository.findById(photoBoothId).orElseThrow(() -> new SilentApplicationErrorException(ApplicationErrorType.COULDNT_FIND_ANY_DATA));
    }

    public List<PhotoBoothEntity> kakaoMapTest(String keyword, Double latitude, Double longitude, Double distance) {
        List<PhotoBoothEntity> photoBoothEntityList = kakaoMapService.crawlingPhotoBoothData(keyword, latitude, longitude, distance);
        Set<Point> crawlingPointSet = photoBoothEntityList.stream().map(PhotoBoothEntity::getPoint).collect(Collectors.toSet());
        Set<Point> alreadyPointSet = photoBoothRepository.findByPointSet(crawlingPointSet).stream().map(PhotoBoothEntity::getPoint).collect(Collectors.toSet());
        return photoBoothRepository.saveAll(photoBoothEntityList.stream().filter(photoBoothEntity -> alreadyPointSet.contains(photoBoothEntity.getPoint()) == false).collect(Collectors.toList()));
    }
}
