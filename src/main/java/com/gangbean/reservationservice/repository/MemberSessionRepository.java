package com.gangbean.reservationservice.repository;

import com.gangbean.reservationservice.domain.member.MemberSession;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberSessionRepository extends JpaRepository<MemberSession, Long> {

    Optional<MemberSession> findByMemberId(Long memberId);
}
