package service;

public class FlooringMasteryInvalidOrderException extends Exception {
    public FlooringMasteryInvalidOrderException(String message) {
        super(message);
    }

    public FlooringMasteryInvalidOrderException(String message, Throwable cause) {
        super(message, cause);
    }
}
