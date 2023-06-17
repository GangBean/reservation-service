package com.gangbean.reservationservice.domain.member

import spock.lang.Specification
import spock.lang.Unroll

class RoleTest extends Specification {

    @Unroll
    def "역할은 본인이 매니저 인지를 판단해줍니다"(Role role, boolean expected) {
        expect:
        role.isManager() == expected

        where:
        role | expected
        Role.GUEST | false
        Role.MEMBER | false
        Role.MANAGER | true
    }

    @Unroll
    def "역할은 손님, 회원, 매니저 3가지 종류를 갖습니다"(String name, Role expected) {
        expect:
        expected.name() == name

        where:
        name | expected
        "GUEST" | Role.GUEST
        "MEMBER" | Role.MEMBER
        "MANAGER" | Role.MANAGER
    }
}
