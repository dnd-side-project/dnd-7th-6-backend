package com.hot6.phopa.core.security.jwt.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthTokenType {

    BEARER_TYPE("Bearer ");

    private final String tokenType;
}

