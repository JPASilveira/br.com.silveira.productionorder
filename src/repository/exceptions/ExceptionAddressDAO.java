package repository.exceptions;

public class ExceptionAddressDAO extends RuntimeException {
    public ExceptionAddressDAO(String message, Throwable cause) {
        super(message, cause);
    }
}
