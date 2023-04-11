package iuresti.training.peopleandcars.service.impl;

import iuresti.training.peopleandcars.exception.MyCarBadRequestException;
import iuresti.training.peopleandcars.exception.MyCarResourceNotFoundException;
import iuresti.training.peopleandcars.modelapi.Car;
import iuresti.training.peopleandcars.modeldb.CarDB;
import iuresti.training.peopleandcars.repository.CarDao;
import iuresti.training.peopleandcars.service.CarDBMapper;
import iuresti.training.peopleandcars.service.CarMapper;
import iuresti.training.peopleandcars.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {
    private final CarDao carDao;
    private final CarMapper carMapper;
    private final CarDBMapper carDBMapper;

    @Autowired
    public CarServiceImpl(CarDao carDao, CarMapper carMapper, CarDBMapper carDBMapper) {
        this.carDao = carDao;
        this.carMapper = carMapper;
        this.carDBMapper = carDBMapper;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Car> fetchAllCars() {
        return carDao.findAll().stream().map(carMapper).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Car addCar(Car car) throws MyCarBadRequestException {
        UUID uuid = UUID.randomUUID();
        car.setVin(uuid.toString());

        try {
            CarDB returnedCar = carDao.save(carDBMapper.apply(car));
            return carMapper.apply(returnedCar);
        } catch (Exception e) {
            throw new MyCarBadRequestException(e.getMessage());
        }
    }

    @Transactional
    @Override
    public void updateCar(String id, Car car) throws MyCarResourceNotFoundException {
        fetchCarById(id);
        car.setVin(id);
        carDao.save(carDBMapper.apply(car));
    }

    @Transactional
    @Override
    public void deleteCar(String id) throws MyCarResourceNotFoundException {
        fetchCarById(id);
        carDao.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Car fetchCarById(String id) throws MyCarResourceNotFoundException {
        return carDao.findById(id).stream().map(carMapper).findFirst()
                .orElseThrow(
                () -> new MyCarResourceNotFoundException("Car not found!")
        );
    }

    @Transactional
    @Override
    public Car addCarWithVIN(String id, Car car) throws MyCarBadRequestException {
        car.setVin(id);

        try {
            CarDB returnedCar = carDao.save(carDBMapper.apply(car));

            return carMapper.apply(returnedCar);
        } catch (Exception e) {
            throw new MyCarBadRequestException(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Car> findCarsByPeopleGuid(String guid) {
        return carDao.findCarsByPeopleGuid(guid).stream().map(carMapper).collect(Collectors.toList());
    }
}
