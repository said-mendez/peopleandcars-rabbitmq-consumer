package iuresti.training.peopleandcars.service;


import iuresti.training.peopleandcars.exception.MyCarBadRequestException;
import iuresti.training.peopleandcars.exception.MyCarResourceNotFoundException;
import iuresti.training.peopleandcars.modelapi.Car;

import java.util.List;

public interface CarService {
    List<Car> fetchAllCars() throws MyCarResourceNotFoundException;
    Car addCar(Car car) throws MyCarBadRequestException;
    void updateCar(String id, Car car) throws MyCarResourceNotFoundException;
    void deleteCar(String id) throws MyCarResourceNotFoundException;
    Car fetchCarById(String id) throws MyCarResourceNotFoundException;
    Car addCarWithVIN(String id, Car car) throws MyCarBadRequestException;
    List<Car> findCarsByPeopleGuid(String guid) throws MyCarResourceNotFoundException;
}
