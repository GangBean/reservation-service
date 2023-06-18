package com.gangbean.reservationservice.repository

import com.gangbean.reservationservice.domain.member.Email
import com.gangbean.reservationservice.domain.member.Member
import com.gangbean.reservationservice.domain.member.MemberTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Shared
import spock.lang.Specification

@SpringBootTest
class MemberRepositoryTest extends Specification {

    @Autowired
    private MemberRepository memberRepository

    @Shared Member saved = null

    def setup() {
        saved = memberRepository.save(MemberTest.TEST_MEMBER)
    }

    def "아이디로 회원을 찾습니다"() {
        when:
        def find = memberRepository.findById(saved.id())

        then:
        verifyAll {
            find.isPresent()
            find.get() == saved
        }
    }

    def "입력된 로그인아이디를 갖는 회원이 존재하면 해당 회원을 찾습니다"() {
        when:
        def find = memberRepository.findByLoginId("abc")

        then:
        verifyAll {
            find.isPresent()
            find.get() == saved
        }
    }

    def "입력된 이메일을 갖는 회원이 존재하면 해당 회원을 찾습니다"() {
        given:
        def email = new Email.EmailBuilder()
                .id(1L)
                .email("email@naver.com")
                .build()

        when:
        def find = memberRepository.findByEmail(email)

        then:
        verifyAll {
            find.isPresent()
            find.get() == saved
        }
    }
}
