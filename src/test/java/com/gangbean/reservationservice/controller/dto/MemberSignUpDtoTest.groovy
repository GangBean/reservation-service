package com.gangbean.reservationservice.controller.dto

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import javax.validation.Validation
import javax.validation.ValidatorFactory
import java.util.stream.Collectors

class MemberSignUpDtoTest extends Specification {

    @Shared String defaultLoginId = "abcde"
    @Shared String defaultEmail = "abc@abc.com"
    @Shared String defaultPassword = "123456789"
    @Shared String defaultName = "이름"

    @Unroll
    def "비밀번호는 형식을 체크합니다"(String password) {
        given:
        def dto = new MemberSignUpDto.MemberSignUpDtoBuilder()
                .loginId("id")
                .password(password)
                .email("aaa@aaaa.com")
                .name("name")
                .build()
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        def validator = factory.getValidator()
        def violations = validator.validate(dto)
        def booleans = violations.stream()
                .map(it -> it.getMessage() == MemberSignUpDto.INVALID_PASSWORD_PATTERN_MESSAGE)
                .collect(Collectors.toList())

        expect:
        verifyAll {
            !booleans.isEmpty()
            !booleans.contains(false)
        }

        where:
        password | _
        "1234" | _
        "1234567))" | _
        "한글로비밀번호불가" | _
        "~~~~~~~~[][][{}" | _
        "123456789012345678901" | _
    }

    @Unroll
    def "이메일은 형식을 체크합니다"(String email) {
        given:
        def dto = new MemberSignUpDto.MemberSignUpDtoBuilder()
                .loginId("id")
                .password("password12345")
                .email(email)
                .name("name")
                .build()
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        def validator = factory.getValidator()
        def violations = validator.validate(dto)
        def booleans = violations.stream()
                .map(it -> it.getMessage() == MemberSignUpDto.INVALID_EMAIL_PATTERN_MESSAGE)
                .collect(Collectors.toList())

        expect:
        verifyAll {
            !booleans.isEmpty()
            !booleans.contains(false)
        }

        where:
        email | _
        "@b.b" | _
        "ab.b" | _
        "a@.com" | _
        "a@a-coms" | _
    }

    @Unroll
    def "문자열 형태의 로그인 아이디, 비밀번호, 이메일, 이름이 필수입니다"(String loginId, String password, String email, String name, String expected) {
        def memberSignUpDto = new MemberSignUpDto.MemberSignUpDtoBuilder()
                .loginId(loginId)
                .password(password)
                .email(email)
                .name(name)
                .build()

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        def validator = factory.getValidator()
        def violations = validator.validate(memberSignUpDto)
        def booleans = violations.stream()
                .map(it -> it.getMessage() == expected)
                .collect(Collectors.toList())

        expect:
        verifyAll {
            !booleans.isEmpty()
            booleans.contains(true)
        }

        cleanup:
        factory.close()

        where:
        loginId | password | email | name | expected
        null | defaultPassword | defaultEmail | defaultName | "아이디는 빈 값일 수 없습니다."
        "" | defaultPassword | defaultEmail | defaultName | "아이디는 빈 값일 수 없습니다."
        " " | defaultPassword | defaultEmail | defaultName | "아이디는 빈 값일 수 없습니다."
        defaultLoginId | null | defaultEmail | defaultName | "비밀번호는 빈 값일 수 없습니다."
        defaultLoginId | "" | defaultEmail | defaultName | "비밀번호는 빈 값일 수 없습니다."
        defaultLoginId | " " | defaultEmail | defaultName | "비밀번호는 빈 값일 수 없습니다."
        defaultLoginId | defaultPassword | null | defaultName | "이메일은 빈 값일 수 없습니다."
        defaultLoginId | defaultPassword | "" | defaultName | "이메일은 빈 값일 수 없습니다."
        defaultLoginId | defaultPassword | " " | defaultName | "이메일은 빈 값일 수 없습니다."
        defaultLoginId | defaultPassword | defaultEmail | null | "이름은 빈 값일 수 없습니다."
        defaultLoginId | defaultPassword | defaultEmail | "" | "이름은 빈 값일 수 없습니다."
        defaultLoginId | defaultPassword | defaultEmail | " " | "이름은 빈 값일 수 없습니다."
    }
}
