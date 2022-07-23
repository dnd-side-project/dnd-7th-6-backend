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
}
