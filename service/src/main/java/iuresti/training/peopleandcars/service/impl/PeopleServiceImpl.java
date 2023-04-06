package iuresti.training.peopleandcars.service.impl;


import iuresti.training.peopleandcars.exception.MyCarBadRequestException;
import iuresti.training.peopleandcars.exception.MyCarResourceNotFoundException;
import iuresti.training.peopleandcars.modelapi.People;
import iuresti.training.peopleandcars.modeldb.CarDB;
import iuresti.training.peopleandcars.modeldb.PeopleDB;
import iuresti.training.peopleandcars.repository.PeopleDao;
import iuresti.training.peopleandcars.service.PeopleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class PeopleServiceImpl implements PeopleService {
    private final PeopleDao peopleDao;

    public PeopleServiceImpl(PeopleDao peopleDao) {
        this.peopleDao = peopleDao;
    }

    @Transactional(readOnly = true)
    @Override
    public List<People> fetchAllPeople() {
        return peopleDao.findAll().stream().map(person -> {
            People peopleAPI = new People();

            peopleAPI.setGuid(person.getGuid());
            peopleAPI.setFirstName(person.getFirstName());
            peopleAPI.setLastName(person.getLastName());
            peopleAPI.setEmail(person.getEmail());
            peopleAPI.setGender(person.getGender());

            return peopleAPI;
        }).collect(Collectors.toList());
    }

    @Override
    public People addPeople(People people) throws MyCarBadRequestException {
        PeopleDB peopleDB = new PeopleDB();
        peopleDB.setFirstName(people.getFirstName());
        peopleDB.setLastName(people.getLastName());
        peopleDB.setEmail(people.getEmail());
        peopleDB.setGender(people.getGender());

        PeopleDB returnedPeople = peopleDao.save(peopleDB);
        People peopleAPI = new People();
        peopleAPI.setGuid(returnedPeople.getGuid());
        peopleAPI.setFirstName(returnedPeople.getFirstName());
        peopleAPI.setLastName(returnedPeople.getLastName());
        peopleAPI.setEmail(returnedPeople.getEmail());
        peopleAPI.setGender(returnedPeople.getGender());

        return peopleAPI;
    }

    @Transactional
    @Override
    public void updatePeople(String id, People people) throws MyCarResourceNotFoundException {
        fetchPeopleById(id);

        PeopleDB peopleDB = new PeopleDB();
        peopleDB.setFirstName(people.getFirstName());
        peopleDB.setLastName(people.getLastName());
        peopleDB.setEmail(people.getEmail());
        peopleDB.setGender(people.getGender());

        peopleDao.save(peopleDB);
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
        return peopleDao.findById(id).stream().map(people -> {
           People peopleAPI = new People();
           peopleAPI.setGuid(people.getGuid());
           peopleAPI.setFirstName(people.getFirstName());
           peopleAPI.setLastName(people.getLastName());
           peopleAPI.setEmail(people.getEmail());
           peopleAPI.setGender(people.getGender());

           return peopleAPI;
        }).findFirst()
                .orElseThrow(
                        () -> new MyCarResourceNotFoundException("People not found!")
                );
    }

    @Transactional
    @Override
    public People addPeopleWithGUID(String id, People people) throws MyCarBadRequestException {
        PeopleDB peopleDB = new PeopleDB();
        peopleDB.setGuid(id);
        peopleDB.setFirstName(people.getFirstName());
        peopleDB.setLastName(people.getLastName());
        peopleDB.setEmail(people.getEmail());
        peopleDB.setGender(people.getGender());

        PeopleDB returnedPeople = peopleDao.save(peopleDB);
        People peopleAPI = new People();
        peopleAPI.setGuid(returnedPeople.getGuid());
        peopleAPI.setFirstName(returnedPeople.getFirstName());
        peopleAPI.setLastName(returnedPeople.getLastName());
        peopleAPI.setEmail(returnedPeople.getEmail());
        peopleAPI.setGender(returnedPeople.getGender());

        return peopleAPI;
    }
}
