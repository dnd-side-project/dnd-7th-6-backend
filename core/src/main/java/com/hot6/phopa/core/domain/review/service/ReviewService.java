package com.hot6.phopa.core.domain.review.service;

import com.hot6.phopa.core.domain.review.model.entity.ReviewEntity;
import com.hot6.phopa.core.domain.review.repository.ReviewRepository;
import com.hot6.phopa.core.domain.review.repository.TagRepository;
import com.hot6.phopa.core.domain.tag.model.entity.TagEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    private final TagRepository tagRepository;
    public List<ReviewEntity> getReview(long photoBoothId) {
        return reviewRepository.findByPhotoBoothId(photoBoothId);
    }
    public List<TagEntity> getTagList(List<Long> tagIdList) {return tagRepository.findAllById(tagIdList);}
    public ReviewEntity createReview(ReviewEntity reviewEntity) {return reviewRepository.save(reviewEntity);}
}
