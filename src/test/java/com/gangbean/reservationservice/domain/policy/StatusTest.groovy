package com.gangbean.reservationservice.domain.policy

import spock.lang.Specification

import java.util.stream.Collectors

class StatusTest extends Specification {

    def "상태는 승인되었는지 알려줍니다"(Status status, boolean expected) {
        expect:
        expected == status.isApproved()

        where:
        status        | expected
        Status.APPROVED   | true
        Status.REQUESTING | false
        Status.REJECTED   | false
    }

    def "상태는 거절되었는지 알려줍니다"(Status status, boolean expected) {
        expect:
        expected == status.isRejected()

        where:
        status        | expected
        Status.REJECTED   | true
        Status.REQUESTING | false
        Status.APPROVED   | false
    }

    def "상태는 요청중인지를 알려줍니다"(Status status, boolean expected) {
        expect:
        expected == status.isRequesting()

        where:
            status        | expected
        Status.REQUESTING | true
        Status.APPROVED   | false
        Status.REJECTED   | false
    }

    def "상태는 요청중, 승인, 거절을 갖습니다"() {
        expect:
        Arrays.stream(Status.values())
                .map(s -> s.description())
                .collect(Collectors.toSet()) == Set.of("요청중", "승인", "거절")
    }
}
