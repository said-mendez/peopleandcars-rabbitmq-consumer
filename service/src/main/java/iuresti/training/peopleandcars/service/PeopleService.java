package iuresti.training.peopleandcars.service;

import iuresti.training.peopleandcars.exception.MyCarBadRequestException;
import iuresti.training.peopleandcars.exception.MyCarResourceNotFoundException;
import iuresti.training.peopleandcars.modelapi.People;

import java.util.List;

public interface PeopleService {
    List<People> fetchAllPeople() throws MyCarResourceNotFoundException;
    People addPeople(People people) throws MyCarBadRequestException;
    void updatePeople(String id, People people) throws MyCarResourceNotFoundException;
    void deletePeople(String id) throws MyCarResourceNotFoundException;
    People fetchPeopleById(String id) throws MyCarResourceNotFoundException;
    People addPeopleWithGUID(String id, People people) throws MyCarBadRequestException;
    List<People> fetchAllCarPeople(String vin) throws MyCarResourceNotFoundException;
}
