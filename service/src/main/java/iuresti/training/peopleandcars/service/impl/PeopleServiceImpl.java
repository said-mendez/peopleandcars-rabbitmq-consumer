package iuresti.training.peopleandcars.service.impl;


import iuresti.training.peopleandcars.exception.MyCarBadRequestException;
import iuresti.training.peopleandcars.exception.MyCarResourceNotFoundException;
import iuresti.training.peopleandcars.modelapi.People;
import iuresti.training.peopleandcars.modeldb.PeopleDB;
import iuresti.training.peopleandcars.repository.PeopleDao;
import iuresti.training.peopleandcars.service.PeopleDBMapper;
import iuresti.training.peopleandcars.service.PeopleMapper;
import iuresti.training.peopleandcars.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service

public class PeopleServiceImpl implements PeopleService {
    private final PeopleDao peopleDao;
    private final PeopleMapper peopleMapper;
    private final PeopleDBMapper peopleDBMapper;

    @Autowired
    public PeopleServiceImpl(PeopleDao peopleDao, PeopleMapper peopleMapper, PeopleDBMapper peopleDBMapper) {
        this.peopleDao = peopleDao;
        this.peopleMapper = peopleMapper;
        this.peopleDBMapper = peopleDBMapper;
    }

    @Transactional(readOnly = true)
    @Override
    public List<People> fetchAllPeople() throws MyCarResourceNotFoundException {
        List<PeopleDB> peopleList = peopleDao.findAll();

        if (peopleList.isEmpty()) {
            throw new MyCarResourceNotFoundException("There are not any people records!");
        }

        return peopleList.stream().map(peopleMapper).collect(Collectors.toList());
    }


    @Transactional
    @Override
    public People addPeople(People people) throws MyCarBadRequestException {
        UUID uuid = UUID.randomUUID();
        people.setGuid(uuid.toString());

        try {
            PeopleDB returnedPeople = peopleDao.save(peopleDBMapper.apply(people));

            return peopleMapper.apply(returnedPeople);
        } catch(Exception e) {
            throw new MyCarBadRequestException(e.getMessage());
        }
    }

    @Transactional
    @Override
    public void updatePeople(String id, People people) throws MyCarResourceNotFoundException {
        fetchPeopleById(id);
        people.setGuid(id);

        peopleDao.save(peopleDBMapper.apply(people));
    }

    @Transactional
    @Override
    public void deletePeople(String id) throws MyCarResourceNotFoundException {
        fetchPeopleById(id);
        peopleDao.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public People fetchPeopleById(String id) throws MyCarResourceNotFoundException {
        return peopleDao.findById(id).stream().map(peopleMapper).findFirst()
                .orElseThrow(
                        () -> new MyCarResourceNotFoundException("People not found!")
                );
    }

    @Transactional
    @Override
    public People addPeopleWithGUID(String id, People people) throws MyCarBadRequestException {
        try {
            people.setGuid(id);

            PeopleDB returnedPeople = peopleDao.save(peopleDBMapper.apply(people));

            return peopleMapper.apply(returnedPeople);
        } catch (Exception e) {
            throw new MyCarBadRequestException("Something went wrong! " + e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<People> fetchAllCarPeople(String vin) throws MyCarResourceNotFoundException {
        List<PeopleDB> peopleDBList = peopleDao.findPeopleByCarsVin(vin);
        if (peopleDBList.isEmpty()) {
            throw new MyCarResourceNotFoundException("There are not any people assigned to car VIN: " + vin);
        }

        return peopleDBList.stream().map(peopleMapper).collect(Collectors.toList());
    }
}
