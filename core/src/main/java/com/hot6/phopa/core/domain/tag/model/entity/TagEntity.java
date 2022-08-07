package com.hot6.phopa.core.domain.tag.model.entity;

import com.hot6.phopa.core.common.model.entity.BaseTimeEntity;
import com.hot6.phopa.core.domain.community.model.entity.PostTagEntity;
import com.hot6.phopa.core.domain.photobooth.model.entity.PhotoBoothEntity;
import com.hot6.phopa.core.domain.review.model.entity.ReviewTagEntity;
import com.hot6.phopa.core.domain.tag.enumeration.TagType;
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

    @Column(name = "title")
    private String title;

    @Column(name = "keyword")
    private String keyword;

    @Column(name = "review_count")
    private int reviewCount;

    @Column(name = "post_count")
    private int postCount;

    @Column(name = "photo_booth_count")
    private int photoBoothCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "tag_type")
    private TagType tagType;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "tag", orphanRemoval = true)
    private Set<PhotoBoothEntity> PhotoBoothSet;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "tag", orphanRemoval = true)
    private Set<PostTagEntity> postTagSet;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "tag", orphanRemoval = true)
    private Set<ReviewTagEntity> reviewTagSet;

    public void updatePhotoBoothCount(int count) {
        this.photoBoothCount += count;
    }

    public void updateReviewCount(int count) { this.reviewCount += count;}

    public void updatePostCount(int count) { this.postCount += count;}

}
