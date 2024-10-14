package repository.exceptions;

public class ExceptionUnitDAO extends RuntimeException {
    public ExceptionUnitDAO(String message, Throwable cause) {
        super(message, cause);
    }
}
