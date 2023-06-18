package com.gangbean.reservationservice.domain.member

import spock.lang.Specification

import java.time.LocalDateTime

class MemberSessionTest extends Specification {

    public static MemberSession TEST_SESSION = new MemberSession.MemberSessionBuilder()
            .memberId(1L)
            .connectTime(LocalDateTime.of(2023,6,18,0,0,0))
            .build()

    def "세션은 시간을 입력받아 연결된 상태인지 알려줍니다_60분 동안 유효합니다"(Long minute, Long second, boolean expected) {
        given:
        Long memberId = 1L
        def prev = LocalDateTime.of(2023, 6, 18, 7, 43, 0)
        def session = new MemberSession.MemberSessionBuilder().connectTime(prev).memberId(memberId).build()

        expect:
        expected == session.isConnected(prev.plusMinutes(minute).plusSeconds(second))

        where:
        minute | second | expected
        0L     | 0L     | true
        59L    | 58L    | true
        59L    | 59L    | true
        60L    | 0L     | false
        60L    | 1L     | false
    }

    def "세션은 고객의 아이디 정보를 기록합니다"() {
        given:
        Long memberId = 1L
        def prev = LocalDateTime.of(2023, 6, 18, 7, 43, 0)
        def session = new MemberSession.MemberSessionBuilder().connectTime(prev).memberId(memberId).build()

        when:
        def id = session.memberId()

        then:
        id == memberId
    }

    def "세션은 마지막 접근시간을 기록합니다"() {
        given:
        def prev = LocalDateTime.of(2023, 6, 18, 7, 43, 0)
        def session = new MemberSession.MemberSessionBuilder().connectTime(prev).build()
        LocalDateTime next = prev.plusMinutes(7L)

        when:
        session.connect(next)

        then:
        session.lastConnectTime() == next
    }
}
