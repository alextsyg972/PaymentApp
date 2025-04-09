package my.project.paymentapp.exception;

import org.springframework.http.HttpStatus;

public class NotEnoughWalletAmountException extends RuntimeException{

    public NotEnoughWalletAmountException(String message) {
        super(message);
    }

    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
