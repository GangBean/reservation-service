package com.gangbean.reservationservice.domain.member

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class PasswordTest extends Specification {

    @Shared Cipher cipher

    def setupSpec() {
        cipher = new CipherSha512();
    }

    def "비밀번호는 입력된 문자열에 대해 동일여부를 판단합니다"(String origin, boolean expected) {
        given:
        String input = "abcd12345"
        Password password = new Password(origin)

        when:
        boolean isSame = password.isSame(input)

        then:
        isSame == expected

        where:
        origin | expected
        "abcd12345" | true
        "aaa123455" | false
    }

    @Unroll
    def "비밀번호는 널이거나 빈 문자열이면 안됩니다"(String input) {
        when:
        new Password(input)

        then:
        RuntimeException e = thrown()
        String message = e.getMessage()

        expect:
        message == "비밀번호는 빈 값일 수 없습니다."

        where:
        input << [null, "", " ", "    "]
    }
}