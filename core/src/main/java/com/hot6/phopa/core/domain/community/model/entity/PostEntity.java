package com.hot6.phopa.core.domain.community.model.entity;

import com.hot6.phopa.core.common.model.entity.BaseTimeEntity;
import com.hot6.phopa.core.common.model.type.Status;
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
@Table(name = "post")
public class PostEntity extends BaseTimeEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "like_count")
    private Integer likeCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "post", orphanRemoval = true)
    private Set<PostTagEntity> postTagSet;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "post", orphanRemoval = true)
    private Set<PostImageEntity> postImageSet;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "post", orphanRemoval = true)
    private Set<PostLikeEntity> postLikeSet;

    public void updateLikeCount(int count) {
        this.likeCount += count;
    }

    public void updateStatus(Status status) {
        this.status = status;
    }

    public void deleteImage(List<Long> imageIdList){
        this.getPostImageSet().removeAll(this.getPostImageSet().stream().filter(image -> imageIdList.contains(image.getId())).collect(Collectors.toSet()));
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
