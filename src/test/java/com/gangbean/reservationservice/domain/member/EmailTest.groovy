package com.gangbean.reservationservice.domain.member

import spock.lang.Specification

class EmailTest extends Specification {

    def "이메일은 같은 문자열 값을 갖으면 같은 이메일입니다"() {
        expect:
        new Email("abc1234") == new Email("abc1234")
    }
}
