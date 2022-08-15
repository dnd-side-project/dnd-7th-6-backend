package com.hot6.phopa.core.domain.tag.service;

import com.hot6.phopa.core.common.model.dto.PageableParam;
import com.hot6.phopa.core.domain.tag.enumeration.TagType;
import com.hot6.phopa.core.domain.tag.repository.TagRepository;
import com.hot6.phopa.core.domain.tag.model.entity.TagEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    public List<TagEntity> getTagList(List<Long> tagIdList) {
        return tagRepository.findAllById(tagIdList);
    }

    public List<TagEntity> getTagListByTagType(TagType tagType) { return tagRepository.findAllByTagType(tagType); }

    public List<TagEntity> getTagListByTagTypeList(List<TagType> tagTypeList, Boolean onlyKeyword) { return tagRepository.findAllByTagTypeList(tagTypeList, onlyKeyword); }


    public TagEntity getTagOrCreate(String title, String keyword, TagType tagType) {
        TagEntity tagEntity = tagRepository.findOneByTitleAndTagType(keyword, tagType);
        if(tagEntity == null){
            tagEntity = tagRepository.save(
                    TagEntity.builder()
                            .title(title)
                            .keyword(keyword != null ? keyword : title)
                            .postCount(0)
                            .reviewCount(0)
                            .photoBoothCount(0)
                            .tagType(tagType)
                            .build()
            );
        }
        return tagEntity;
    }

    public List<TagEntity> getTagByPhotoBoothId(Long photoBoothId) {
        return tagRepository.findByPhotoBoothId(photoBoothId);
    }
    @Transactional(readOnly = true)
    public Page<TagEntity> getTagByKeyword(String keyword, TagType tagType, PageableParam pageable) {
        return tagRepository.getTagByKeyword(keyword, tagType, pageable);
    }

    public TagEntity create(TagEntity tagEntity) {
        return tagRepository.save(tagEntity);
    }

    public List<TagEntity> createAll(List<TagEntity> tagEntityList) {
        return tagRepository.saveAll(tagEntityList);
    }

    public List<TagEntity> createCustomTagList(List<String> newTagList) {
        List<TagEntity> tagEntityList = newTagList.stream().map( keyword ->
                TagEntity.builder()
                .title(keyword)
                .keyword(keyword)
                .tagType(TagType.CUSTOM)
                .build()
        ).collect(Collectors.toList());
        return createAll(tagEntityList);
    }
}
