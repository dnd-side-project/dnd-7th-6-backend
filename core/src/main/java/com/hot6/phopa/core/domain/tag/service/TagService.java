package com.hot6.phopa.core.domain.tag.service;

import com.hot6.phopa.core.domain.tag.repository.TagRepository;
import com.hot6.phopa.core.domain.tag.model.entity.TagEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    public List<TagEntity> getTagList(List<Long> tagIdList) {
        return tagRepository.findAllById(tagIdList);
    }

    public List<TagEntity> getTagListIsPhotoBooth() { return tagRepository.findAllByIsPhotoBoothIsTrue(); }

    public TagEntity getTagOrCreate(String keyword) {
        TagEntity tagEntity = tagRepository.findOneByTagAndIsPhotoBoothIsTrue(keyword);
        if(tagEntity == null){
            tagEntity = tagRepository.save(
                    TagEntity.builder()
                            .tag(keyword)
                            .postCount(0)
                            .reviewCount(0)
                            .isPhotoBooth(true)
                            .build()
            );
        }
        return tagEntity;
    }
}
