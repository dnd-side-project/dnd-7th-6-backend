package com.hot6.phopa.core.domain.review.model.entity;

import com.hot6.phopa.core.common.model.entity.BaseTimeEntity;
import com.hot6.phopa.core.common.model.type.Status;
import com.hot6.phopa.core.domain.photobooth.model.entity.PhotoBoothEntity;
import com.hot6.phopa.core.domain.user.model.entity.UserEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@Table(name = "review")
public class ReviewEntity extends BaseTimeEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "star_score")
    private Float starScore;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "photo_booth_id", nullable = false)
    private PhotoBoothEntity photoBooth;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "review", orphanRemoval = true)
    private Set<ReviewTagEntity> reviewTagSet;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "review", orphanRemoval = true)
    private Set<ReviewImageEntity> reviewImageSet;

    public void updateStatus(Status status) {
        this.status = status;
    }

    public void updateStarScore(Float starScore) {
        this.starScore = starScore;
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void deleteImage(List<Long> imageIdList) {
        this.getReviewImageSet().removeAll(this.getReviewImageSet().stream().filter(image -> imageIdList.contains(image.getId())).collect(Collectors.toSet()));
    }
}
