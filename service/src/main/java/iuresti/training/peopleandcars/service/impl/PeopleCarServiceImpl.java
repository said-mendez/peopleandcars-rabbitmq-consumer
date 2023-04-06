package iuresti.training.peopleandcars.service.impl;


import iuresti.training.peopleandcars.exception.MyCarBadRequestException;
import iuresti.training.peopleandcars.exception.MyCarResourceNotFoundException;
import iuresti.training.peopleandcars.modelapi.Car;
import iuresti.training.peopleandcars.modelapi.People;
import iuresti.training.peopleandcars.modelapi.PeopleCar;
import iuresti.training.peopleandcars.modeldb.PeopleCarDB;
import iuresti.training.peopleandcars.repository.PeopleCarDao;
import iuresti.training.peopleandcars.service.PeopleCarService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PeopleCarServiceImpl implements PeopleCarService {
    private final PeopleCarDao peopleCarDao;

    public PeopleCarServiceImpl(PeopleCarDao peopleCarDao) {
        this.peopleCarDao = peopleCarDao;
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
        return peopleCarDao.findAllPersonCars(guid).stream().map(car -> {
            Car carAPI = new Car();
            carAPI.setVin(car.getVin());
            carAPI.setModel(car.getModel());
            carAPI.setBrand(car.getBrand());
            carAPI.setYear(car.getYear());
            carAPI.setColor(car.getColor());

            return carAPI;
        }).collect(Collectors.toList());
    }

    @Override
    public List<People> fetchAllCarPeople(String vin) {
        return peopleCarDao.findAllCarPeople(vin).stream().map(people -> {
            People peopleAPI = new People();
            peopleAPI.setGuid(people.getGuid());
            peopleAPI.setFirstName(people.getFirstName());
            peopleAPI.setLastName(people.getLastName());
            peopleAPI.setEmail(people.getEmail());
            peopleAPI.setGender(people.getGender());

            return peopleAPI;
        }).collect(Collectors.toList());
    }

    @Override
    public PeopleCar addPeopleCar(PeopleCar peopleCar) throws MyCarBadRequestException {
        PeopleCarDB peopleCarDB = new PeopleCarDB();
        peopleCarDB.setPeopleId(peopleCar.getPeopleId());
        peopleCarDB.setCarId(peopleCar.getCarId());

        PeopleCarDB returnedPeople = peopleCarDao.save(peopleCarDB);
        PeopleCar peopleCarAPI = new PeopleCar();
        peopleCarAPI.setUuid(returnedPeople.getUuid());
        peopleCarAPI.setCarId(returnedPeople.getCarId());
        peopleCarAPI.setPeopleId(returnedPeople.getPeopleId());

        return peopleCarAPI;
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
