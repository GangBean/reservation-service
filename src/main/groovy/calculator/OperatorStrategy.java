package calculator;

@FunctionalInterface
public interface OperatorStrategy {
    int result(int first, int second);

}
