package iuresti.training.peopleandcars.service.impl;


import iuresti.training.peopleandcars.exception.MyCarBadRequestException;
import iuresti.training.peopleandcars.exception.MyCarResourceNotFoundException;
import iuresti.training.peopleandcars.modelapi.Car;
import iuresti.training.peopleandcars.modelapi.People;
import iuresti.training.peopleandcars.modelapi.PeopleCar;
import iuresti.training.peopleandcars.modeldb.PeopleCarDB;

import iuresti.training.peopleandcars.repository.PeopleCarDao;
import iuresti.training.peopleandcars.service.CarService;
import iuresti.training.peopleandcars.service.PeopleCarService;
import iuresti.training.peopleandcars.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class PeopleCarServiceImpl implements PeopleCarService {
    private final PeopleCarDao peopleCarDao;
    private final PeopleService peopleService;
    private final CarService carService;

    @Autowired
    public PeopleCarServiceImpl(PeopleCarDao peopleCarDao, PeopleService peopleService, CarService carService) {
        this.peopleService = peopleService;
        this.peopleCarDao = peopleCarDao;
        this.carService = carService;
    }

    @Override
    public List<PeopleCar> fetchAllPeopleCars() {
       return peopleCarDao.findAll().stream().map(peopleCar -> {
           PeopleCar peopleCarAPI = new PeopleCar();
           peopleCarAPI.setUuid(peopleCar.getUuid());
           peopleCarAPI.setPeopleId(peopleCar.getPeopleId());
           peopleCarAPI.setCarId(peopleCar.getCarId());

           return peopleCarAPI;
       }).collect(Collectors.toList());
    }

    @Override
    public List<Car> fetchAllPersonCars(String guid) {
//        return peopleCarDao.findAllPersonCars(guid).stream().map(car -> {
//            Car carAPI = new Car();
//            carAPI.setVin(car.getVin());
//            carAPI.setModel(car.getModel());
//            carAPI.setBrand(car.getBrand());
//            carAPI.setYear(car.getYear());
//            carAPI.setColor(car.getColor());
//
//            return carAPI;
//        }).collect(Collectors.toList());
        return carService.findCarsByPeopleGuid(guid);
    }

    @Override
    public List<People> fetchAllCarPeople(String vin) {
        return peopleService.fetchAllCarPeople(vin);
//        return peopleDao.findPeopleByCarsVin(vin).stream().map(people -> {
//            People peopleAPI = new People();
//            peopleAPI.setGuid(people.getGuid());
//            peopleAPI.setFirstName(people.getFirstName());
//            peopleAPI.setLastName(people.getLastName());
//            peopleAPI.setEmail(people.getEmail());
//            peopleAPI.setGender(people.getGender());
//
//            return peopleAPI;
//        }).collect(Collectors.toList());

//        return peopleCarDao.findAllByCarId(vin).stream().map(people -> {
//            People peopleAPI = new People();
//            peopleAPI.setGuid(people.getGuid());
//            peopleAPI.setFirstName(people.getFirstName());
//            peopleAPI.setLastName(people.getLastName());
//            peopleAPI.setEmail(people.getEmail());
//            peopleAPI.setGender(people.getGender());
//
//            return peopleAPI;
//        }).collect(Collectors.toList());
    }

    @Override
    public PeopleCar addPeopleCar(PeopleCar peopleCar) throws MyCarBadRequestException {
        try {
            int peopleCarExist = peopleCarDao.countByCarIdAndPeopleId(peopleCar.getCarId(), peopleCar.getPeopleId());
            if (peopleCarExist == 1) {
                throw new MyCarBadRequestException("People car relation already exist!");
            }

            int carCount = peopleCarDao.countByCarId(peopleCar.getCarId());
            if (carCount >= 2) {
                throw new MyCarBadRequestException("Car is already assigned to two persons");
            }

//            People people = peopleService.fetchPeopleById(peopleCar.getPeopleId());
//
//            Car car = carService.fetchCarById(peopleCar.getCarId());
            UUID uuid = UUID.randomUUID();

            PeopleCarDB peopleCarDB = new PeopleCarDB();
            peopleCarDB.setUuid(uuid.toString());
            peopleCarDB.setPeopleId(peopleCar.getPeopleId());
            peopleCarDB.setCarId(peopleCar.getCarId());

            PeopleCarDB returnedPeople = peopleCarDao.save(peopleCarDB);
            PeopleCar peopleCarAPI = new PeopleCar();
            peopleCarAPI.setUuid(returnedPeople.getUuid());
            peopleCarAPI.setCarId(returnedPeople.getCarId());
            peopleCarAPI.setPeopleId(returnedPeople.getPeopleId());

            return peopleCarAPI;
        } catch (Exception e) {
            throw new MyCarBadRequestException("Something went wrong! " + e);
        }
    }

    @Override
    public void updatePeopleCar(String guid, String vin, PeopleCar peopleCar) throws MyCarBadRequestException {
        // TODO: Check if a record exists with the given vin, gui
        // peopleCarDao.update(guid, vin, peopleCar);
    }

    @Override
    public void deletePeopleCar(String guid, String vin) throws MyCarResourceNotFoundException {

    }
}
