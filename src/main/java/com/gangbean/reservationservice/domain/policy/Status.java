package com.gangbean.reservationservice.domain.policy;

public enum Status {
    REQUESTING("요청중"),
    APPROVED("승인"),
    REJECTED("거절");

    private final String description;

    Status(String description) {
        this.description = description;
    }

    public String description() {
        return description;
    }

    public boolean isRequesting() {
        return this == REQUESTING;
    }

    public boolean isApproved() {
        return this == APPROVED;
    }

    public boolean isRejected() {
        return this == REJECTED;
    }
}
