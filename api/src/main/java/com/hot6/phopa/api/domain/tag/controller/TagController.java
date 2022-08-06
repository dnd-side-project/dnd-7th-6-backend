package com.hot6.phopa.api.domain.tag.controller;

import com.hot6.phopa.api.domain.tag.model.dto.TagApiDTO;
import com.hot6.phopa.api.domain.tag.model.dto.TagApiDTO.TagKeywordResponse;
import com.hot6.phopa.api.domain.tag.service.TagApiService;
import com.hot6.phopa.core.common.model.dto.PageableParam;
import com.hot6.phopa.core.common.model.dto.PageableResponse;
import com.hot6.phopa.core.domain.tag.enumeration.TagType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/tag", produces = "application/json")
@RequiredArgsConstructor
public class TagController {
    private final TagApiService tagService;

    @GetMapping(value = "/keyword-search")
    public PageableResponse<TagKeywordResponse> getTagByKeyword(
            @RequestParam String keyword,
            @RequestParam(required = false) TagType tagType,
            PageableParam pageable
    ){
        return tagService.getTagByKeyword(keyword, tagType, pageable);
    }
}
