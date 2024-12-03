package az.pashabank.msparnik.model;

public class IllegalCommandException extends RuntimeException {
    public String message;

    public IllegalCommandException(String message) {
        super(message);
    }
}
