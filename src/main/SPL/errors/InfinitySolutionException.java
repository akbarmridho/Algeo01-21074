package main.SPL.errors;

public class InfinitySolutionException extends Exception {
    InfinitySolutionException(String errorMessage) {
        super(errorMessage);
    }
}
