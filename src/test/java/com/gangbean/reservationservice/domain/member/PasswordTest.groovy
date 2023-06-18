package com.gangbean.reservationservice.domain.member

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class PasswordTest extends Specification {

    @Shared Cipher cipher

    def setupSpec() {
        cipher = new CipherSha512();
    }

    def "다른 Cipher와 같은 문자열을 갖는 비밀번호는 다른 비밀번호입니다"() {
        expect:
        new Password.PasswordBuilder().cipherName((plain -> plain).getClass().getName()).password("abc")
                != new Password.PasswordBuilder().cipherName((plain -> plain.reverse()).getClass().getName()).password("abc")
    }


    def "같은 CipherName과 같은 문자열을 갖는 비밀번호는 같은 비밀번호입니다"() {
        expect:
        new Password.PasswordBuilder().cipherName(CipherSha512.getName()).password("abc").build()
                == new Password.PasswordBuilder().cipherName(CipherSha512.getName()).password(new String("abc")).build()
    }

    def "비밀번호는 입력된 문자열과 알고리즘에 대해 동일여부를 판단합니다"(String plain, boolean expected) {
        given:
        String input = cipher.encryption("abcd12345")
        Password password = new Password(cipher, input)

        when:
        boolean isSame = password.isSame(plain)

        then:
        isSame == expected

        where:
        plain | expected
        "abcd12345" | true
        "aaa123455" | false
    }

    @Unroll
    def "비밀번호는 널이거나 빈 문자열이면 안됩니다"(String input) {
        when:
        new Password(cipher, input)

        then:
        RuntimeException e = thrown()
        String message = e.getMessage()

        expect:
        message == "비밀번호는 빈 값일 수 없습니다."

        where:
        input << [null, "", " ", "    "]
    }
}