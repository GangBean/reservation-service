package calculator;

import java.util.Objects;

public class PositiveNumber {

    private final int number;

    public PositiveNumber(int number) {
        this.number = valid(number);
    }

    private int valid(int number) {
        if (number <= 0) {
            throw new RuntimeException("양의 정수가 아닙니다 : " + number);
        }
        return number;
    }

    public PositiveNumber calculationOf(OperatorStrategy operator, PositiveNumber operand) {
        return new PositiveNumber(operator.result(number, operand.number));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PositiveNumber that = (PositiveNumber) o;
        return number == that.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}
