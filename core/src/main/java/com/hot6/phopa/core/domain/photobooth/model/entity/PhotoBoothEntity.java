package com.hot6.phopa.core.domain.photobooth.model.entity;

import com.google.common.collect.Lists;
import com.hot6.phopa.core.common.model.entity.BaseTimeEntity;
import com.hot6.phopa.core.domain.review.model.entity.ReviewEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.locationtech.jts.geom.Point;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@Table(name = "photo_booth")
public class PhotoBoothEntity extends BaseTimeEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "jibun_address")
    private String jibunAddress;

    @Column(name = "road_address")
    private String roadAddress;

    @Column(name = "point")
    private Point point;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "photoBooth", orphanRemoval = true)
    private List<ReviewEntity> reviewEntityList;
}
