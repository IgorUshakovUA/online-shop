package com.study.shop.security.entity;

public enum UserRole {
    GUEST("guest"),
    USER("user"),
    ADMIN("admin");

    private final String roleName;

    UserRole(String roleName) {
        this.roleName = roleName;
    }

}
