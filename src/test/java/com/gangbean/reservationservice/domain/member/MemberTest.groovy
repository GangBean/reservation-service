package com.gangbean.reservationservice.domain.member

import spock.lang.Shared
import spock.lang.Specification

class MemberTest extends Specification {

    @Shared Member member
    @Shared String name = "이름"
    @Shared String email = "email"
    @Shared String encrypt = CipherSha512.getInstance().encryption("abc123")
    @Shared String loginId = "abc"
    @Shared Role role = Role.MEMBER

    def setup() {
        member = new Member(loginId, new Password(CipherSha512.getInstance(), encrypt), new Email(email), name, role)
    }

    def "판매자 권한으로 변경합니다"() {
        when:
        member.becomeSeller()

        then:
        member.isSeller() == true
    }

    def "매니저 권한을 가지고 있는지 판단합니다"(Role role, boolean expected) {
        expect:
        new Member.MemberBuilder().role(role).build().isManager() == expected

        where:
        role | expected
        Role.MANAGER | true
        Role.MEMBER | false
        Role.GUEST | false
        Role.SELLER | false
    }

    def "회원 권한을 가지고 있는지 판단합니다"(Role role, boolean expected) {
        expect:
        new Member.MemberBuilder().role(role).build().isMember() == expected

        where:
        role | expected
        Role.MEMBER | true
        Role.SELLER | true
        Role.GUEST | false
        Role.MANAGER | false
    }

    def "빌더 패턴으로 정상 생성됩니다"() {
        expect:
        member == new Member.MemberBuilder()
                .loginId(loginId)
                .password(new Password(CipherSha512.getInstance(), encrypt))
                .email(new Email(email))
                .name(name)
                .role(role)
                .build();
    }

    def "멤버는 입력된 평문 패스워드가 일치하는지 판단합니다"(String input, boolean expected) {
        expect:
        member.isPasswordMatch(input) == expected

        where:
        input | expected
        encrypt | false
        "abc123" | true
    }

    def "멤버는 같은 이름, 이메일, 패스워드, 로그인아이디를 가지면 같은 멤버입니다"() {
        expect:
        member == new Member(loginId, new Password(CipherSha512.getInstance(), encrypt), new Email(email), name, role)
    }
}
