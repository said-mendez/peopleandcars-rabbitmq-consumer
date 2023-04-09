package iuresti.training.peopleandcars.service;

import iuresti.training.peopleandcars.modelapi.Car;
import iuresti.training.peopleandcars.modeldb.CarDB;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CarMapper implements Function<CarDB, Car> {

    @Override
    public Car apply(CarDB carDB) {
        Car carAPI = new Car();

        carAPI.setVin(carDB.getVin());
        carAPI.setBrand(carDB.getBrand());
        carAPI.setModel(carDB.getModel());
        carAPI.setYear(carDB.getYear());
        carAPI.setColor(carDB.getColor());

        return carAPI;
    }
}
