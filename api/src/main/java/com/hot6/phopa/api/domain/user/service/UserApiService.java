package com.hot6.phopa.api.domain.user.service;

import com.hot6.phopa.api.domain.community.model.mapper.PostApiMapper;
import com.hot6.phopa.api.domain.review.model.mapper.ReviewApiMapper;
import com.hot6.phopa.api.domain.user.model.dto.UserApiDTO;
import com.hot6.phopa.api.domain.user.model.dto.UserApiDTO.*;
import com.hot6.phopa.api.domain.user.model.mapper.UserApiMapper;
import com.hot6.phopa.core.common.exception.ApplicationErrorType;
import com.hot6.phopa.core.common.exception.SilentApplicationErrorException;
import com.hot6.phopa.core.common.model.type.Status;
import com.hot6.phopa.core.common.service.PrincipleDetailService;
import com.hot6.phopa.core.domain.community.model.entity.PostEntity;
import com.hot6.phopa.core.domain.community.service.PostService;
import com.hot6.phopa.core.domain.photobooth.model.entity.PhotoBoothEntity;
import com.hot6.phopa.core.domain.photobooth.model.mapper.PhotoBoothMapper;
import com.hot6.phopa.core.domain.photobooth.service.PhotoBoothService;
import com.hot6.phopa.core.domain.review.model.entity.ReviewEntity;
import com.hot6.phopa.core.domain.review.service.ReviewService;
import com.hot6.phopa.core.domain.user.model.dto.UserDTO;
import com.hot6.phopa.core.domain.user.model.entity.UserEntity;
import com.hot6.phopa.core.domain.user.service.UserService;
import com.hot6.phopa.core.domain.user.type.UserRole;
import com.hot6.phopa.core.domain.user.type.UserStatus;
import com.hot6.phopa.core.security.config.PrincipleDetail;
import com.hot6.phopa.core.security.jwt.JwtToken;
import com.hot6.phopa.core.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserApiService {
    private final PhotoBoothService photoBoothService;
    private final PostService postService;
    private final PhotoBoothMapper photoBoothMapper;
    private final ReviewService reviewService;
    private final PostApiMapper postApiMapper;
    private final ReviewApiMapper reviewApiMapper;
    private final UserService userService;
    private final UserApiMapper userApiMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public UserLikeResponse getLikeResponse() {
        UserDTO userDTO = PrincipleDetail.get();
        List<PhotoBoothEntity> photoBoothEntityList = photoBoothService.findAllByUserLike(userDTO.getId());
        List<PostEntity> postEntityList = postService.findAllByUserLike(userDTO.getId());
        return UserLikeResponse.of(photoBoothMapper.toDtoList(photoBoothEntityList), postApiMapper.toDtoList(postEntityList));
    }

    public UserListResponse getUserListResponse() {
        UserDTO userDTO = PrincipleDetail.get();
        List<ReviewEntity> reviewEntityList = reviewService.findAllByUserId(userDTO.getId());
        List<PostEntity> postEntityList = postService.findAllByUserId(userDTO.getId());
        return UserListResponse.of(reviewApiMapper.toDtoList(reviewEntityList), postApiMapper.toDtoList(postEntityList));
    }

    public UserApiResponse updateName(UserNameUpdateRequest userNameUpdateRequest) {
        UserDTO userDTO = PrincipleDetail.get();
        UserEntity userEntity = userService.getByName(userNameUpdateRequest.getName());
        if (userEntity != null) {
            throw new SilentApplicationErrorException(ApplicationErrorType.ALREADY_NAME);
        }
        userEntity = userService.findById(userDTO.getId());
        userEntity.updateName(userNameUpdateRequest.getName());
        return userApiMapper.toDto(userEntity);
    }

    public UserApiResponse getUserDto() {
        UserDTO userDTO = PrincipleDetail.get();
        return userApiMapper.toApiResponse(userDTO);
    }

    public void inactiveUser() {
        UserDTO userDTO = PrincipleDetail.get();
        UserEntity userEntity = userDTO.getId() != null ? userService.findById(userDTO.getId()) : null;
        postService.findAllByUserId(userEntity.getId()).forEach(post -> post.updateStatus(Status.INACTIVE));
        reviewService.findAllByUserId(userEntity.getId()).forEach(review -> review.updateStatus(Status.INACTIVE));
        userEntity.updateStatus(UserStatus.INACTIVE);
    }

    public JwtToken login(UserLoginRequest userLoginRequest) {
        UserEntity userEntity = userService.getUser(userLoginRequest.getEmail());
        if (userEntity == null) {
            userEntity = userService.createUser(convertToUserEntity(userLoginRequest));
            userEntity.setName("photalks_user_" + userEntity.getId());
        }
        return (jwtTokenProvider.generateToken(userEntity.getEmail()));
    }

    private UserEntity convertToUserEntity(UserLoginRequest userLoginRequest) {
        return UserEntity
                .builder()
                .email(userLoginRequest.getEmail())
                .name("random")
                .status(UserStatus.ACTIVE)
                .userRole(UserRole.USER)
                .upwd(bCryptPasswordEncoder.encode(userLoginRequest.getEmail()))
                .provider(userLoginRequest.getProvider())
                .providerId(userLoginRequest.getProviderId())
                .build();
    }
}
