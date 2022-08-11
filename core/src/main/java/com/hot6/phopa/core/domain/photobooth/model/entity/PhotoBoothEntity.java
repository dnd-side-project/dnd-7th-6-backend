package com.hot6.phopa.core.domain.photobooth.model.entity;

import com.hot6.phopa.core.common.model.entity.BaseTimeEntity;
import com.hot6.phopa.core.common.model.type.Status;
import com.hot6.phopa.core.domain.review.model.entity.ReviewEntity;
import com.hot6.phopa.core.domain.tag.model.entity.TagEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

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

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "like_count")
    private Integer likeCount;

    @Column(name = "review_count")
    private Integer reviewCount;

    @Column(name = "star_score")
    private Float starScore;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private TagEntity tag;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "photoBooth", orphanRemoval = true)
    private Set<ReviewEntity> reviewSet;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "photoBooth", orphanRemoval = true)
    private Set<PhotoBoothLikeEntity> photoBoothLikeSet;

    public void updateLikeCount(int count) {
        this.likeCount += count;
    }

    public void updateReviewCount(int count) {
        this.reviewCount += count;
    }

    public void updateStarScore(Float starScore) {
        this.starScore = starScore;
    }
}
