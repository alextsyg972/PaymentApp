package my.project.paymentapp.exception;

import org.springframework.http.HttpStatus;

public class WrongOperationTypeException extends RuntimeException {

    public WrongOperationTypeException(String message) {
        super(message);
    }

    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
