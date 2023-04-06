package iuresti.training.peopleandcars.service;

import iuresti.training.peopleandcars.exception.MyCarBadRequestException;
import iuresti.training.peopleandcars.exception.MyCarResourceNotFoundException;
import iuresti.training.peopleandcars.modelapi.Car;
import iuresti.training.peopleandcars.modelapi.People;
import iuresti.training.peopleandcars.modelapi.PeopleCar;

import java.util.List;

public interface PeopleCarService {
    List<PeopleCar> fetchAllPeopleCars();
    List<Car> fetchAllPersonCars(String guid);
    List<People> fetchAllCarPeople(String vin);
    PeopleCar addPeopleCar(PeopleCar peopleCar) throws MyCarBadRequestException;
    void updatePeopleCar(String guid, String vin, PeopleCar peopleCar) throws MyCarBadRequestException;
    void deletePeopleCar(String guid, String vin) throws MyCarResourceNotFoundException;
}
