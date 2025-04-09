package my.project.paymentapp.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorDto {

    private HttpStatus status;
    private String message;
    private LocalDateTime localDateTime;

    public ErrorDto() {
    }

    public ErrorDto(HttpStatus status, String message, LocalDateTime localDateTime) {
        this.status = status;
        this.message = message;
        this.localDateTime = localDateTime;
    }


}
