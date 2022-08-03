package com.hot6.phopa.core.domain.user.model.dto;

import com.hot6.phopa.core.domain.user.type.UserProvider;
import com.hot6.phopa.core.domain.user.type.UserStatus;
import com.hot6.phopa.core.domain.user.type.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String email;
    private String name;
//    private String upwd;
    private UserStatus status;
    private UserRole userRole;
    private UserProvider userProvider;
    private String providerId;
}
