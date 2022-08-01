package com.hot6.phopa.api.domain.user.service;

import com.hot6.phopa.api.domain.user.model.dto.UserApiDTO.UserLikeResponse;
import com.hot6.phopa.api.domain.user.model.mapper.UserApiMapper;
import com.hot6.phopa.core.domain.community.model.dto.PostDTO;
import com.hot6.phopa.core.domain.community.model.entity.PostEntity;
import com.hot6.phopa.core.domain.community.model.mapper.PostMapper;
import com.hot6.phopa.core.domain.community.service.PostService;
import com.hot6.phopa.core.domain.photobooth.model.dto.PhotoBoothDTO;
import com.hot6.phopa.core.domain.photobooth.model.entity.PhotoBoothEntity;
import com.hot6.phopa.core.domain.photobooth.model.mapper.PhotoBoothMapper;
import com.hot6.phopa.core.domain.photobooth.service.PhotoBoothService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserApiService {
    private final PhotoBoothService photoBoothService;
    private final PostService postService;
    private final PhotoBoothMapper photoBoothMapper;
    private final PostMapper postMapper;
    public UserLikeResponse getLikeResponse(Long userId) {
        List<PhotoBoothDTO> photoBoothDTOList = photoBoothMapper.toDtoList(photoBoothService.findAllByUserLike(userId));
        List<PostDTO> postDTOList = postMapper.toDtoList(postService.findAllByUserLike(userId));
        return UserLikeResponse.of(photoBoothDTOList, postDTOList);
    }
}
