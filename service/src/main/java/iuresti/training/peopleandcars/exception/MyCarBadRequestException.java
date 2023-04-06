package iuresti.training.peopleandcars.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MyCarBadRequestException extends RuntimeException{
    public MyCarBadRequestException(String message) {
        super(message);
    }
}
