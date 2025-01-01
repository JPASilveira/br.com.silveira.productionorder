package repository.exceptions;

public class ExceptionProductDAO extends RuntimeException {
    public ExceptionProductDAO(String message, Throwable cause) {
        super(message, cause);
    }
}
