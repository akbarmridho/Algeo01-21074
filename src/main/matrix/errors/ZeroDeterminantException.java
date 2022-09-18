package main.matrix.errors;

public class ZeroDeterminantException extends Exception {
    ZeroDeterminantException(String errorMessage) {
        super(errorMessage);
    }
}
