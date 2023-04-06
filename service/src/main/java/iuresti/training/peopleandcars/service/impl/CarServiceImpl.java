package iuresti.training.peopleandcars.service.impl;

import iuresti.training.peopleandcars.exception.MyCarBadRequestException;
import iuresti.training.peopleandcars.exception.MyCarResourceNotFoundException;
import iuresti.training.peopleandcars.modelapi.Car;
import iuresti.training.peopleandcars.modeldb.CarDB;
import iuresti.training.peopleandcars.repository.CarDao;
import iuresti.training.peopleandcars.service.CarService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {
    private final CarDao carDao;

    public CarServiceImpl(CarDao carDao) {
        this.carDao = carDao;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Car> fetchAllCars() {
        return carDao.findAll().stream().map(car -> {
            Car carAPI = new Car();

            carAPI.setVin(car.getVin());
            carAPI.setBrand(car.getBrand());
            carAPI.setModel(car.getModel());
            carAPI.setYear(car.getYear());
            carAPI.setColor(car.getColor());

            return carAPI;
        }).collect(Collectors.toList());
    }

    @Override
    public Car addCar(Car car) throws MyCarBadRequestException {
        CarDB carDB = new CarDB();
        carDB.setBrand(car.getBrand());
        carDB.setModel(car.getModel());
        carDB.setYear(car.getYear());
        carDB.setColor(car.getColor());

        CarDB returnedCar = carDao.save(carDB);
        Car carAPI = new Car();
        carAPI.setVin(returnedCar.getVin());
        carAPI.setBrand(returnedCar.getBrand());
        carAPI.setModel(returnedCar.getModel());
        carAPI.setYear(returnedCar.getYear());
        carAPI.setColor(returnedCar.getColor());

        return carAPI;
    }

    @Override
    public void updateCar(String id, Car car) throws MyCarResourceNotFoundException {
        fetchCarById(id);

        CarDB carDB = new CarDB();
        carDB.setBrand(car.getBrand());
        carDB.setModel(car.getModel());
        carDB.setYear(car.getYear());
        carDB.setColor(car.getColor());

        carDao.save(carDB);
    }

    @Override
    public void deleteCar(String id) throws MyCarResourceNotFoundException {
        fetchCarById(id);
        carDao.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Car fetchCarById(String id) throws MyCarResourceNotFoundException {
        return carDao.findById(id).stream().map(car -> {
                    Car carAPI = new Car();
                    carAPI.setBrand(car.getBrand());
                    carAPI.setColor(car.getColor());
                    carAPI.setModel(car.getModel());
                    carAPI.setYear(car.getYear());
                    carAPI.setVin(car.getVin());

                    return carAPI;
                }).findFirst()
                .orElseThrow(
                () -> new MyCarResourceNotFoundException("Car not found!")
        );
    }

    @Override
    public Car addCarWithVIN(String id, Car car) throws MyCarBadRequestException {
        CarDB carDB = new CarDB();
        carDB.setVin(id);
        carDB.setBrand(car.getBrand());
        carDB.setModel(car.getModel());
        carDB.setYear(car.getYear());
        carDB.setColor(car.getColor());

        CarDB returnedCar = carDao.save(carDB);
        Car carAPI = new Car();
        carAPI.setVin(returnedCar.getVin());
        carAPI.setBrand(returnedCar.getBrand());
        carAPI.setModel(returnedCar.getModel());
        carAPI.setYear(returnedCar.getYear());
        carAPI.setColor(returnedCar.getColor());

        return carAPI;
    }
}
