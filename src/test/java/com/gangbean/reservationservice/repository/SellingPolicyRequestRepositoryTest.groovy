package com.gangbean.reservationservice.repository


import com.gangbean.reservationservice.domain.policy.SellingPolicyRequestTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class SellingPolicyRequestRepositoryTest extends Specification {

    @Autowired
    private SellingPolicyRequestRepository repository

    def "판매권한요청 리포지토리는 판매권한요청을 저장합니다"() {
        when:
        def saved = repository.save(SellingPolicyRequestTest.TEST_REQUEST)

        then:
        verifyAll {
            saved.requesterId() == SellingPolicyRequestTest.TEST_REQUEST.requesterId()
            saved.status() == SellingPolicyRequestTest.TEST_REQUEST.status()
        }
    }
}
