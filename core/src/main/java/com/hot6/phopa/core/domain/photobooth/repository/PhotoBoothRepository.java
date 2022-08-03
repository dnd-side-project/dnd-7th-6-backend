package com.hot6.phopa.core.domain.photobooth.repository;

import com.hot6.phopa.core.common.model.type.Status;
import com.hot6.phopa.core.domain.photobooth.dto.PhotoBoothNativeQueryDTO;
import com.hot6.phopa.core.domain.photobooth.model.entity.PhotoBoothEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface PhotoBoothRepository extends JpaRepository<PhotoBoothEntity, Long>, PhotoBoothCustomRepository {

    @Query(
            value = "SELECT DISTINCT p.id AS id, ST_DISTANCE(POINT(:lat, :lon), p.point) AS distance " +
                    "FROM photo_booth as p "
                    + "LEFT JOIN review r ON r.photo_booth_id = p.id "
                    + "LEFT JOIN review_tag rt ON rt.review_id = r.id "
                    + "LEFT JOIN tag t ON rt.tag_id = t.id "
                    + "WHERE MBRContains(ST_LINESTRINGFROMTEXT(:lineString), p.point)",
            nativeQuery = true
    )
    List<PhotoBoothNativeQueryDTO> findIdsByGeo(@Param(value = "lineString") String lineString, @Param(value = "lat") double lat, @Param(value = "lon") double lon);

}
