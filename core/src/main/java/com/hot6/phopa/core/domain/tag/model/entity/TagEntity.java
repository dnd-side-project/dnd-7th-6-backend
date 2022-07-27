package com.hot6.phopa.core.domain.tag.model.entity;

import com.hot6.phopa.core.common.model.entity.BaseTimeEntity;
import com.hot6.phopa.core.domain.community.model.entity.PostTagEntity;
import com.hot6.phopa.core.domain.photobooth.model.entity.PhotoBoothEntity;
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
@Table(name = "tag")
public class TagEntity extends BaseTimeEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "tag")
    private String tag;

    @Column(name = "review_count")
    private Integer reviewCount;

    @Column(name = "post_count")
    private Integer postCount;

    @Column(name = "photo_booth_count")
    private Integer photoBoothCount;

    @Column(name = "is_photo_booth")
    private Boolean isPhotoBooth;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "tag", orphanRemoval = true)
    private Set<PhotoBoothEntity> PhotoBoothSet;

    public void updatePhotoBoothCount(int count) {
        this.photoBoothCount += count;
    }
}
