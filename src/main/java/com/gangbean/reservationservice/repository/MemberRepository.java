package com.gangbean.reservationservice.repository;

import com.gangbean.reservationservice.domain.member.Email;
import com.gangbean.reservationservice.domain.member.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(Email email);

    Optional<Member> findByLoginId(String loginId);

}
