package service.exception;

/**
 * Exception thrown when business validation fails
 */
public class ValidationException extends ServiceException {
    
    public ValidationException(String message) {
        super(message);
    }
}