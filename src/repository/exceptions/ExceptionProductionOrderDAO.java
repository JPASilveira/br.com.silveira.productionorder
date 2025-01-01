package repository.exceptions;

public class ExceptionProductionOrderDAO extends RuntimeException {
    public ExceptionProductionOrderDAO(String message, Throwable cause) {
        super(message, cause);
    }
}
