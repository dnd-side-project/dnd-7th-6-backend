package com.hot6.phopa.core.security.config;

import com.hot6.phopa.core.domain.user.model.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

public class PrincipleDetail implements UserDetails, OAuth2User {

    private final UserEntity user;
    private Map<String, Object> attributes;

    public PrincipleDetail(UserEntity user){
        this.user = user;
    }

    public PrincipleDetail(UserEntity user, Map<String, Object> attributes){
        this.user = user;
        this.attributes = attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority(user.getUserRole().getAuthority()));
    }

    @Override
    public String getPassword() {
        return user.getUpwd();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        //필수 override 항목이나 미사용으로 null 반환
        return null;
    }
}
