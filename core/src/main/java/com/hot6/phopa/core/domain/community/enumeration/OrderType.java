package com.hot6.phopa.core.domain.community.enumeration;

public enum OrderType {
    popular("인기순"),
    latest("최신순"),
    ;
    private String description;

    OrderType(String description) {
        this.description = description;
    }
}
