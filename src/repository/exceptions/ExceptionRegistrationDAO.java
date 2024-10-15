package repository.exceptions;

public class ExceptionRegistrationDAO extends RuntimeException {
    public ExceptionRegistrationDAO(String message, Throwable cause) {
        super(message, cause);
    }
}
