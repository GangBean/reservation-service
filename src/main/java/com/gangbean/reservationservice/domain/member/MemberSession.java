package com.gangbean.reservationservice.domain.member;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;

@Entity
public class MemberSession {

    public static final long CONNECTION_MINUTES = 60L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long memberId;

    @Column(nullable = false)
    private LocalDateTime lastConnectTime;

    public MemberSession() {}

    @Builder
    public MemberSession(Long memberId, LocalDateTime connectTime) {
        this.memberId = memberId;
        this.lastConnectTime = connectTime;
    }

    public void connect(LocalDateTime connectTime) {
        this.lastConnectTime = connectTime;
    }

    public Long id() {
        return id;
    }

    public LocalDateTime lastConnectTime() {
        return lastConnectTime;
    }

    public Long memberId() {
        return memberId;
    }

    public boolean isConnected(LocalDateTime connectTime) {
        return connectTime.isBefore(connectionLimitTime());
    }

    private LocalDateTime connectionLimitTime() {
        return lastConnectTime.plusMinutes(CONNECTION_MINUTES);
    }
}
