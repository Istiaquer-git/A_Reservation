package service.exception;

/**
 * Exception thrown when a requested resource is not found
 */
public class ResourceNotFoundException extends ServiceException {
    
    public ResourceNotFoundException(String message) {
        super(message);
    }
}