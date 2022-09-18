package main.matrix.errors;

public class MatrixMultiplicationException extends Exception {
    MatrixMultiplicationException(String errorMessage) {
        super(errorMessage);
    }
}
