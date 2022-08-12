package com.hot6.phopa.core.security.service;

import com.hot6.phopa.core.domain.user.model.dto.UserDTO;
import com.hot6.phopa.core.domain.user.model.entity.UserEntity;
import com.hot6.phopa.core.domain.user.model.mapper.UserMapper;
import com.hot6.phopa.core.domain.user.service.UserService;
import com.hot6.phopa.core.domain.user.type.UserProvider;
import com.hot6.phopa.core.domain.user.type.UserRole;
import com.hot6.phopa.core.domain.user.type.UserStatus;
import com.hot6.phopa.core.security.config.PrincipleDetail;
import com.hot6.phopa.core.security.jwt.JwtTokenProvider;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserService userService;

    private final UserMapper userMapper;

    private final JwtTokenProvider jwtTokenProvider;

    // oauth 로그인 시 받은 userRequest 데이터에 대한 후처리 함수
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuth2Attribute oAuth2Attribute =
                OAuth2Attribute.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        UserDTO userDTO = new UserDTO(
                null,
                oAuth2Attribute.getEmail(),
                "random",
                bCryptPasswordEncoder.encode(oAuth2Attribute.getEmail()),
                UserStatus.ACTIVE,
                UserRole.USER,
                oAuth2Attribute.getProvider(),
                oAuth2Attribute.getAttributeKey()
        );

        UserEntity userEntity = userMapper.toEntity(userDTO);
        return new PrincipleDetail(userEntity, oAuth2User.getAttributes(), jwtTokenProvider.generateToken(userEntity.getEmail()));
    }

    @ToString
    @Builder(access = AccessLevel.PRIVATE)
    @Getter
    private static class OAuth2Attribute {
        private Map<String, Object> attributes;
        private UserProvider provider;
        private String attributeKey;
        private String email;

        public static OAuth2Attribute of(String provider, String attributeKey,
                                         Map<String, Object> attributes) {
            switch (provider) {
                case "google":
                    return ofGoogle(attributeKey, attributes);
                case "kakao":
                    return ofKakao("email", attributes);
                case "naver":
                    return ofNaver("id", attributes);
                default:
                    throw new RuntimeException();
            }
        }

        private static OAuth2Attribute ofGoogle(String attributeKey,
                                                Map<String, Object> attributes) {
            return OAuth2Attribute.builder()
                    .provider(UserProvider.GOOGLE)
                    .email((String) attributes.get("email"))
                    .attributes(attributes)
                    .attributeKey(attributeKey)
                    .build();
        }

        private static OAuth2Attribute ofKakao(String attributeKey,
                                               Map<String, Object> attributes) {
            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");

            return OAuth2Attribute.builder()
                    .provider(UserProvider.KAKAO)
                    .email((String) kakaoAccount.get("email"))
                    .attributes(kakaoAccount)
                    .attributeKey(attributeKey)
                    .build();
        }

        private static OAuth2Attribute ofNaver(String attributeKey,
                                               Map<String, Object> attributes) {
            Map<String, Object> response = (Map<String, Object>) attributes.get("response");

            return OAuth2Attribute.builder()
                    .provider(UserProvider.NAVER)
                    .email((String) response.get("email"))
                    .attributes(response)
                    .attributeKey(attributeKey)
                    .build();
        }
    }
}
