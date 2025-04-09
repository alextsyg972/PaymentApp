package my.project.paymentapp.exception;

import my.project.paymentapp.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class WalletExceptionHandler {


    @ExceptionHandler(WalletNotFoundException.class)
    public ResponseEntity<ErrorDto> handleNotFoundUUID(WalletNotFoundException exception) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage(exception.getMessage());
        errorDto.setStatus(exception.getStatus());
        errorDto.setLocalDateTime(LocalDateTime.now());
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WrongOperationTypeException.class)
    public ResponseEntity<ErrorDto> handleWrongOperationType(WrongOperationTypeException exception) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage(exception.getMessage());
        errorDto.setStatus(exception.getStatus());
        errorDto.setLocalDateTime(LocalDateTime.now());
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotEnoughWalletAmountException.class)
    public ResponseEntity<ErrorDto> handleNotEnoughAmount(NotEnoughWalletAmountException exception) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage(exception.getMessage());
        errorDto.setStatus(exception.getStatus());
        errorDto.setLocalDateTime(LocalDateTime.now());
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorDto>> handleValidationExceptions(MethodArgumentNotValidException exception) {
        List<ErrorDto> errors = exception
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError -> FieldError.getField() + " " + FieldError.getDefaultMessage())
                .map(msg -> new ErrorDto(HttpStatus.BAD_REQUEST, msg, LocalDateTime.now()))
                .toList();
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorDto> handleWrongUUIDJson(MethodArgumentTypeMismatchException exception) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage(exception.getCause().getMessage());
        errorDto.setStatus(HttpStatus.BAD_REQUEST);
        errorDto.setLocalDateTime(LocalDateTime.now());
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDto> handleWrongUUIDJson(HttpMessageNotReadableException exception) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage(exception.getMessage());
        errorDto.setStatus(HttpStatus.BAD_REQUEST);
        errorDto.setLocalDateTime(LocalDateTime.now());
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OperationTypeNotFoundException.class)
    public ResponseEntity<ErrorDto> handleNullOperationType(OperationTypeNotFoundException exception) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage(exception.getMessage());
        errorDto.setStatus(HttpStatus.BAD_REQUEST);
        errorDto.setLocalDateTime(LocalDateTime.now());
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

}
