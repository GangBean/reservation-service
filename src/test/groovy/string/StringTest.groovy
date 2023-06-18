package string


import spock.lang.Specification

class StringTest extends Specification {

    def "스트링의 split은 입력값을 기준으로 스트링을 나눈 배열을 반환합니다."() {
        given:
        def str = "A;B;C;D;E"
        def delimiter = ";"

        when:
        def split = str.split(delimiter)

        then:
        verifyAll {
            split.size() == 5
            split as Set == ["A", "B", "C", "D", "E"] as Set
        }
    }

    def "스트링은 같은 값을 가지면 동등한 객체입니다."() {
        given:
        def str = "홍길동"

        when:
        def same = "홍길동"

        then:
        str == same
    }

    def "스트링은 스트링 풀에서 관리하는 동일한 객체입니다."() {
        given:
        def origin = "홍길동"

        when:
        def duplicate = "홍길동"

        then:
        origin === duplicate
    }

    def "스트링은 new 연산자로 생성시 새로운 heap 객체로 생성됩니다_동일하지않습니다."() {
        given:
        def origin = "홍길동"
        when:
        def duplicate = new String(origin)
        then:
        origin !== duplicate
    }

    def "스트링은 + 연산자를 통해 이어붙일수 있습니다."() {
        given:
        def origin = "홍길동"
        when:
        origin += " by 허균"
        then:
        origin == "홍길동 by 허균"
    }

    def "스트링은 사이즈보다 같거나 큰 인덱스에 접근시 IndexOutOfRange exception을 throw합니다."() {
        given:
        def origin = "12345"
        when:
        origin[6]
        then:
        def e = thrown(IndexOutOfBoundsException)
        e != null
    }
}
