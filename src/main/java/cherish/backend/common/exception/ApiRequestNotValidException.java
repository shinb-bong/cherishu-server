package cherish.backend.common.exception;

public class ApiRequestNotValidException extends RuntimeException {
    public ApiRequestNotValidException(String message) {
        super(message);
    }
}
