package iuresti.training.peopleandcars.controller;

import iuresti.training.peopleandcars.exception.MyCarBadRequestException;
import iuresti.training.peopleandcars.exception.MyCarResourceNotFoundException;
import iuresti.training.peopleandcars.modelapi.People;
import iuresti.training.peopleandcars.modelapi.PeopleAndCarsError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class PeopleAndCarsExceptionHandler {
    public PeopleAndCarsError createPeopleAndCarsError(String exceptionMessage) {
        PeopleAndCarsError peopleAndCarsError = new PeopleAndCarsError ();
        peopleAndCarsError.setMessage(exceptionMessage);
        peopleAndCarsError.setTimestamp(ZonedDateTime.now());

        return peopleAndCarsError;
    }
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<PeopleAndCarsError> handleException(Exception exception) {
        PeopleAndCarsError peopleAndCarsError = createPeopleAndCarsError(exception.getMessage());

        return new ResponseEntity<>(peopleAndCarsError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = MyCarResourceNotFoundException.class)
    public ResponseEntity<PeopleAndCarsError> handleNotFoundCar(MyCarResourceNotFoundException myCarResourceNotFoundException) {
        PeopleAndCarsError peopleAndCarsError = createPeopleAndCarsError(myCarResourceNotFoundException.getMessage());

        return new ResponseEntity<>(peopleAndCarsError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = MyCarBadRequestException.class)
    public ResponseEntity<PeopleAndCarsError> handleBadRequestException(MyCarBadRequestException myCarBadRequestException) {
        PeopleAndCarsError peopleAndCarsError = createPeopleAndCarsError(myCarBadRequestException.getMessage());

        return new ResponseEntity<>(peopleAndCarsError, HttpStatus.BAD_REQUEST);
    }
}
