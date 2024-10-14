package repository.exceptions;

public class ExceptionGroupDAO extends RuntimeException {
    public ExceptionGroupDAO(String message, Throwable cause) {
        super(message, cause);
    }
}
