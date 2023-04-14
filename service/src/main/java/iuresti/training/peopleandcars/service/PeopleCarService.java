package iuresti.training.peopleandcars.service;

import iuresti.training.peopleandcars.exception.MyCarBadRequestException;
import iuresti.training.peopleandcars.exception.MyCarResourceNotFoundException;
import iuresti.training.peopleandcars.modelapi.Car;
import iuresti.training.peopleandcars.modelapi.People;
import iuresti.training.peopleandcars.modelapi.PeopleCar;

import java.util.List;

public interface PeopleCarService {
    List<PeopleCar> fetchAllPeopleCars() throws MyCarResourceNotFoundException;
    List<Car> fetchAllPersonCars(String guid) throws MyCarResourceNotFoundException;
    List<People> fetchAllCarPeople(String vin) throws MyCarResourceNotFoundException;
    PeopleCar addPeopleCar(PeopleCar peopleCar) throws MyCarBadRequestException;
}
