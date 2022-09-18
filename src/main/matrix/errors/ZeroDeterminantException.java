package main.matrix.errors;

public class ZeroDeterminantException extends Exception {
    public ZeroDeterminantException(String errorMessage) {
        super(errorMessage);
    }
}
