package com.hot6.phopa.api.domain.user.service;

import com.hot6.phopa.api.domain.community.model.mapper.PostApiMapper;
import com.hot6.phopa.api.domain.review.model.mapper.ReviewApiMapper;
import com.hot6.phopa.api.domain.user.model.dto.UserApiDTO.UserLikeResponse;
import com.hot6.phopa.api.domain.user.model.dto.UserApiDTO.UserListResponse;
import com.hot6.phopa.core.domain.community.model.entity.PostEntity;
import com.hot6.phopa.core.domain.community.model.mapper.PostMapper;
import com.hot6.phopa.core.domain.community.service.PostService;
import com.hot6.phopa.core.domain.photobooth.model.entity.PhotoBoothEntity;
import com.hot6.phopa.core.domain.photobooth.model.entity.PhotoBoothEntity;
import com.hot6.phopa.core.domain.photobooth.model.mapper.PhotoBoothMapper;
import com.hot6.phopa.core.domain.photobooth.service.PhotoBoothService;
import com.hot6.phopa.core.domain.review.model.entity.ReviewEntity;
import com.hot6.phopa.core.domain.review.service.ReviewService;
import com.hot6.phopa.core.domain.user.model.dto.UserDTO;
import com.hot6.phopa.core.domain.user.model.entity.UserEntity;
import com.hot6.phopa.core.domain.user.service.UserService;
import com.hot6.phopa.core.security.config.PrincipleDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserApiService {
    private final PhotoBoothService photoBoothService;
    private final PostService postService;
    private final PhotoBoothMapper photoBoothMapper;
    private final ReviewService reviewService;
    private final PostApiMapper postApiMapper;
    private final ReviewApiMapper reviewApiMapper;
    public UserLikeResponse getLikeResponse() {
        UserDTO userDto = PrincipleDetail.get();
        List<PhotoBoothEntity> photoBoothEntityList = photoBoothService.findAllByUserLike(userDto.getId());
        List<PostEntity> postEntityList = postService.findAllByUserLike(userDto.getId());
        return UserLikeResponse.of(photoBoothMapper.toDtoList(photoBoothEntityList), postApiMapper.toDtoList(postEntityList));
    }

    public UserListResponse getUserListResponse() {
        UserDTO userDto = PrincipleDetail.get();
        List<ReviewEntity> reviewEntityList = reviewService.findAllByUserId(userDto.getId());
        List<PostEntity> postEntityList = postService.findAllByUserId(userDto.getId());
        return UserListResponse.of(reviewApiMapper.toDtoList(reviewEntityList), postApiMapper.toDtoList(postEntityList));
    }
}
