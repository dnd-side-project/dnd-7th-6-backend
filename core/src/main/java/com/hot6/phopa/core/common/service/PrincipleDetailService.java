package com.hot6.phopa.core.common.service;

import com.hot6.phopa.core.common.exception.AdminErrorType;
import com.hot6.phopa.core.common.exception.SilentAdminErrorException;
import com.hot6.phopa.core.security.config.PrincipleDetail;
import com.hot6.phopa.core.domain.user.model.entity.UserEntity;
import com.hot6.phopa.core.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrincipleDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findByEmail(username);
        if(userEntity.isPresent()){
            return new PrincipleDetail(userEntity.get());
        }
        throw new SilentAdminErrorException(AdminErrorType.UNKNOWN_USER);
    }
}
