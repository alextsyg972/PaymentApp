package my.project.paymentapp.exception;

import org.springframework.http.HttpStatus;

public class OperationTypeNotFoundException extends RuntimeException{
    public OperationTypeNotFoundException(String message) {
        super(message);
    }

    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
