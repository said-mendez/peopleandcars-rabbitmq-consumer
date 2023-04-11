package iuresti.training.peopleandcars.modelapi;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class PeopleAndCarsError {
    private String message;
    private ZonedDateTime timestamp;

}
