package repository.exceptions;

public class ExceptionConnectionFactory extends RuntimeException {
    public ExceptionConnectionFactory(String message, Throwable cause) {
        super(message, cause);
    }
}
