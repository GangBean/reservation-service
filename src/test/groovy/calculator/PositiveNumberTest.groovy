package calculator

import spock.lang.Specification
import spock.lang.Unroll

class PositiveNumberTest extends Specification {

    @Unroll
    def "PositiveNumber 의 계산은 입력된 연산자의 결과를 한번 수행한 결과입니다."() {
        given:
        OperatorStrategy operator = Mock()

        when:
        new PositiveNumber(1).calculationOf(operator, new PositiveNumber(1))

        then:
        thrown(RuntimeException)
        1 * operator.result(1, 1)
    }

    @Unroll
    def "PositiveNumber 의 계산결과는 연산자에 따라 달라집니다."(int a, int b) {
        OperatorStrategy operator = (x, y) -> Integer.MAX_VALUE
        PositiveNumber num1 = new PositiveNumber(a)
        PositiveNumber num2 = new PositiveNumber(b)

        expect:
        num1.calculationOf(operator, num2) == new PositiveNumber(Integer.MAX_VALUE)

        where:
        a | b
        1 | 2
        1 | 3
        2 | 1
        Integer.MAX_VALUE | Integer.MAX_VALUE
    }

    @Unroll
    def "PositiveNumber 는 양의 정수만 가질수 있습니다."(int number, String message) {
        when:
        new PositiveNumber(number);

        then:
        RuntimeException e = thrown()
        String error = e.getMessage()

        expect:
        error == message

        where:
        number | message
        0       | "양의 정수가 아닙니다 : 0"
        -1      | "양의 정수가 아닙니다 : -1"
    }

    @Unroll
    def "+ 연산과 피연산자를 넣어주면 더한 숫자를 갖는 PositiveNumber를 리턴합니다."(int a, int b) {
        given:
        def num1 = new PositiveNumber(a)
        def num2 = new PositiveNumber(b)

        expect:
        num1.calculationOf((x, y) -> x + y, num2) == new PositiveNumber(a + b)

        where:
        a | b
        1 | 1
        1 | 2
        2 | 3
    }

    @Unroll
    def "- 연산자와 피연산자를 넣어주면 뺀 숫자를 갖는 PositiveNumber 를 리턴합니다."(int a, int b) {
        given:
        def num1 = new PositiveNumber(a)
        def num2 = new PositiveNumber(b)
        expect:
        num1.calculationOf((x,y)-> x - y, num2) == new PositiveNumber(a - b)

        where:
        a | b
        2 | 1
        3 | 1
        4 | 2
    }

    @Unroll
    def "* 연산자와 피연산자를 넣어주면 곱한 숫자를 갖는 PositiveNumber 를 리턴합니다."(int a, int b) {
        given:
        def num1 = new PositiveNumber(a)
        def num2 = new PositiveNumber(b)
        expect:
        num1.calculationOf((x,y) -> x * y, num2) == new PositiveNumber(a * b)

        where:
        a | b
        2 | 1
        3 | 1
        4 | 2
    }

    @Unroll
    def "/ 연산자와 피연산자를 넣어주면 나눈 숫자를 갖는 PositiveNumber 를 리턴합니다."(int a, int b) {
        given:
        def num1 = new PositiveNumber(a)
        def num2 = new PositiveNumber(b)

        expect:
        num1.calculationOf((x, y) -> {
            if (x % y != 0) {
                throw new RuntimeException("나누어 떨어지지 않는 수 입니다 : " + y)
            }
            return x / y as int
        }, num2) == new PositiveNumber(a / b as int)

        where:
        a | b
        2 | 1
        3 | 1
        4 | 2
    }

    @Unroll
    def "/ 연산자는 operand 로 나누어 떨어지지 않으면 연산이 불가합니다."(int a, int b, String message) {
        given:
        def num1 = new PositiveNumber(a)
        def num2 = new PositiveNumber(b)

        when:
        num1.calculationOf((x, y) -> {
            if (x % y != 0) {
                throw new RuntimeException("나누어 떨어지지 않는 수 입니다 : " + y)
            }
            return x / y as int
        }, num2)

        then:
        def e = thrown(RuntimeException)
        def errorMessage = e.getMessage()

        expect:
        errorMessage == message

        where:
        a | b | message
        1 | 2 | "나누어 떨어지지 않는 수 입니다 : 2"
        3 | 2 | "나누어 떨어지지 않는 수 입니다 : 2"
        5 | 4 | "나누어 떨어지지 않는 수 입니다 : 4"
    }
}
