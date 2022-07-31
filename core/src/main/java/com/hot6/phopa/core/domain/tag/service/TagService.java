package com.hot6.phopa.core.domain.tag.service;

import com.hot6.phopa.core.domain.tag.enumeration.TagType;
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

    public List<TagEntity> getTagListByTagType(TagType tagType) { return tagRepository.findAllByTagType(tagType); }

    public TagEntity getTagOrCreate(String keyword, TagType tagType) {
        TagEntity tagEntity = tagRepository.findOneByTitleAndTagType(keyword, tagType);
        if(tagEntity == null){
            tagEntity = tagRepository.save(
                    TagEntity.builder()
                            .title(keyword)
                            .postCount(0)
                            .reviewCount(0)
                            .photoBoothCount(0)
                            .tagType(TagType.PHOTO_BOOTH)
                            .build()
            );
        }
        return tagEntity;
    }
}
