package main.SPL.errors;

public class NoSolutionException extends Exception {
    public NoSolutionException(String errorMessage) {
        super(errorMessage);
    }
}
