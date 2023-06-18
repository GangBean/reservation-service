package com.gangbean.reservationservice.domain.policy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;

@Entity
public class SellingPolicyRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    private Long requesterId;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    public SellingPolicyRequest() {}

    @Builder
    public SellingPolicyRequest(Long requesterId) {
        this.requesterId = requesterId;
        this.status = Status.REQUESTING;
    }

    public void approve() {
        status = Status.APPROVED;
    }

    public Status status() {
        return status;
    }

    public void reject() {
        status = Status.REJECTED;
    }

    public Long requesterId() {
        return requesterId;
    }
}
