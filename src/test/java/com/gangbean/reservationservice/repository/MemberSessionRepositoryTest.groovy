package com.gangbean.reservationservice.repository

import com.gangbean.reservationservice.domain.member.MemberSessionTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class MemberSessionRepositoryTest extends Specification {

    @Autowired
    private MemberSessionRepository repository

    def cleanup() {
        repository.deleteAll()
    }

    def "세션리포지토리는 전달한 세션정보를 삭제합니다"() {
        setup:
        def saved = repository.save(MemberSessionTest.TEST_SESSION)

        when:
        repository.delete(saved)

        then:
        repository.findById(saved.id()).isEmpty()
    }

    def "세션리포지토리는 멤버의 아이디에 해당하는 세션정보를 돌려줍니다"() {
        setup:
        repository.save(MemberSessionTest.TEST_SESSION)

        when:
        def session = repository.findByMemberId(MemberSessionTest.TEST_SESSION.memberId())

        then:
        verifyAll {
            session.isPresent()
            session.get().memberId() == MemberSessionTest.TEST_SESSION.memberId()
            session.get().lastConnectTime() == MemberSessionTest.TEST_SESSION.lastConnectTime()
        }
    }

    def "세션리포지토리는 전달한 세션정보를 저장합니다"() {
        when:
        def saved = repository.save(MemberSessionTest.TEST_SESSION)

        then:
        verifyAll {
            saved.memberId() == MemberSessionTest.TEST_SESSION.memberId()
            saved.lastConnectTime() == MemberSessionTest.TEST_SESSION.lastConnectTime()
        }
    }
}
