package com.hot6.phopa.core.domain.photobooth.repository;

import com.hot6.phopa.core.common.model.type.Status;
import com.hot6.phopa.core.domain.photobooth.model.entity.PhotoBoothEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoBoothRepository extends JpaRepository<PhotoBoothEntity, Long>, PhotoBoothCustomRepository {
    @Query(
            value = "SELECT DISTINCT p.* "
                    + "FROM photo_booth AS p "
                    + "LEFT JOIN review r ON r.photo_booth_id = p.id "
                    + "LEFT JOIN review_tag rt ON rt.review_id = r.id "
                    + "LEFT JOIN tag t ON rt.tag_id = t.id "
                    + "WHERE MBRContains(ST_LINESTRINGFROMTEXT('LINESTRING(:x1 :y1, :x2 :y2)'), p.point) and p.status =:status ORDER BY ST_DISTANCE(POINT(:lon, :lat), p.point)",
            countQuery = "SELECT COUNT(DISTINCT p.*) "
                    + "FROM photo_booth AS p "
                    + "LEFT JOIN review r ON r.photo_booth_id = p.id "
                    + "LEFT JOIN review_tag rt ON rt.review_id = r.id "
                    + "LEFT JOIN tag t ON rt.tag_id = t.id "
                    + "WHERE MBRContains(ST_LINESTRINGFROMTEXT('LINESTRING(:x1 :y1, :x2 :y2)'), p.point) and p.status =:status",
            nativeQuery = true
    )
    Page<PhotoBoothEntity> findByGeo(@Param(value = "x1") double x1, @Param(value = "y1") double y1, @Param(value = "x2") double x2, @Param(value = "y2") double y2, @Param(value = "status") Status status , @Param(value = "lon") double lon, @Param(value = "lat") double lat, Pageable pageable);
}
