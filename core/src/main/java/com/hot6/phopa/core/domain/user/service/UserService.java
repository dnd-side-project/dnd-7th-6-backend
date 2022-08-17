package com.hot6.phopa.core.domain.user.service;

import com.hot6.phopa.core.common.exception.ApplicationErrorType;
import com.hot6.phopa.core.common.exception.SilentAdminErrorException;
import com.hot6.phopa.core.common.exception.SilentApplicationErrorException;
import com.hot6.phopa.core.common.model.type.Status;
import com.hot6.phopa.core.domain.user.model.entity.UserEntity;
import com.hot6.phopa.core.domain.user.repository.UserRepository;
import com.hot6.phopa.core.domain.user.type.UserStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.lang.model.type.ErrorType;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
@Order(1)
public class UserService {
    private final UserRepository userRepository;

    public UserEntity createUser(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    public UserEntity getUser(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public Optional<UserEntity> getUserByStatus(String email) {
        return userRepository.findByEmailAndStatus(email, UserStatus.ACTIVE);
    }

    public UserEntity findById(Long id) { return userRepository.findById(id).orElseThrow(() -> new SilentApplicationErrorException(ApplicationErrorType.COULDNT_FIND_ANY_DATA));}

    public UserEntity getByName(String name) { return userRepository.findByName(name);}
}
