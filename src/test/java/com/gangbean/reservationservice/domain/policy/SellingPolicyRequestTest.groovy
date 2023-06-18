package com.gangbean.reservationservice.domain.policy

import spock.lang.Specification

class SellingPolicyRequestTest extends Specification {

    public static final SellingPolicyRequest TEST_REQUEST = new SellingPolicyRequest.SellingPolicyRequestBuilder()
        .requesterId(1L)
        .build()

    def "판매권한요청은 생성시 요청중입니다"() {
        given:
        def request = new SellingPolicyRequest(1L)

        when:
        def status = request.status()

        then:
        status == Status.REQUESTING
    }

    def "판매권한요청은 요청자id를 알려줍니다"() {
        given:
        def request = new SellingPolicyRequest(1L)

        when:
        Long memberId = request.requesterId()

        then:
        memberId == 1L
    }

    def "판매권한요청은 거절가능합니다"() {
        given:
        def request = new SellingPolicyRequest()

        when:
        request.reject()

        then:
        request.status() == Status.REJECTED
    }

    def "판매권한요청은 승인가능합니다"() {
        given:
        def request = new SellingPolicyRequest()

        when:
        request.approve()

        then:
        request.status() == Status.APPROVED
    }
}
