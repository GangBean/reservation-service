package com.gangbean.reservationservice.domain.member;

public enum Role {
    GUEST("손님"),
    MEMBER("회원"),
    MANAGER("매니저");

    private final String name;

    Role(String name) {
        this.name = name;
    }

    public boolean isManager() {
        return this == MANAGER;
    }
}
