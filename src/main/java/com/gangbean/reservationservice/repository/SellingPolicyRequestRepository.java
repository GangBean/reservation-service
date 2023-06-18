package com.gangbean.reservationservice.repository;

import com.gangbean.reservationservice.domain.policy.SellingPolicyRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellingPolicyRequestRepository extends JpaRepository<SellingPolicyRequest, Long> {

}
