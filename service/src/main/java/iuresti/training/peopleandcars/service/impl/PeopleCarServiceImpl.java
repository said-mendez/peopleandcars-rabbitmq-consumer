package iuresti.training.peopleandcars.service.impl;


import iuresti.training.peopleandcars.exception.MyCarBadRequestException;
import iuresti.training.peopleandcars.exception.MyCarResourceNotFoundException;
import iuresti.training.peopleandcars.modelapi.Car;
import iuresti.training.peopleandcars.modelapi.People;
import iuresti.training.peopleandcars.modelapi.PeopleCar;
import iuresti.training.peopleandcars.modeldb.PeopleCarDB;

import iuresti.training.peopleandcars.repository.PeopleCarDao;
import iuresti.training.peopleandcars.service.*;
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
    private final PeopleCarDBMapper peopleCarDBMapper;
    private final PeopleCarMapper peopleCarMapper;

    @Autowired
    public PeopleCarServiceImpl(PeopleCarDao peopleCarDao, PeopleService peopleService, CarService carService,
                                PeopleCarDBMapper peopleCarDBMapper, PeopleCarMapper peopleCarMapper) {
        this.peopleService = peopleService;
        this.peopleCarDao = peopleCarDao;
        this.carService = carService;
        this.peopleCarDBMapper = peopleCarDBMapper;
        this.peopleCarMapper = peopleCarMapper;
    }

    @Transactional(readOnly = true)
    @Override
    public List<PeopleCar> fetchAllPeopleCars() {
       return peopleCarDao.findAll().stream().map(peopleCarMapper).collect(Collectors.toList());

    }

    @Transactional(readOnly = true)
    @Override
    public List<Car> fetchAllPersonCars(String guid) {
        return carService.findCarsByPeopleGuid(guid);
    }

    @Transactional(readOnly = true)
    @Override
    public List<People> fetchAllCarPeople(String vin) {
        return peopleService.fetchAllCarPeople(vin);
    }

    @Transactional
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

            UUID uuid = UUID.randomUUID();
            peopleCar.setUuid(uuid.toString());

            PeopleCarDB returnedPeopleCar = peopleCarDao.save(peopleCarDBMapper.apply(peopleCar));

            return peopleCarMapper.apply(returnedPeopleCar);
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
