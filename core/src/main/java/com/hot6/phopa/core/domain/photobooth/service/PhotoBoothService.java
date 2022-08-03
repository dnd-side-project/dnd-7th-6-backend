package com.hot6.phopa.core.domain.photobooth.service;

import com.hot6.phopa.core.common.enumeration.Direction;
import com.hot6.phopa.core.common.exception.ApplicationErrorType;
import com.hot6.phopa.core.common.exception.SilentApplicationErrorException;
import com.hot6.phopa.core.common.model.type.Status;
import com.hot6.phopa.core.common.utils.GeometryUtil;
import com.hot6.phopa.core.common.utils.Location;
import com.hot6.phopa.core.domain.map.service.KakaoMapService;
import com.hot6.phopa.core.domain.photobooth.model.entity.PhotoBoothEntity;
import com.hot6.phopa.core.domain.photobooth.model.entity.PhotoBoothLikeEntity;
import com.hot6.phopa.core.domain.photobooth.repository.PhotoBoothLikeRepository;
import com.hot6.phopa.core.domain.photobooth.repository.PhotoBoothRepository;
import com.hot6.phopa.core.domain.tag.model.entity.TagEntity;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.locationtech.jts.geom.Point;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
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

    private final PhotoBoothLikeRepository photoBoothLikeRepository;

    @Transactional(readOnly = true)
    //    distance 1 = 1km
    //TODO 리팩토링 필요
    public List<PhotoBoothEntity> getPhotoBoothNearByUserGeo(Double latitude, Double longitude, Double distance, Status status, Set<Long> tagIdSet, int pageSize, int pageNumber) {
        Location northEast = GeometryUtil
                .calculate(latitude, longitude, distance, Direction.NORTHEAST.getBearing());
        Location southWest = GeometryUtil
                .calculate(latitude, longitude, distance, Direction.SOUTHWEST.getBearing());

        double x1 = northEast.getLatitude();
        double y1 = northEast.getLongitude();
        double x2 = southWest.getLatitude();
        double y2 = southWest.getLongitude();
        String pointFormat = String.format("'LINESTRING(%f %f, %f %f)')", x1, y1, x2, y2);
        String orderByFormat = " ORDER BY " + String.format("ST_DISTANCE(p.point, POINT(%f, %f)) ", longitude, latitude);
        String tagIdStringFormat = "(" + tagIdSet.stream().map(String::valueOf).collect(Collectors.joining(", ")) + ") ";
        String tagWhereStr = CollectionUtils.isNotEmpty(tagIdSet) ? "AND (t.id IN " + tagIdStringFormat + " OR p.tag_id IN" + tagIdStringFormat + ")": "";
        Query query = em.createNativeQuery("SELECT DISTINCT p.* "
                + "FROM photo_booth AS p "
                + "LEFT JOIN review r ON r.photo_booth_id = p.id "
                + "LEFT JOIN review_tag rt ON rt.review_id = r.id "
                + "LEFT JOIN tag t ON rt.tag_id = t.id "
                + "WHERE MBRContains(ST_LINESTRINGFROMTEXT(" + pointFormat + ", p.point) AND p.status ='" + status + "' " + tagWhereStr + orderByFormat + "LIMIT  " + pageSize +" OFFSET  " + (pageNumber - 1), PhotoBoothEntity.class);

        return query.getResultList();
    }

    @Transactional(readOnly = true)
    public PhotoBoothEntity getPhotoBooth(Long photoBoothId) {
        return photoBoothRepository.findById(photoBoothId).orElseThrow(() -> new SilentApplicationErrorException(ApplicationErrorType.COULDNT_FIND_ANY_DATA));
    }

    public List<PhotoBoothEntity> kakaoMapTest(String keyword, Double latitude, Double longitude, Double distance, TagEntity tagEntity) {
        List<PhotoBoothEntity> photoBoothEntityList = kakaoMapService.crawlingPhotoBoothData(keyword, latitude, longitude, distance);
        Set<Point> crawlingPointSet = photoBoothEntityList.stream().map(PhotoBoothEntity::getPoint).collect(Collectors.toSet());
        Set<Point> alreadyPointSet = photoBoothRepository.findByPointSet(crawlingPointSet).stream().map(PhotoBoothEntity::getPoint).collect(Collectors.toSet());
        List<PhotoBoothEntity> savePhotoBoothEntityList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(photoBoothEntityList)) {
            for (PhotoBoothEntity photoBoothEntity : photoBoothEntityList) {
                if (alreadyPointSet.contains(photoBoothEntity.getPoint()) == false) {
                    photoBoothEntity.setTag(tagEntity);
                    savePhotoBoothEntityList.add(photoBoothEntity);
                }
            }
            tagEntity.updatePhotoBoothCount(photoBoothEntityList.size());
        }
        return photoBoothRepository.saveAll(savePhotoBoothEntityList);
    }

    @Transactional(readOnly = true)
    public PhotoBoothEntity getPhotoBoothById(Long photoBoothId) {
        return photoBoothRepository.findById(photoBoothId).orElseThrow(() -> new SilentApplicationErrorException(ApplicationErrorType.COULDNT_FIND_ANY_DATA));
    }

    public PhotoBoothLikeEntity createReviewLikeEntity(PhotoBoothLikeEntity photoBoothLikeEntity) {
        return photoBoothLikeRepository.save(photoBoothLikeEntity);
    }

    public void deletePhotoBoothLikeEntity(PhotoBoothLikeEntity photoBoothLikeEntity) {
        photoBoothLikeRepository.delete(photoBoothLikeEntity);
    }

    @Transactional(readOnly = true)
    public PhotoBoothLikeEntity getPhotoBoothLikeByPhotoBoothIdAndUserId(Long photoBoothId, Long userId) {
        return photoBoothLikeRepository.findOneByPhotoBoothIdAndUserId(photoBoothId, userId);
    }

    public List<PhotoBoothEntity> findAllByUserLike(Long userId) {
        return photoBoothRepository.findAllByUserLike(userId);
    }
}
