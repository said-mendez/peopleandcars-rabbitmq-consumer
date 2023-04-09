package iuresti.training.peopleandcars.service;

import iuresti.training.peopleandcars.modelapi.Car;
import iuresti.training.peopleandcars.modeldb.CarDB;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CarDBMapper implements Function<Car, CarDB> {
    @Override
    public CarDB apply(Car car) {
        CarDB carDB = new CarDB();
        carDB.setVin(car.getVin());
        carDB.setBrand(car.getBrand());
        carDB.setModel(car.getModel());
        carDB.setYear(car.getYear());
        carDB.setColor(car.getColor());

        return carDB;
    }
}
